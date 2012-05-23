/*
*Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
*This file is part of Pick_EM program
*Pick_EM is free software; you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation; either version 2 of the License, or
*(at your option) any later version.
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*You should have received a copy of the GNU General Public License along
*with this program; if not, write to the Free Software Foundation, Inc.,
*51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelDilateDiff extends PickPanel {

	private static String setpointNoise = "Noise tolerance              : ";
	private static String toleranceNoise;
	private static String dilateIt1;
	private static String setpointIteration1 = "Dilate iteration image1 : ";
	private static String dilateIt2;
	private static String setpointIteration2 = "Dilate iteration image2 : ";
	private static String widthCrop;
	private static String setpointWidth = "Square width                 : ";
	
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
	private static JLabel helpImage;
	
	private static JPanel infoPanel;
	private static JPanel iteration1Panel;
	private static JPanel iteration2Panel;
	private static JPanel noisePanel;
	private static JPanel widthPanel;
	private static JPanel debugCropPanel;
	
	PanelDilateDiff() {
		super();
	}
	static JPanel create(){
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		// Creations of subpanels
		infoPanel = new JPanel();
		iteration1Panel = new JPanel();
		iteration2Panel = new JPanel();
		widthPanel = new JPanel();
		noisePanel = new JPanel();
		debugCropPanel = new JPanel();
		// Instructions
//		helpImage = new JLabel(" Alorithm optimized for Blobs ", JLabel.CENTER);
//		helpImage = new JLabel("", JLabel.CENTER);
		orderIteration1 = new JLabel(setpointIteration1);
		orderIteration2 = new JLabel(setpointIteration2);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		iteration1JTF = new JTextField("1",3);
		iteration2JTF = new JTextField("2",3);
		noiseToleranceJTF = new JTextField("3",3);
		cropWidthJTF = new JTextField("100",4);
		// Chekbox for the debug mode and the cropper
		debugMode = new JCheckBox( "Debug" ); 
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panels
//		infoPanel.add(helpImage);
		iteration1Panel.add(orderIteration1);
		iteration1Panel.add(iteration1JTF);
//		iteration1Panel.setAlignmentX(RIGHT_ALIGNMENT);

		iteration2Panel.add(orderIteration2);
		iteration2Panel.add(iteration2JTF);
//		iteration2Panel.setAlignmentX(RIGHT_ALIGNMENT);

		noisePanel.add(orderNoise);
		noisePanel.add(noiseToleranceJTF);
//		noisePanel.setAlignmentX(RIGHT_ALIGNMENT);

		widthPanel.add(orderWidth);
		widthPanel.add(cropWidthJTF);
//		widthPanel.setAlignmentX(RIGHT_ALIGNMENT);

		debugCropPanel.add(debugMode);
		debugCropPanel.add(cropperMode);
//		debugCropPanel.setAlignmentX(RIGHT_ALIGNMENT);

		panel2.add(infoPanel);
		panel2.add(iteration1Panel);
		panel2.add(iteration2Panel);
		panel2.add(noisePanel);
		panel2.add(widthPanel);
		panel2.add(debugCropPanel);
		return panel2;
	}
	public static void setAttributes(){
		// Getting data entered by user
		dilateIt1 = iteration1JTF.getText();
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
		Attributes.setAttributes("iter1", dilateIt1);
		Attributes.setAttributes("iter2", dilateIt2);
		Attributes.setAttributes("squareWidth", widthCrop);
		Attributes.setAttributes("noise", toleranceNoise);
	}
}
