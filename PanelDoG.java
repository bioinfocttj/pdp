
//add Licence GPL and description of the plugin and his authors
import ij.IJ;
<<<<<<< HEAD

=======
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782
import java.awt.event.*;

import java.util.Hashtable;

<<<<<<< HEAD
import javax.swing.JCheckBox;
=======
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

<<<<<<< HEAD
@SuppressWarnings({ "serial" })
=======

@SuppressWarnings({ "serial"})
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782

class PanelDoG extends Panel2 /*implements ActionListener*/{

	private static String consigneS1 = "Sigma 1 : ";
	private static String consigneS2 = "Sigma 2 : ";
	private static String consignewidth = "Square width : ";
	static JCheckBox debugMode;
	
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
		debugMode = makeJCheckBox("debug");
		//panel2.addNumericField("x Radius", sigma1, 0);
		//panel2.addNumericField("y Radius", sigma2, 0);
		
		//GenericDialog gd = new GenericDialog("Enter the values");
		//gd.addStringField("Title: ", title);
		//gd.addNumericField("Sigma 1: ", sigma1, 0);
		//gd.addNumericField("Sigma 2: ", sigma2, 0);
		//gd.showDialog();
		panel2.add(debugMode);
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

		if (debugMode.isSelected()) {
			result.put("debug","true");
			IJ.showMessage("Debug mode on");
		}
		else {
			result.put("debug","false");
			IJ.showMessage("Debug mode off");
		}
		
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

