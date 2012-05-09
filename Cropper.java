//add Licence GPL and description of the plugin and his authors

import java.util.Hashtable;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.Duplicator;
import ij.plugin.filter.Analyzer;

public class Cropper {
	
	private static ImagePlus imp;
	private static ImagePlus img2;
	private static double[][] coordinates;
	private static int widthCrop;
	private static int impWidth;
	private static int impHeight;
	
	public Cropper(ImagePlus imp, double[][] coords){
		// coords = tableau de 3 vecteurs de doubles
		Cropper.imp = imp;
		coordinates = coords;
		IJ.showMessage("cropper avec arguments : vide pr l'instant");
		//img = IJ.getImage();
		impWidth = imp.getWidth();
		impHeight = imp.getHeight();
		Hashtable<String, String> hash = Attributes.getAttributes();
		widthCrop = Integer.parseInt(hash.get("cropWidth"));
		//crop();
		
	}

	public Cropper(){
		// only for debug
		imp = new ImagePlus("/home/tomo/Bureau/M1_bioinfo/sanofi/blobs.gif");
		imp.show();
		//int blobsWidth = blobs.getWidth();
		//int blobsHeight = blobs.getHeight();
		double coord[][] = new double[2][2];
		coord[0][0] = 136.4;
		coord[0][1] = 162.4;
		coord[1][0] = 99.4;
		coord[1][1] = 73.4;

		crop();
		
		//imp=
	}

	public void crop() {
		boolean debug = false;
		//ResultsTable table;
		ResultsTable table = Analyzer.getResultsTable();
		int counter = table.getCounter();
		for (int i= 0; i<counter ;i++){
		//for (int i= 0; i<20;i++){*
			// Getting X values
/*			
			Object obj = coordinates[0].elementAt(i);
			String str = obj.toString();
			double posx = Double.valueOf(str).doubleValue();
*/			
			double posx = (double) coordinates[i][0];
			
			// Getting Y values
/*			obj = coordinates[1].elementAt(i);
			str = obj.toString();
			double posy = Double.valueOf(str).doubleValue();
*/			
			double posy = (double) coordinates[i][1];
			
			// Getting Z (slices) values
/*			
			obj = coordinates[2].elementAt(i);
			str = obj.toString();
			double posz = Double.valueOf(str).doubleValue();
*/
			double posz = (double) coordinates[i][2];
			
			//double posx = table.getValue("X",i);
			//double posy = table.getValue("Y",i);
			//double posx = (double) array[i][0];
			//double posy = (double) array[i][1];
			//IJ.showMessage((Double.toString(posx)));
			//double posz = (double) array[2][i];
			int x = (int) (posx - (widthCrop/2));
			int y = (int) (posy - (widthCrop/2));     
			if ( (posx - (widthCrop/2)) < 0 ) {
				if (debug){
				IJ.showMessage("x < 0");}
			}
			else if ( (posx + (widthCrop/2)) > impHeight ) {
				if (debug){
				IJ.showMessage("x > impHeight");}
			}
			else if ( (posy + (widthCrop/2)) > impWidth ) {
				if (debug){
				IJ.showMessage("y > impWidth");}
			}
			else if ( (posy - (widthCrop/2)) < 0 ) {
				if (debug){
				IJ.showMessage("y < 0");}
			}
			else {
			//if ((x > 0) || (x < imp.getHeight()) || (y < imp.getWidth()) || (y > 0)){
					//IJ.showMessage((Integer.toString(x)));
				//IJ.showMessage(Integer.toString(x));
					imp.setRoi(x, y, widthCrop, widthCrop); //select a square around the particle 
					IJ.showMessage("progress");
					img2 = new Duplicator().run(imp);
					img2.show();
			}
			
		}
		IJ.showProgress(counter);
		IJ.run(imp, "Images to Stack", "name=stack title=[DUP] use");
		IJ.showMessage("progressbar");
	}
}
