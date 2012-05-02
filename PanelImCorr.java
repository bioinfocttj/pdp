
//add Licence GPL and description of the plugin and his authors
import ij.IJ;
<<<<<<< HEAD
=======

>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.JCheckBox;
<<<<<<< HEAD
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings({ "serial" })
=======
import javax.swing.JPanel;


@SuppressWarnings({ "serial"})
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782

class PanelImCorr extends Panel2 {

	static JCheckBox debugMode;
	static JTextField squareWidth;

	private static String consignewidth = "Square width : ";
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		JLabel c3 = new JLabel(consignewidth);
		squareWidth = makeJTextField("100");
		debugMode = makeJCheckBox("debug");
		panel2.add(debugMode);
		panel2.add(c3);
		panel2.add(squareWidth);
		return panel2;
	}
	
	public static Hashtable<String, String> getvalue(){
		Hashtable<String, String> result = new Hashtable<String, String>();

		if (debugMode.isSelected()) {
			result.put("debug","true");
			IJ.showMessage("on");
		}
		else {
			result.put("debug","false");
			IJ.showMessage("off");
		}
		String w = squareWidth.getText();
		result.put("squareWidth", w);
		return result;
	}

	public void actionPerformed(ActionEvent event) {
	}
	
	
}

