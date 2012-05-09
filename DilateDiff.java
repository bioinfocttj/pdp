//add Licence GPL and description of the plugin and his authors

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
	
	static Vector[] resultstable = new Vector[3];
	static Vector xtab=new Vector();
	static Vector ytab=new Vector();
	static Vector Slice=new Vector();	

	
	DilateDiff(){
	}
	
	static void pick(ImagePlus image, int currentslice){
		//IJ.showMessage("Picker.pick Dilatation Difference ");
		
		ImageCalculator ic;
		ResultsTable table;
		int counter=0;
		int[] xpoints;
		int[] ypoints;
	
		ImagePlus imp = WindowManager.getCurrentImage();
		ImagePlus imp1=new Duplicator().run(imp);
		ImagePlus imp2= new Duplicator().run(imp1);
		imp1.setSlice(currentslice);
		imp2.setSlice(currentslice);
		IJ.run(imp1, "Make Binary", "");
		IJ.run(imp2, "Make Binary", "");
		IJ.run(imp1, "Options...", "iterations=1 count=1 edm=Overwrite do=Nothing");
		IJ.run(imp1, "Dilate", "");
		IJ.run(imp2, "Options...", "iterations=2 count=1 edm=Overwrite do=Nothing");
		IJ.run(imp2, "Dilate", "");
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp2, imp1);
		IJ.run(imp3, "Find Maxima...", "noise=10 output=List");
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
		ResultsTable finalresults = Analyzer.getResultsTable();//table with y,y and slice
		int count=finalresults.getCounter();

		for(int i=0;i<count;i++){
			double temp = finalresults.getValue("Slice", i);
			Slice.add(temp);
		}
		
	}

	static Vector[] sliceSelection(){
		
		ImagePlus im=WindowManager.getCurrentImage();
		int nbslice=im.getStackSize();
		for (int a=1;a<=nbslice;a++){
			pick(im, a);
		}
		//printResultTable(resultstable);
		return resultstable;
	}
	
	static void printResultTable(Vector[] resulttable){
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
	}

}

