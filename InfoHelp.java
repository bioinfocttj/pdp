/*
Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
import ij.text.*;

import java.awt.event.*;

class InfoHelp implements ActionListener{
	/*Penser a mettre les infos et description d'aide!!!*/
	private String description="HELP ABOUT Pick_EM (Electron Microscopy) \n\n"+
		"This plugin allows to pick particles on cryo-Met images. \n"+
		"choose a picking alorithm;\n"+
		"1) Click on the button \"Preview\" to have an idea of your job;\n"+
		"2) Click on the button \"Apply\" to definitively apply modifications to the image;\n"+
		"3) Click on the button \"Help & Info\" to have some help and informations;\n"+
		"\n"+
		"All algorithms have common parameters like noise tolerance for the precision of the particles \n" +
		"selection and the square width if you choose to use the crop option. \n"+
		"The debug mode permits to see all stages of the algorithms' process. \n " +
		"The crop mode permits to obtain a stack which contains all of the selected particles. \n" +
		"\n"+
		"DoG Algorithm : \n"+
		" 	This algorithm doesn't need a processed stack to do the particles selection. \n" +
		" 	Parameters are sigmas values (for the filters).\n" +
		"\n"+
		"Dilate Difference Algorithm : \n"+
		" 	This algorithm doesn't need a processed stack to do the particles selection. \n" +
		" 	Parameters are dilate iterations (for dilation cycles).\n" +
		"\n"+
		"Image Correlation Algorithm : \n"+
		" 	This algorithm needs a processed stack to do the particles selection (filters, brightness and contrast, \n" +
		" 	find edges and threshold). It needs the non-processed stack too. \n " +
		" 	Parameters are the differents radius of circles and the radius incrementation for the correlation. \n " +
		"\n"+
		"ABOUT THE AUTHORS\n"+
		"This plugin was created by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		" Co-working with Pr. Jean-Christophe Taveau.\n";

	public void actionPerformed(ActionEvent e) {
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Pick_EM\"", sb.toString(), 800, 400);
		tw.setVisible(true);
	}
}

