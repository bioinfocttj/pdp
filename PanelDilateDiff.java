//add Licence GPL and description of the plugin and his authors

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelDilateDiff extends PickPanel {

	private static String setpointNoise = "Noise tolerance : ";
	private static String toleranceNoise;
	private static String widthCrop;
	private static String setpointWidth = "Square width : ";
	
	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;
	
	private static JTextField noiseTolerance;
	private static JTextField cropWidth;
	
	private static JLabel noiseConsigne;
	private static JLabel orderWidth;
	
	PanelDilateDiff() {
		super();
	}
	static JPanel create(){
		// Instructions
		orderWidth = new JLabel(setpointWidth);
		noiseConsigne = new JLabel(setpointNoise);
		// Values as default
		noiseTolerance = new JTextField("003");
		cropWidth = new JTextField("100");
		// Chekbox for the debug mode
		debugMode = new JCheckBox( "Debug" ); 
		cropperMode = new JCheckBox( "Crop" );
		panel2.add(noiseConsigne);
		panel2.add(noiseTolerance);
		panel2.add(orderWidth);
		panel2.add(cropWidth);
		panel2.add(debugMode);
		panel2.add(cropperMode);
		return panel2;
	}
	public static void setAttributes(){
		// Getting data entered by user
		toleranceNoise = noiseTolerance.getText();
		widthCrop = cropWidth.getText();
		
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
