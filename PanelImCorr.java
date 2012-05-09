//add Licence GPL and description of the plugin and his authors

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings({ "serial" })

class PanelImCorr extends PickPanel {

	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;
	
	private static String widthCrop;
	private static String toleranceNoise;
	private static String setpointWidth = "Square width : ";
	private static String setpointNoise = "Noise tolerance : ";
	
	private static JTextField cropWidth;
	private static JTextField noiseTolerance;
	
	private static JLabel orderWidth;
	private static JLabel orderNoise;
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		// Instructions
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		cropWidth = new JTextField("100");
		noiseTolerance = new JTextField("003");
		// Chekbox for the debug mode
		debugMode = new JCheckBox( "Debug"); 
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panel
		panel2.add(orderNoise);
		panel2.add(noiseTolerance);
		panel2.add(orderWidth);
		panel2.add(cropWidth);
		panel2.add(debugMode);
		panel2.add(cropperMode);
		return panel2;
	}
	
	public static void setAttribute(){
		// Getting data entered by user
		widthCrop = cropWidth.getText();
		toleranceNoise = noiseTolerance.getText();
		
		if (debugMode.isSelected()) {
			Attributes.setAttributes("debug","true");
		}
		else {
			Attributes.setAttributes("debug","false");
		}
		
		if (cropperMode.isSelected()) {
			Attributes.setAttributes("crop","true");
		}
		else {
			Attributes.setAttributes("crop","false");
		}
		Attributes.setAttributes("cropWidth", widthCrop);
		Attributes.setAttributes("noiseTolerance", toleranceNoise);
	}
}

