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

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.Duplicator;
import ij.plugin.ImageCalculator;
import ij.plugin.filter.MaximumFinder;
import ij.process.ImageProcessor;

public class DilateDiff extends Picker{
// Picking algorithm : dilate difference
	
	private static Cropper cropper;
	
	DilateDiff(){}
	
	static void picking() {
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		im = WindowManager.getCurrentImage();
		int current=im.getSlice();
		System.out.println(current);
		pick(im, current);
	}
	
	static double[][] sliceSelection(){
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
		im = WindowManager.getCurrentImage();
		int nbslice = im.getStackSize();
		for (int i=1; i<=nbslice; i++){
			im.setSlice(i);
			pick(im, i);
		}
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		cropMode = hashAttributes.get("crop");
		cropperMode = Boolean.parseBoolean(cropMode);
		array = resultConverter();
		if (cropperMode) {
			cropper = new Cropper(im, array);
		}
		return array;
	}
	
	static void pick(ImagePlus image, int currentslice){
		ImageCalculator ic;
		int counter=0;
		int[] xpoints;
		int[] ypoints;
		ResultsTable table = new ResultsTable();
		MaximumFinder mf = new MaximumFinder();
		boolean excludeOnEdges = false;
		
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String iteration1 = hashAttributes.get("iter1");
		String iteration2 = hashAttributes.get("iter2");
		
		String it1 = "iterations=" + iteration1+" count=1 edm=Overwrite do=Nothing";
		String it2 = "iterations=" + iteration2+" count=1 edm=Overwrite do=Nothing";
		noiseT = hashAttributes.get("noise");
		tolerance = Double.parseDouble(noiseT);
		
		ImagePlus imp = WindowManager.getCurrentImage();
		ImagePlus imp1=new Duplicator().run(imp);
		ImagePlus imp2= new Duplicator().run(imp1);
		imp1.setSlice(currentslice);
		imp2.setSlice(currentslice);
		IJ.run(imp1, "Make Binary", "calculate");
		IJ.run(imp2, "Make Binary", "calculate");
		IJ.run(imp1, "Options...", it1);
		IJ.run(imp1, "Dilate", "slice");
		IJ.run(imp2, "Options...", it2);
		IJ.run(imp2, "Dilate", "slice");
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp2, imp1);
		ImageProcessor ip3 = imp3.getProcessor();
		ip3.invert();
		Polygon points = mf.getMaxima(ip3, tolerance, excludeOnEdges);
		int[] xArray = points.xpoints;
		int[] yArray = points.ypoints;
		for (int i=0; i<xArray.length; i++){
			table.incrementCounter();
			double tempx = (double)xArray[i];
			double tempy = (double)yArray[i];
			table.addValue("X",tempx);
			table.addValue("Y",tempy);
			table.addValue("Slice",currentslice);
		}
		counter = table.getCounter();
		xpoints = new int[counter];
		ypoints = new int [counter];
	
		for (int i=0;i<counter;i++){
			double x = table.getValue("X",i);
			double y = table.getValue("Y",i);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[i] = xx;
			ypoints[i] = yy;
			xtab.add(x);
			ytab.add(y);
			imp.setRoi(new PointRoi(xpoints,ypoints,counter));
		}
		for(int i=0;i<counter;i++){
			double temp = table.getValue("Slice", i);
			slice.add(temp);
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
		coordinates[0]=xArray;
		coordinates[1]=yArray;
		coordinates[2]=zArray;
		return coordinates;
	}
}