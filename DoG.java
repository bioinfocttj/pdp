/*
Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
import java.awt.Polygon;
import java.util.Hashtable;
import java.util.Vector;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.Duplicator;
import ij.plugin.ImageCalculator;
import ij.plugin.filter.MaximumFinder;
import ij.process.ImageProcessor;

abstract class DoG implements Picker {

	static Vector[] resultstable = new Vector[3];
	static Vector<Double> xtab=new Vector<Double>();
	static Vector<Double> ytab=new Vector<Double>();
	static Vector<Double> slice=new Vector<Double>();
	
	DoG(){
	}
	
	static void picking() {
		ImagePlus im = WindowManager.getCurrentImage();
		pick(im, 1);
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		resultstable[0].removeAllElements();
		resultstable[1].removeAllElements();
		resultstable[2].removeAllElements();
		IJ.run("Clear Results");
	}
	
	static double[][] sliceSelection(){
		
		ImagePlus im = WindowManager.getCurrentImage();
		String stackName = im.getTitle();
		int nbslice=im.getStackSize();
		for (int a=1;a<=nbslice;a++){
			im.setSlice(a);
			pick(im,a);
		}
		//cast the vector in an array so as to send it to the cropper
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String cropMode = hashAttributes.get("crop");
		boolean cropperMode = Boolean.parseBoolean(cropMode);
		double[][]array= resultConverter();
		if (cropperMode) {
			for (int a=1;a<=nbslice;a++){
				im.setSlice(a);
				IJ.run(im, "Duplicate...", stackName);
				ImagePlus dupli = WindowManager.getCurrentImage();
				Cropper cropper = new Cropper(dupli, array, a);
				cropper.crop(a, stackName);
				dupli.close();
			}
			IJ.run(im, "Images to Stack", "name=stack title=[DUP] use");
		}
		return array;
		
	}

	public static void pick(ImagePlus imp, int currentslice){
		
		ImageCalculator ic;
		ResultsTable table = new ResultsTable();
		int counter;
		int[] xpoints;
		int[] ypoints;
		MaximumFinder mf = new MaximumFinder();
		boolean excludeOnEdges = false;
		
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String sigma1 = hashAttributes.get("sigma1");
		String sigma2 = hashAttributes.get("sigma2");
		String noiseT = hashAttributes.get("noiseTolerance");
		double tolerance = Double.parseDouble(noiseT);
		
		imp.setSlice(currentslice);
		ImagePlus imp1 = new Duplicator().run(imp);
		imp1.setSlice(currentslice);
		ImagePlus imp2 = new Duplicator().run(imp1);
		
		String si1 = "sigma=" + sigma1;
		String si2 = "sigma=" + sigma2;

		imp1.setSlice(currentslice);
		IJ.run(imp1, "Gaussian Blur...", si1);
		imp2.setSlice(currentslice);
		IJ.run(imp2, "Gaussian Blur...", si2);
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create 32-bit", imp2, imp1);
		WindowManager.setTempCurrentImage(imp3);
		

		ImageProcessor ip3 = imp3.getProcessor();
		Polygon points = mf.getMaxima(ip3, tolerance, excludeOnEdges);
		int[] xArray = points.xpoints;
		int[] yArray = points.ypoints;
		for (int i=0; i<xArray.length; i++){
			table.incrementCounter();
			double tempx=(double)xArray[i];
			double tempy=(double)yArray[i];
			table.addValue("X",tempx);
			table.addValue("Y",tempy);
			table.addValue("Slice",currentslice);
		}
		counter=table.getCounter();
		xpoints = new int[counter];
		ypoints = new int [counter];
		for (int i=0; i<counter; i++){
			double x = table.getValue("X",i);
			double y = table.getValue("Y",i);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[i] = xx;
			ypoints[i] = yy;
			xtab.add(x);
			ytab.add(y);
			imp3.close();
			imp.setRoi(new PointRoi(xpoints, ypoints, counter));
		}
		for(int i=0;i<counter;i++){
			double temp = table.getValue("Slice", i);
			slice.add(temp);
		}
		resultstable[0]= xtab;
		resultstable[1]= ytab;
		resultstable[2] = slice;
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
}
