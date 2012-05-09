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
	private static String minRadius;
	private static String maxRadius;
	private static String incRadius;
	private static String setpointRMin = "Minimum Radius : ";
	private static String setpointRMax = "Maximum Radius : ";
	private static String setpointRInc = "Radius Incrementation : ";
	private static String setpointWidth = "Square width : ";
	private static String setpointNoise = "Noise tolerance : ";

	private static JTextField radiusMinJTF;
	private static JTextField radiusMaxJTF;
	private static JTextField radiusIncJTF;
	private static JTextField cropWidthJTF;
	private static JTextField noiseToleranceJTF;

	private static JLabel orderRadiusMin;
	private static JLabel orderRadiusMax;
	private static JLabel orderRadiusInc;
	private static JLabel orderWidth;
	private static JLabel orderNoise;
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		// Instructions
		orderRadiusMin = new JLabel(setpointRMin);
		orderRadiusMax= new JLabel(setpointRMax);
		orderRadiusInc= new JLabel(setpointRInc);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		radiusMinJTF = new JTextField("020");
		radiusMaxJTF= new JTextField("060");
		radiusIncJTF = new JTextField("005");
		cropWidthJTF = new JTextField("100");
		noiseToleranceJTF = new JTextField("00.5");
		// Chekbox for the debug mode
		debugMode = new JCheckBox( "Debug"); 
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panel
		panel2.add(orderRadiusMin);
		panel2.add(radiusMinJTF);
		panel2.add(orderRadiusMax);
		panel2.add(radiusMaxJTF);
		panel2.add(orderRadiusInc);
		panel2.add(radiusIncJTF);
		panel2.add(orderNoise);
		panel2.add(noiseToleranceJTF);
		panel2.add(orderWidth);
		panel2.add(cropWidthJTF);
		panel2.add(debugMode);
		panel2.add(cropperMode);
		return panel2;
	}
	
	public static void setAttribute(){
		// Getting data entered by user
		widthCrop = cropWidthJTF.getText();
		toleranceNoise = noiseToleranceJTF.getText();
		minRadius = radiusMinJTF.getText();
		maxRadius = radiusMaxJTF.getText();
		incRadius = radiusIncJTF.getText();
		
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
		Attributes.setAttributes("radiusMin", minRadius);
		Attributes.setAttributes("radiusMax", maxRadius);
		Attributes.setAttributes("radiusInc", incRadius);
		Attributes.setAttributes("cropWidth", widthCrop);
		Attributes.setAttributes("noiseTolerance", toleranceNoise);
	}
}

