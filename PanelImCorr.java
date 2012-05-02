
//add Licence GPL and description of the plugin and his authors

import ij.IJ;
import ij.gui.GenericDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

@SuppressWarnings({ "serial", "unused" })

class PanelImCorr extends Panel2 {

	static JCheckBox debugMode;
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		debugMode = makeJCheckBox("debug");
		panel2.add(debugMode);
		return panel2;
	}
	
	public static Hashtable<String, String> getvalue(){
		Hashtable<String, String> result = new Hashtable<String, String>();

		if (debugMode.isSelected()) {
			result.put("debug","true");
		}//essai git
		else {

			result.put("debug","false");
		}
		return result;
	}

	public void actionPerformed(ActionEvent event) {
	}
	
	
}

