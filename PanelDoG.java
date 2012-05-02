
//add Licence GPL and description of the plugin and his authors
import ij.IJ;
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings({ "serial"})

class PanelDoG extends Panel2 /*implements ActionListener*/{

	private static String consigneS1 = "Sigma 1 : ";
	private static String consigneS2 = "Sigma 2 : ";
	private static String consignewidth = "Square width : ";
	
	static String title="Enter the values of sigmas";
	static JTextField sigma1;
	static JTextField sigma2;
	static JTextField squareWidth;
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		JLabel c1 = new JLabel(consigneS1);
		JLabel c2 = new JLabel(consigneS2);
		JLabel c3 = new JLabel(consignewidth);
		sigma1 = makeJTextField("20");
		sigma2 = makeJTextField("15");
		squareWidth = makeJTextField("100");
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
		panel2.add(c3);
		panel2.add(squareWidth);
		return panel2;
	}
	
	public static Hashtable<String, String> getvalue(){
		String s1 = sigma1.getText();
		String s2 = sigma2.getText();
		String w = squareWidth.getText();
		Hashtable<String, String> result = new Hashtable<String, String>();
		result.put("sigma1",s1);
		result.put("sigma2",s2);
		result.put("width", w);
		return result;
	}
		
	public void actionPerformed(ActionEvent event) {
		//String sigma1 = ((JTextComponent)event.getSource()).getText();
		//IJ.showMessage(sigma1);
	}
	
	
}

