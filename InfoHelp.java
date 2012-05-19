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
import ij.text.*;
import java.awt.event.*;

public class InfoHelp implements ActionListener{
	// This class displays a help about the utilization of the plugin
	
	private String description="HELP ABOUT Pick_EM (Electron Microscopy) \n\n"+
		"This plugin allows to pick particles on cryo-Met images. \n"+
		"choose a picking alorithm;\n"+
		"1) Click on the button \"Preview\" to have an idea of the picking results;\n"+
		"2) Click on the button \"Apply\" to definitively apply modifications to the image;\n"+
		"3) Click on the button \"Show Results\" to see the table which contains the coordinates\n"+
		"    and slices of the selected particles;\n"+
		"\n"+
		"All algorithms have common parameters like noise tolerance for the precision of \n" +
		"the particles selection and the square width if you choose to use the crop option. \n"+
		"The debug mode allows the user to see some information message if they're implemented. \n" +
		"The crop mode returns a stack with the selected particles. \n" +
		"\n"+
		"DoG Algorithm : \n"+
		"   This algorithm needs unprocessed stack/dm3 image to do the particles selection. \n" +
		"   Equalization or normalisation are not supported by this algorithm. \n"+
		"   Parameters are sigmas values (for the filters).\n" +
		"   Sigma1 must be greater than Sigma2 \n" +
		"\n"+
		"Dilate Difference Algorithm : \n"+
		"   This algorithm needs unprocessed stack or image. \n" +
		"   Parameters are dilate iterations (for dilation cycles). \n" +
		"   Dilate iteration image1 must be less than Dilate iteration image2. \n" +
		"\n"+
		"Image Correlation Algorithm : \n"+
		"   This algorithm needs a processed stack or image (for our trials we used three median filters (radius 2), \n"+
		"   Parameters are the differents radius of circles and the radius incrementation for the correlation. \n " +
		"\n"+
		"TO ADD AN NEW ALGORITHM TO THIS PLUGIN : \n"+
		"   See the file named AddAlgoPickEM.pdf. \n" +
		"ABOUT THE AUTHORS\n"+
		"   This plugin was created by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		"   Co-working with Pr. Jean-Christophe Taveau.\n";

	public void actionPerformed(ActionEvent e) {
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Pick_EM\"", sb.toString(), 800, 400);
		tw.setVisible(true);
	}
}