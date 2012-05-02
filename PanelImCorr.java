
//add Licence GPL and description of the plugin and his authors
import ij.IJ;

import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.JCheckBox;
import javax.swing.JPanel;


@SuppressWarnings({ "serial"})

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

