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
import ij.WindowManager;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.filter.MaximumFinder;
import ij.process.ImageProcessor;

abstract class ImCorr implements Picker {
	private static ImagePlus im;
	static Vector[] resultstable = new Vector[3];
	static Vector<Double> xtab=new Vector<Double>();
	static Vector<Double> ytab=new Vector<Double>();
	static Vector<Double> slice=new Vector<Double>();
	private static double z;
	private static Cropper cropper;
	
	ImCorr(){
	}
	static void picking() {
		im = WindowManager.getCurrentImage();
		int current=im.getSlice();
		System.out.println(current);
		pick(im, current);
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		resultstable[0].removeAllElements();
		resultstable[1].removeAllElements();
		resultstable[2].removeAllElements();
		IJ.run("Clear Results");
		}
	
	static double[][] sliceSelection(){
		
		im=WindowManager.getCurrentImage();
		
		int nbslice=im.getStackSize();
		for (int a=1;a<=nbslice;a++){
			im.setSlice(a);
			pick(im, a);
		}
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String cropMode = hashAttributes.get("crop");
		boolean cropperMode = Boolean.parseBoolean(cropMode);
		double[][]array= resultConverter();
		if (cropperMode) {
			cropper = new Cropper(im, array);
		}
		return array;
	}

	public static void pick(ImagePlus image,int currentslice){
		z = (double) currentslice;
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String rMin = hashAttributes.get("radiusMin");
		String rMax = hashAttributes.get("radiusMax");
		String rInc = hashAttributes.get("radiusInc");
		String noiseT = hashAttributes.get("noiseTolerance");
		double tolerance = Double.parseDouble(noiseT);
		ResultsTable table = new ResultsTable(); //result table
		MaximumFinder mf = new MaximumFinder();
		boolean excludeOnEdges = false;
		int w=image.getWidth(); //image width
		int h=image.getHeight(); //image heigh
		int radiusMin=Integer.parseInt(rMin); //radius min of the draw circule
		int radiusMax=Integer.parseInt(rMax); //radius max of the draw circule
		int radiusInc=Integer.parseInt(rInc); //radius incrementation
		//creation of an image which contains a circle with different diameters
		image.setSlice(currentslice);
		for (int radius=radiusMin;radius<=radiusMax;radius=radius+radiusInc){
			ImagePlus imp = IJ.createImage("test2", "8-bit White", w, h, 1);
			imp.setRoi(new OvalRoi((w/2)-radius, (h/2)-radius, radius*2, radius*2));
			IJ.run(imp, "Draw", "");
			ImagePlus result = FFTMath.doMath(image,imp);
			ImageProcessor ip = result.getProcessor();
			
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
		}
		sort(table,im);
	}
	
	static  void sort(ResultsTable table,ImagePlus image)
	{	
		int counter=table.getCounter();
		int []list=new int [counter];
		int []pass=new int[counter];
		int lenlist=0;
		int nb = 0;
		int iterator = counter-1;
		
		for (int j=iterator;j>=0;j--){
			int yetpass = 0;
			int maxval=j;
			int cpt=0;
			double xj=table.getValue("X",j);
			double yj=table.getValue("Y",j);
			double maxj=table.getValue("Max",j);
			
			for (int iter=0; iter<nb; iter++){
				if( j == pass[iter]){
					yetpass=1;
				}
			}
			
			if(yetpass == 0){
				for (int k=iterator;k>=0;k--){
					double xk=table.getValue("X",k);
					double yk=table.getValue("Y",k);
					double maxk=table.getValue("Max",k);
						if ((xk<=xj+40 && xk>=xj-40) && (yk<=yj+40 && yk>=yj-40) ){
						int cont=0;
						for (int p=0;p<nb;p++){
							if (pass[p]==k){cont+=1;}
						}
						if (cont==0){
							pass[nb]=k;
							nb ++;
						}
					if (maxj<maxk){maxval=k;}
					else if(maxj>maxk){maxval=j;}
					else{if (j != k){maxval=j;}}
					}
				}
				for (int q=0;q<lenlist;q++){
					if (maxval == list[q]){cpt+=1;}
				}
				if (cpt==0){
					list[lenlist]=maxval;
					lenlist++;
				}
			}
			iterator --;
		}
		results(list,table,lenlist,image);
	}
	
	static Object results(int []list,ResultsTable table, int lenlist,ImagePlus image)
	{
		
		int []xpoints=new int[lenlist];
		int []ypoints=new int[lenlist];
		
		for (int l=0;l<lenlist;l++){
			int line2=list[l];
			double x=table.getValue("X",line2);
			double y=table.getValue("Y",line2);
			xtab.add(x);
			ytab.add(y);
			slice.add(z);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[l] = xx;
			ypoints[l] = yy;
			image.setRoi(new PointRoi(xpoints,ypoints,lenlist));
		}
		resultstable[0]= xtab;
		resultstable[1]= ytab;
		resultstable[2] = slice;
		return resultstable;
	}

	static double[][] resultConverter(){
		int arrayLength=xtab.size();
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
		coordinates[0]=xArray;
		coordinates[1]=yArray;
		coordinates[2]=zArray;
		return coordinates;
	}
	
	
	
	static void printResultTable(Vector[] resulttable){
		for (int i=0;i<resulttable[1].size();i++){
			for (int j=0;j<resulttable.length;j++){
				System.out.println("lala");
				System.out.print(resulttable[j]);
				System.out.println("\n");
			}
		}
	}
}
