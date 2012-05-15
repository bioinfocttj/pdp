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
import java.util.Hashtable;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.plugin.Duplicator;
import ij.process.ImageProcessor;

public class Cropper {
	
	private static ImagePlus imp;
	private static ImagePlus img2;
	private static ImageStack ims;
	private static ImagePlus imgR;
	private static double[][] coordinates;
	
	private static int widthCrop;
	private static int impWidth;
	private static int impHeight;
	
	public Cropper(ImagePlus im, double[][] coords){
		coordinates = coords;
		impWidth = im.getWidth();
		impHeight = im.getHeight();
		Hashtable<String, String> hash = Attributes.getAttributes();
		widthCrop = Integer.parseInt(hash.get("cropWidth"));
		ims = new ImageStack(widthCrop,widthCrop);
		String stackName = im.getTitle();
		int nbslice=im.getStackSize();
		ImagePlus dupli;
		for (int a=1;a<=nbslice;a++){
			im.setSlice(a);
			IJ.run(im, "Duplicate...", stackName);
			dupli = WindowManager.getCurrentImage();
			imp = dupli;
			crop(a,stackName);
			imp.close();
		}
		showCrop();
	}

	public Cropper(){
		//only for debug
		ImagePlus blobs = new ImagePlus("/home/tomo/Bureau/M1_bioinfo/sanofi/blobs.gif");
		imp=blobs;
		imp.show();
		int blobsWidth = blobs.getWidth();
		int blobsHeight = blobs.getHeight();
		double coord[][] = new double[2][2];
		coord[0][0] = 136.4;
		coord[0][1] = 162.4;
		coord[1][0] = 99.4;
		coord[1][1] = 73.4;
		crop(1,"stack");
		showCrop();
	}

	public void crop(int currentSlice, String stackName) {
		String stackTitle = (String) "title=" + stackName;
		boolean debug = false;
		int counter = coordinates[0].length;
		for (int i= 0; i<counter ;i++){
			// Getting X values
			double posx = (double) coordinates[0][i];
			// Getting Y values
			double posy = (double) coordinates[1][i];
			// Getting Z (slices) values
			double posz = (double) coordinates[2][i];

			int x = (int) (posx - (widthCrop/2));
			int y = (int) (posy - (widthCrop/2));
			int z = (int) posz;
			if ( (posx - (widthCrop/2)) < 0 ) {
				if (debug){
				IJ.showMessage("x < 0");
				}
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
				if (z == currentSlice) {
					imp.setRoi(x, y, widthCrop, widthCrop); //select a square around the particle 
					Roi currentRoi = imp.getRoi();
					img2 = new Duplicator().run(imp);
					Calibration cal = img2.getCalibration();
					cal.xOrigin -= currentRoi.getBounds().x;
					cal.yOrigin -= currentRoi.getBounds().y;
					ImageProcessor imp2=img2.getProcessor();
					ims.addSlice(imp2);
				}
			}
			if (ims.getSize()!=0){
				imgR = new ImagePlus("Results", ims);
			}
		}
	}
	public void showCrop() {
		imgR.show();
	}
}
