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
import java.util.Hashtable;
import java.util.Vector;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.PointRoi;
import ij.measure.ResultsTable;
import ij.plugin.Duplicator;
import ij.plugin.ImageCalculator;
import ij.plugin.filter.Analyzer;

abstract class DilateDiff implements Picker{
	
	static Vector<Double> xtab=new Vector<Double>();
	static Vector<Double> ytab=new Vector<Double>();
	static Vector<Double> slice=new Vector<Double>();	

	DilateDiff(){}
	
	static void picking() {
		ImagePlus im = WindowManager.getCurrentImage();
		pick(im, 1);
		xtab.removeAllElements();
		ytab.removeAllElements();
		slice.removeAllElements();
	}
	
	static double[][] sliceSelection(){
		
		ImagePlus im=WindowManager.getCurrentImage();
		String stackName = im.getTitle();
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
	static void pick(ImagePlus image, int currentslice){
		//IJ.showMessage("Picker.pick Dilatation Difference ");
		
		ImageCalculator ic;
		ResultsTable table;
		int counter=0;
		int[] xpoints;
		int[] ypoints;

		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String iteration1 = hashAttributes.get("iteration1");
		String iteration2 = hashAttributes.get("iteration2");

		String it1 = "iterations=" + iteration1+" count=1 edm=Overwrite do=Nothing";
		String it2 = "iterations=" + iteration2+" count=1 edm=Overwrite do=Nothing";
		String noiseT = hashAttributes.get("noiseTolerance");
		String noise = "noise=" + noiseT+" output=List";
		
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
		IJ.run(imp3, "Find Maxima...", noise);
		table = Analyzer.getResultsTable();
		counter=table.getCounter();
		xpoints = new int[counter];
		ypoints = new int [counter];
		
		for (int i=0;i<counter;i++){
			double x=table.getValue("X",i);
			double y=table.getValue("Y",i);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[i] = xx;
			ypoints[i] = yy;
			xtab.add(x);
			ytab.add(y);
			imp.setRoi(new PointRoi(xpoints,ypoints,counter));
		}
		imp.show();
		IJ.selectWindow("Results");
		IJ.run("Clear Results");
		IJ.run("Measure");
		ResultsTable finalresults = Analyzer.getResultsTable();//table with x,y and slice
		int count=finalresults.getCounter();
		for(int i=0;i<count;i++){
			double temp = finalresults.getValue("Slice", i);
			slice.add(temp);
		}
		
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
			//xArray[i] = Double.parseDouble(tempX[i]);
			//yArray[i] = Double.parseDouble(tempY[i]);
			//zArray[i] = Double.parseDouble(tempZ[i]);
		}
		double[][] coordinates = new double[3][arrayLength];
		coordinates[0]=xArray;
		coordinates[1]=yArray;
		coordinates[2]=zArray;
		return coordinates;
	}
	
	/*static void printResultTable(double[][] resultstable){
		int zero = resultstable[0].length;
		int un = resultstable[1].length;
		int deux = resultstable[2].length;
		String longueurs = "0,"+zero+",1,"+un+",2,"+deux;
		IJ.showMessage(longueurs);
		for (int i=0;i<resultstable.length;i++){
			for (int j=1;j<resultstable.length;j++){
				System.out.println("lala");
				System.out.print(resultstable[j]);
				System.out.println("\n");
			}
		}
	}*/

}

