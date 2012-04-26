import ij.*;
import ij.measure.*;
import ij.process.*;
import ij.gui.*;
import ij.gui.Roi.*;
import ij.gui.PolygonRoi.*;
import ij.gui.PointRoi.*;
import ij.plugin.*;
import ij.plugin.filter.*;
import ij.plugin.frame.*;
import java.lang.*;
import java.awt.*;
import java.io.PrintStream;

public class dilate_diff implements PlugIn {

	ImageCalculator ic;
	ResultsTable table;
	int counter=0;
	int[] xpoints;
  	int[] ypoints;

	public void run(String arg) {
		ImagePlus imp = WindowManager.getCurrentImage();
		if (imp==null){IJ.noImage(); return;}
		ImagePlus imp1=new Duplicator().run(imp);
		ImagePlus imp2= new Duplicator().run(imp1);
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
		int iterator=counter-1;
		xpoints = new int[counter];
    		ypoints = new int [counter];
		for (int i=0;i<counter;i++){
			double x=table.getValue("X",i);
     		 	double y=table.getValue("Y",i);
     			int xx = (int) x;
      			int yy = (int) y;
      			xpoints[i] = xx;
     			ypoints[i] = yy;
    			imp.setRoi(new PointRoi(xpoints,ypoints,counter));
		}
		imp.show();
		IJ.selectWindow("Results");
 		IJ.run("Clear Results");
		IJ.run("Measure");	
	}

}