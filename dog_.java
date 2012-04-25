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

public class dog_ implements PlugIn {

	ImageCalculator ic;
	ResultsTable table;
	int counter=0;
	int[] xpoints;
  	int[] ypoints;

	public void run(String arg) {
		//IJ.selectWindow("ech34-1d1-oprm-popc-10e-01.dm3");
		ImagePlus imp = WindowManager.getCurrentImage();
		 if (imp==null){IJ.noImage(); return;}
		//ImageProcessor ip = imp.getProcessor();
		//ImageProcessor ip1 =ip.duplicate();
		//ImageProcessor ip2 =ip.duplicate();
		ImagePlus imp1=new Duplicator().run(imp);		
		ImagePlus imp2= new Duplicator().run(imp1);
		IJ.run(imp1, "Gaussian Blur...", "sigma=20");
		IJ.run(imp2, "Gaussian Blur...", "sigma=15");
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp2, imp1);
		//IJ.run(imp3, "Invert LUT", "");
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
	}

}
