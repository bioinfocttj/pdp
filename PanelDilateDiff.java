//add Licence GPL and description of the plugin and his authors

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelDilateDiff extends PickPanel {

	private static String setpointNoise = "Noise tolerance : ";
	private static String toleranceNoise;
	private static String dilateIt1;
	private static String setpointIteration1 = "Dilate iteration image1 : ";
	private static String dilateIt2;
	private static String setpointIteration2 = "Dilate iteration image2 : ";
	private static String widthCrop;
	private static String setpointWidth = "Square width : ";
	
	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;

	private static JTextField iteration1JTF;
	private static JTextField iteration2JTF;
	private static JTextField noiseToleranceJTF;
	private static JTextField cropWidthJTF;

	private static JLabel orderIteration1;
	private static JLabel orderIteration2;
	private static JLabel orderNoise;
	private static JLabel orderWidth;
	
	PanelDilateDiff() {
		super();
	}
	static JPanel create(){
		// Instructions
		orderIteration1 = new JLabel(setpointIteration1);
		orderIteration2= new JLabel(setpointIteration2);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		iteration1JTF = new JTextField("001");
		iteration2JTF = new JTextField("002");
		noiseToleranceJTF = new JTextField("003");
		cropWidthJTF = new JTextField("100");
		// Chekbox for the debug mode
		debugMode = new JCheckBox( "Debug" ); 
		cropperMode = new JCheckBox( "Crop" );
		panel2.add(orderIteration1);
		panel2.add(iteration1JTF);
		panel2.add(orderIteration2);
		panel2.add(iteration2JTF);
		panel2.add(orderNoise);
		panel2.add(noiseToleranceJTF);
		panel2.add(orderWidth);
		panel2.add(cropWidthJTF);
		panel2.add(debugMode);
		panel2.add(cropperMode);
		return panel2;
	}
	public static void setAttributes(){
		// Getting data entered by user
		dilateIt1= iteration1JTF.getText();
		dilateIt2 = iteration2JTF.getText();
		toleranceNoise = noiseToleranceJTF.getText();
		widthCrop = cropWidthJTF.getText();
		
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
		Attributes.setAttributes("iteration1", dilateIt1);
		Attributes.setAttributes("iteration2", dilateIt2);
		Attributes.setAttributes("cropWidth", widthCrop);
		Attributes.setAttributes("noiseTolerance", toleranceNoise);
	}
}
