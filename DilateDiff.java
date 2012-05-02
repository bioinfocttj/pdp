import ij.*;
import ij.measure.*;
import ij.gui.*;
import ij.plugin.*;
import ij.plugin.filter.*;

//add Licence GPL and description of the plugin and his authors


<<<<<<< HEAD
@SuppressWarnings({ "serial" })
=======
@SuppressWarnings({ "serial"})
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782

class DilateDiff extends PickPlug_ /*implements ActionListener*/{
	
	DilateDiff(){
		super();
	}
	
	static void pick(){
		
		ImageCalculator ic;
		ResultsTable table;
		int counter=0;
		int[] xpoints;
		int[] ypoints;
	
		ImagePlus imp = WindowManager.getCurrentImage();
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
		
		//return panel2;
	}

	/*static ActionListener actionL = new ActionListener () {
		public void actionPerformed(ActionEvent actionE) {
			JButton button = (JButton) actionE.getSource();
			IJ.showMessage("Do it yourself !");
		}
	};*/
}

