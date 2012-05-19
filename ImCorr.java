/*
*Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
*This file is part of Pick_EM program
*Pick_EM is free software; you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation; either version 2 of the License, or
*(at your option) any later version.
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*You should have received a copy of the GNU General Public License along
*with this program; if not, write to the Free Software Foundation, Inc.,
*51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

import java.awt.Polygon;
import java.util.Hashtable;
import java.util.Vector;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.Duplicator;
import ij.plugin.filter.MaximumFinder;
import ij.process.ImageProcessor;

public class ImCorr extends Picker {
// Picking algorithm : image correlation
	
	private static ImagePlus imgBlocked;
	
	private static double z;
	
	private static Cropper cropper;
	
	ImCorr(){}
	
	static void picking() {
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		imgBlocked = WindowManager.getCurrentImage();
		im = new Duplicator().run(imgBlocked);
		int i = imgBlocked.getCurrentSlice();
		ImageStack tempstack = im.getStack();
		ImageProcessor ip = tempstack.getProcessor(i);
		ImagePlus impDup = new ImagePlus("imgPlus",ip);
		IJ.run(impDup,"Enhance Contrast", "saturated=0 normalize");
		ip.findEdges();
		pick(impDup, i);
	}
	
	static double[][] sliceSelection(){
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		imgBlocked = WindowManager.getCurrentImage();
		im=new Duplicator().run(imgBlocked);
		int nbslice = imgBlocked.getStackSize();
		for (int i=1;i<=nbslice;i++){	
			ImageStack tempstack = im.getStack();
			ImageProcessor ip = tempstack.getProcessor(i);
			ImagePlus impDup =new ImagePlus("imgPlus",ip);
			IJ.run(impDup,"Enhance Contrast", "saturated=0 normalize");
			ip.findEdges();
			pick(impDup, i);
		}
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		cropMode = hashAttributes.get("crop");
		cropperMode = Boolean.parseBoolean(cropMode);
		double[][]array = resultConverter();
		if (cropperMode) {
			cropper = new Cropper(imgBlocked, array);
		}
		return array;
	}
	
	public static void pick(ImagePlus image,int currentslice){
		z = (double) currentslice;
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String rMin = hashAttributes.get("rMin");
		String rMax = hashAttributes.get("rMax");
		String rInc = hashAttributes.get("rInc");
		noiseT = hashAttributes.get("noise");
		tolerance = Double.parseDouble(noiseT);
		ResultsTable table = new ResultsTable();
		MaximumFinder mf = new MaximumFinder();
		boolean excludeOnEdges = false;
		int w = image.getWidth(); //image width
		int h = image.getHeight(); //image heigh
		int radiusMin = Integer.parseInt(rMin); //radius min of the draw circule
		int radiusMax = Integer.parseInt(rMax); //radius max of the draw circule
		int radiusInc = Integer.parseInt(rInc); //radius incrementation
		// Creation of an image which contains a circle with different diameters
		for (int radius=radiusMin; radius<=radiusMax; radius=radius+radiusInc){
			ImagePlus imp = IJ.createImage("circle", "8-bit White", w, h, 1);
			imp.setRoi(new OvalRoi((w/2)-radius, (h/2)-radius, radius*2, radius*2));
			IJ.run(imp, "Draw", "");
			ImagePlus result = FFTMath.doMath(image,imp);
			ImageProcessor ip = result.getProcessor();
			ip.invert();
			WindowManager.setTempCurrentImage(result);
			IJ.run(result,"Enhance Contrast", "saturated=0 normalize");
			Polygon points = mf.getMaxima(ip, tolerance, excludeOnEdges);
			int[] xArray = points.xpoints;
			int[] yArray = points.ypoints;
			for (int i=0; i<xArray.length; i++){
				table.incrementCounter();
				double tempx=(double)xArray[i];
				double tempy=(double)yArray[i];
				int pxValue= (int) ip.getPixelValue(xArray[i],yArray[i]);
				table.addValue("X",tempx);
				table.addValue("Y",tempy);
				table.addValue("Max",pxValue);
			}
			result.close();
			for(int i=0;i<xArray.length;i++){
				System.out.println(xArray[i]);
			}
		}
		sort(table,imgBlocked);
	}
	
	static void sort(ResultsTable table,ImagePlus image){
		int counter = table.getCounter();
		int []list = new int [counter];
		int []check = new int[counter];
		int lenlist = 0;
		int nb = 0;
		int iterator = counter-1;
	
		for (int j=iterator;j>=0;j--){
			int yetcheck = 0;
			int maxval = j;
			int cpt = 0;
			double xj = table.getValue("X",j);
			double yj = table.getValue("Y",j);
			double maxj = table.getValue("Max",j);
	
			for (int iter=0; iter<nb; iter++){
				if( j == check[iter]){
					yetcheck = 1;
				}
			}	
	
			if(yetcheck == 0){
				for (int k=iterator;k>=0;k--){
					double xk = table.getValue("X",k);
					double yk = table.getValue("Y",k);
					double maxk = table.getValue("Max",k);
					if ((xk<=xj+40 && xk>=xj-40) && (yk<=yj+40 && yk>=yj-40) ){
						int cont = 0;
						for (int p=0;p<nb;p++){
							if (check[p] == k){cont += 1;}
						}
						if (cont == 0){
							check[nb] = k;
							nb ++;
						}
						if (maxj < maxk){maxval = k;}
						else if(maxj > maxk){
							maxval = j;
							}
						else{
							if (j != k){
								maxval = j;
							}
						}
					}
				}
				for (int q = 0; q<lenlist; q++){
					if (maxval == list[q]){cpt+=1;}
				}
				if (cpt == 0){
					list[lenlist] = maxval;
					lenlist++;
				}
			}
			iterator --;
		}
		results(list,table,lenlist,image);
	}
	
	static void results(int []list,ResultsTable table, int lenlist,ImagePlus image){
	
		int []xpoints = new int[lenlist];
		int []ypoints = new int[lenlist];
		
		for (int l=0; l<lenlist; l++){
			int line2 = list[l];
			double x = table.getValue("X",line2);
			double y = table.getValue("Y",line2);
			xtab.add(x);
			ytab.add(y);
			slice.add(z);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[l] = xx;
			ypoints[l] = yy;
			image.setRoi(new PointRoi(xpoints,ypoints,lenlist));
		}
	}
	
	static double[][] resultConverter(){
		int arrayLength = xtab.size();
		Object[] tempX = new String[arrayLength];
		Object[] tempY = new String[arrayLength];
		Object[] tempZ = new String[arrayLength];
		tempX = xtab.toArray();
		tempY = ytab.toArray();
		tempZ = slice.toArray();
		double[] xArray = new double[arrayLength];
		double[] yArray = new double[arrayLength];
		double[] zArray = new double[arrayLength];
		for (int i=0; i < arrayLength; i++){
			String temp = String.valueOf(tempX[i]);
			xArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempY[i]);
			yArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempZ[i]);
			zArray[i] = Double.parseDouble(temp);
		}
		double[][] coordinates = new double[3][arrayLength];
		coordinates[0] = xArray;
		coordinates[1] = yArray;
		coordinates[2] = zArray;
		return coordinates;
	}
}