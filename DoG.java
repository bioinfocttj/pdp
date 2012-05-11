//add Licence GPL and description of the plugin and his authors

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

abstract class DoG implements Picker {

	static Vector[] resultstable = new Vector[3];
	static Vector<Double> xtab=new Vector<Double>();
	static Vector<Double> ytab=new Vector<Double>();
	static Vector<Double> Slice=new Vector<Double>();
	
	DoG(){
	}
	
	static double[][] sliceSelection(){
		
		ImagePlus im=WindowManager.getCurrentImage();
		int nbslice=im.getStackSize();
		for (int a=1;a<=nbslice;a++){
			pick(im,a);
		}
		//cast the vector in an array so as to send it to the cropper
		//resultConverter();
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String cropMode = hashAttributes.get("crop");
		boolean cropperMode = Boolean.parseBoolean(cropMode);
		double[][]array= resultConverter();
		if (cropperMode) {
			Cropper cropper = new Cropper(im,array);
			cropper.crop();
		}
		return array;
		
	}

	public static void pick(ImagePlus imp, int currentslice){
		//IJ.showMessage("Picker.pick DoG");
		
		ImageCalculator ic;
		ResultsTable table;
		int counter=0;
		int[] xpoints;
		int[] ypoints;
		
		Hashtable<String, String> hashAttributes = Attributes.getAttributes();
		String sigma1 = hashAttributes.get("sigma1");
		String sigma2 = hashAttributes.get("sigma2");
		String noiseT = hashAttributes.get("noiseTolerance");
		
		imp.setSlice(currentslice);
		ImagePlus imp1 = new Duplicator().run(imp);
		imp1.setSlice(currentslice);
		ImagePlus imp2 = new Duplicator().run(imp1);
		
		String si1 = "sigma=" + sigma1;
		String si2 = "sigma=" + sigma2;
		String noise = "noise=" + noiseT+" output=List";

		imp1.setSlice(currentslice);
		IJ.run(imp1, "Gaussian Blur...", si1);
		imp2.setSlice(currentslice);
		IJ.run(imp2, "Gaussian Blur...", si2);
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create 32-bit", imp2, imp1);
		imp3.show();
		IJ.run(imp3, "Find Maxima...", noise);
		table = Analyzer.getResultsTable();
		counter = table.getCounter();
		xpoints = new int[counter];
		ypoints = new int [counter];
		for (int i=0; i<counter; i++){
			double x = table.getValue("X",i);
			double y = table.getValue("Y",i);
			int xx = (int) x;
			int yy = (int) y;
			//IJ.showMessage("x="+x+",y="+y);
			xpoints[i] = xx;
			ypoints[i] = yy;
			xtab.add(x);
			ytab.add(y);
			imp3.close();
			imp.setRoi(new PointRoi(xpoints, ypoints, counter));
		}
		
		imp.show();
		

		//IJ.run("Clear Results");
		IJ.run("Set Measurements...", " centroid stack redirect=None decimal=3");
		IJ.run("Measure");
		
		ResultsTable finalresults = Analyzer.getResultsTable();//table with y,y and slice
		int count=finalresults.getCounter();
		//System.out.println(count);
		for(int i=0;i<count;i++){
			double temp = finalresults.getValue("Slice", i);
			Slice.add(temp);
		}
		resultstable[0]= xtab;
		resultstable[1]= ytab;
		resultstable[2] = Slice;
		//printResultTable(resultstable);
		//return resultstable;
	}
	
	static double[][] resultConverter(){
		int arrayLength=xtab.size();
		Object[] tempX = new String[arrayLength];
		Object[] tempY = new String[arrayLength];
		Object[] tempZ = new String[arrayLength];
		tempX = xtab.toArray();
		tempY = ytab.toArray();
		tempZ = Slice.toArray();
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
	
	
	
	/*static void printResultTable(Vector[] resulttable){
		int zero = resulttable[0].size();
		int un = resulttable[0].size();
		int deux = resulttable[0].size();
		String longueurs = "0,"+zero+",1,"+un+",2,"+deux;
		IJ.showMessage(longueurs);
		for (int i=0;i<resulttable[1].size();i++){
			for (int j=0;j<resulttable.length;j++){
				System.out.println("lala");
				System.out.print(resulttable[j]);
				System.out.println("\n");
			}
		}
	}*/
}
