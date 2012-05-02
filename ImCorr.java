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

import javax.swing.JPanel;

//add Licence GPL and description of the plugin and his authors


@SuppressWarnings({ "serial", "unused" })

class ImCorr extends PickPlug_ /*implements ActionListener*/{
	
	ImCorr(){
		super();
	}
	
	static void pick(){
		
		int w=2048; //image width
		int h=2048; //image heigh
		int radiusMin=20; //radius min of the draw circule
		int radiusMax=60; //radius max of the draw circule
		int radiusInc=5; //radius incrementation
		String name = "";
		ResultsTable table; //result table
		ResultsTable finalresults; //table with y,y and slice
		int counter =0; //count of the results
		int[] list;
		int[] pass;
		int[] xpoints;
		int[] ypoints;
		double[] xtab;
		double[] ytab;
		double[] Slice;
		double[][] resultstable;
		
		//creation of an image which contains a circle with different diameters
		ImagePlus image=WindowManager.getCurrentImage();
		for (int radius=radiusMin;radius<=radiusMax;radius=radius+radiusInc){
			ImagePlus imp = IJ.createImage("test2", "8-bit White", 2048, 2048, 1);
			imp.setRoi(new OvalRoi(1024-radius, 1024-radius, radius*2, radius*2));
			IJ.run(imp, "Draw", "");
			ImagePlus result = FFTMath.doMath(image,imp);
			result.show();
			IJ.run(result,"Enhance Contrast", "saturated=0 normalize");
			IJ.run(result,"Find Maxima...", "noise=0.5 output=[Point Selection]");
			IJ.run("Set Measurements...", "  min centroid stack redirect=None decimal=3");
			IJ.run("Measure");
			result.close();
		}
		
		table = Analyzer.getResultsTable();
		counter=table.getCounter();
		list=new int [counter];
		pass=new int[counter];
		int line1=0;
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
				for (int q=0;q<line1;q++){
					if (maxval == list[q]){cpt+=1;}
				}
				if (cpt==0){
					list[line1]=maxval;
					line1++;
				}
			}
			iterator --;
		}
		
		IJ.selectWindow("Stack1.jpg");
		ImagePlus imp2 = WindowManager.getCurrentImage();
		xpoints = new int[line1];
		ypoints = new int [line1];
		xtab= new double[line1];
		ytab= new double[line1];
		for (int l=1;l<line1;l++){
			int line2=list[l];
			double x=table.getValue("X",line2);
			double y=table.getValue("Y",line2);
			xtab[l] = x;
			ytab[l] = y;
			int xx = (int) x;
			int yy = (int) y;
			xpoints[l] = xx;
			ypoints[l] = yy;
			imp2.setRoi(new PointRoi(xpoints,ypoints,line1));
		}
		IJ.run("Clear Results");
		IJ.run("Measure");

		finalresults = Analyzer.getResultsTable();
		counter=finalresults.getCounter();
		Slice = new double[counter];
		for(int i=0;i<counter;i++){
			double temp = finalresults.getValue("Slice", i);
			Slice[i] = temp;
		}

		finalresults = Analyzer.getResultsTable();
		counter=finalresults.getCounter();
		Slice = new double[counter];
		for(int i=0;i<counter;i++){
			double temp = finalresults.getValue("Slice", i);
			Slice[i] = temp;
		}
		
		resultstable = new double[3][];
		resultstable[0] = xtab;
		resultstable[1]= ytab;
		resultstable[2] = Slice;
		
		//return panel2;
	}
}
