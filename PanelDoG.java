
//add Licence GPL and description of the plugin and his authors

import ij.IJ;
import ij.gui.GenericDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

@SuppressWarnings({ "serial", "unused" })

class PanelDoG extends Panel2 /*implements ActionListener*/{

	private static String consigne1 = "Sigma 1 : ";
	private static String consigne2 = "Sigma 2 : ";
	
	static String title="Enter the values of sigmas";
	static JTextField sigma1;
	static JTextField sigma2;
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		JLabel c1 = new JLabel(consigne1);
		JLabel c2 = new JLabel(consigne2);
		sigma1 = makeJTextField("20");
		sigma2 = makeJTextField("15");
		//panel2.addNumericField("x Radius", sigma1, 0);
		//panel2.addNumericField("y Radius", sigma2, 0);
		
		//GenericDialog gd = new GenericDialog("Enter the values");
		//gd.addStringField("Title: ", title);
		//gd.addNumericField("Sigma 1: ", sigma1, 0);
		//gd.addNumericField("Sigma 2: ", sigma2, 0);
		//gd.showDialog();
		panel2.add(c1);
		panel2.add(sigma1);
		panel2.add(c2);
		panel2.add(sigma2);
		return panel2;
	}
	
	public static Hashtable<String, String> getvalue(){
		String s1 = sigma1.getText();
		String s2 = sigma2.getText();
		Hashtable<String, String> result = new Hashtable<String, String>();
		result.put("sigma1",s1);
		result.put("sigma2",s2);
		return result;
	}
		
	public void actionPerformed(ActionEvent event) {
		//String sigma1 = ((JTextComponent)event.getSource()).getText();
		//IJ.showMessage(sigma1);
	}
	
	
}

