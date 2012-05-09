//add Licence GPL and description of the plugin and his authors

import ij.text.*;

import java.awt.event.*;

class InfoHelpViewerPickPlug implements ActionListener{
	/*Penser a mettre les infos et description d'aide!!!*/
	private String description="HELP ABOUT \"****????****\"\n\n"+
		"This plugin allows to pick particles on cryo-Met images. \n"+
		"choose a picking alorithm;\n"+
		"1) Click on the button \"Preview\" to have an idea of your job;\n"+
		"2) Click on the button \"Apply\" to definitively apply modifications to the image;\n"+
		"3) Click on the button \"Help & Info\" to have some help and informations;\n"+
		"ABOUT THE AUTHORS\n"+
		"This plugin was implemented by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		"under Professor Jean-Christophe Taveau's supervision.\n";

	public void actionPerformed(ActionEvent e) {
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Picking Plugin\"", sb.toString(), 800, 400);
		tw.setVisible(true);
	}
}

