
//add Licence GPL and description of the plugin and his authors

import ij.IJ;
import ij.gui.GenericDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

@SuppressWarnings({ "serial", "unused" })

class PanelDoG extends Panel2 /*implements ActionListener*/{

	//private static String consigne1 = "Enter here a value for sigma 1";
	//private static String consigne2 = "Enter here a value for sigma 2";
	
	//static String title="Enter the values of sigmas";
	
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		//JTextArea sigma1 = new JTextArea(consigne1);
		//JTextArea sigma2 = new JTextArea(consigne2);
		//panel2.addNumericField("x Radius", sigma1, 0);
       // panel2.addNumericField("y Radius", sigma2, 0);
		
		GenericDialog gd = new GenericDialog("Enter the values");
		//gd.addStringField("Title: ", title);
		gd.addNumericField("Sigma 1: ", sigma1, 0);
		gd.addNumericField("Sigma 2: ", sigma2, 0);
		gd.showDialog();
		//panel2.add(sigma1);
		//panel2.add(sigma2);
		return panel2;
	}

	public void actionPerformed(ActionEvent event) {
		String sigma1 = ((JTextComponent)event.getSource()).getText();
		IJ.showMessage(sigma1);
	}
	
	
}

