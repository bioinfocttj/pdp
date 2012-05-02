import ij.gui.Roi;

import java.util.Hashtable;


public class Cropper {
	
	public Cropper(imp, coords) {
		IJ.run("Blobs (25K)");
		/* Récupérer les valeurs de l'image blobs
		 * création de coordonnées bidon pour remplir le tableau coords et pourvoir l'utiliser
		 * enlever la méthode stackCreator de PickPlug

		 */
		
	}
	
	public Cropper(){
		// only for debug
		//imp=
		
	}

	public void stackCreator(double [][] array) {
		for (int i= 0; i<array[0].length;i++){
			double posx = (double) array[0][i];
			double posy = (double) array[1][i];
			//IJ.showMessage((Double.toString(posx)));
			//double posz = (double) array[2][i];
			Hashtable<String, String> value = PanelImCorr.getvalue();
			int width = Integer.parseInt((String) value.get("squareWidth"));
			int x = (int) (posx - (width/2));
			int y = (int) (posy - (width/2));
			//IJ.showMessage((Integer.toString(x)));
			imp.setRoi(x, y, width, width); //select a square around the particle 
			
			//ImagePlus temp = imp.duplicate();
			//temp.show();
			//ImagePlus impTemp = new Duplicator().run(imp);
			Roi impTemp = imp.getRoi();
			//IJ.run(imp, "Images to Stack", "name=stack title=[] use");
			
			//stack.show();
		}
	}
}
