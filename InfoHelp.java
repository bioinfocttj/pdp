//add Licence GPL and description of the plugin and his authors
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
	private String description="HELP ABOUT \"****????****\"\n\n"+
		"This plugin allows to pick particles on cryo-Met images. \n"+
		"choose a picking alorithm;\n"+
		"1) Click on the button \"Preview\" to have an idea of your job;\n"+
		"2) Click on the button \"Apply\" to definitively apply modifications to the image;\n"+
		"3) Click on the button \"Help & Info\" to have some help and informations;\n"+
		"ABOUT THE AUTHORS\n"+
		"This plugin was created by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		" Co-working with Pr. Jean-Christophe Taveau.\n";

	public void actionPerformed(ActionEvent e) {
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Picking Plugin\"", sb.toString(), 800, 400);
		tw.setVisible(true);
	}
}

