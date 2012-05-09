//add Licence GPL and description of the plugin and his authors

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings({ "serial"})

class PanelDoG extends PickPanel {

	private static String setpointS1 = "Sigma 1 : ";
	private static String setpointS2 = "Sigma 2 : ";
	private static String setpointWidth = "Square width : ";
	private static String setpointNoise = "Noise tolerance : ";
	private static String sig1;
	private static String sig2;
	private static String widthSquare;
	private static String toleranceNoise;
	
	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;
	
	private static JTextField sigma1;
	private static JTextField sigma2;
	private static JTextField noiseTolerance;
	private static JTextField squareWidth;
	
	private static JLabel orderS1;
	private static JLabel orderS2;
	private static JLabel orderWidth;
	private static JLabel orderNoise;
	
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		// Instructions
		orderS1 = new JLabel(setpointS1);
		orderS2 = new JLabel(setpointS2);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		sigma1 = new JTextField("020");
		sigma2 = new JTextField("015");
		squareWidth = new JTextField("100");
		noiseTolerance = new JTextField("010");
		// Chekbox for the debug mode
		debugMode = new JCheckBox( "Debug" );
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panel
		panel2.add(orderS1);
		panel2.add(sigma1);
		panel2.add(orderS2);
		panel2.add(sigma2);
		panel2.add(orderNoise);
		panel2.add(noiseTolerance);
		panel2.add(orderWidth);
		panel2.add(squareWidth);
		panel2.add(debugMode);
		panel2.add(cropperMode);
		return panel2;
	}
	
	public static void setAttributes(){
		// Getting data entered by user
		sig1 = sigma1.getText();
		sig2 = sigma2.getText();
		widthSquare = squareWidth.getText();
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
		
		Attributes.setAttributes("sigma1",sig1);
		Attributes.setAttributes("sigma2",sig2);
		Attributes.setAttributes("cropWidth", widthSquare);
		Attributes.setAttributes("noiseTolerance", toleranceNoise);
	}
}

