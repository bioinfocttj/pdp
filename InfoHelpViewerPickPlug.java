
//add Licence GPL and description of the plugin and his authors
import ij.text.*;


import java.awt.event.*;

class InfoHelpViewerPickPlug implements ActionListener{
	/*Penser a mettre les infos et description d'aide!!!*/
    private String description="HELP ABOUT \"Picking Plugin\"\n\n"+
		"This plugin allows to pick particles on cryo-Met images. \n"+
		"User can:\n"+
		"1) To apply all pre-treatments on an image with ImageJ;\n"+
		"2) To choose a picking alorithm;\n"+
		"3) Click on the button \"Preview\" to have an idea of your job;\n"+
		"4) Click on the button \"Apply\" to definitively apply modifications to the image;\n"+
		"5) Click on the button \"Reset\" to undo the last operation;\n"+
		"6) Click on the button \"Help & Info\" to have some help and informations;\n"+
		"ABOUT THE AUTHORS\n"+
		"This plugin was implemented by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		"under Professor Jean-Christophe Taveau's supervision.\n";
  
	public void actionPerformed(ActionEvent e) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Picking Plugin\"", sb.toString(), 800, 400);
		tw.setVisible(true);
	}
}

