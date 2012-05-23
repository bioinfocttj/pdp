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

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings({ "serial" })

public class PanelImCorr extends PickPanel {

	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;
	
	private static String widthCrop;
	private static String toleranceNoise;
	private static String minRadius;
	private static String maxRadius;
	private static String incRadius;
	private static String setpointRMin = "Minimum Radius          :   ";
	private static String setpointRMax = "Maximum Radius          :   ";
	private static String setpointRInc = "Radius Incrementation : ";
	private static String setpointWidth = "Square width           :   ";
	private static String setpointNoise = "Noise tolerance        :   ";

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
	private static JLabel helpImage;
	
	private static JPanel infoPanel;
	private static JPanel radiusMinPanel;
	private static JPanel radiusMaxPanel;
	private static JPanel radiusIncPanel;
	private static JPanel NoisePanel;
	private static JPanel widthPanel;
	private static JPanel debugCropPanel;
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
	//	GridLayout experimentLayout = new GridLayout(0,2);
	//	panel2.setLayout(experimentLayout);

		// Creations of subpanels
		infoPanel = new JPanel();
		radiusMinPanel = new JPanel();
		radiusMaxPanel = new JPanel();
		radiusIncPanel = new JPanel();
		widthPanel = new JPanel();
		NoisePanel = new JPanel();
		debugCropPanel = new JPanel();
		// Instructions
//		helpImage = new JLabel(" Need images to the second power ", JLabel.CENTER);
		orderRadiusMin = new JLabel(setpointRMin);
		orderRadiusMax = new JLabel(setpointRMax);
		orderRadiusInc = new JLabel(setpointRInc);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		
		// Values as default
		radiusMinJTF = new JTextField("20",3);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);
		radiusMaxJTF = new JTextField("60",3);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);
		radiusIncJTF = new JTextField("5",3);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);
		cropWidthJTF = new JTextField("100",4);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);
		noiseToleranceJTF = new JTextField("0.5",4);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);
		// Chekbox for the debug mode and cropper
		debugMode = new JCheckBox( "Debug"); 
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panel
//		infoPanel.add(helpImage);
		
		radiusMinPanel.add(orderRadiusMin);
		radiusMinPanel.add(radiusMinJTF);
//		radiusMinPanel.setAlignmentX(RIGHT_ALIGNMENT);

		
		radiusMaxPanel.add(orderRadiusMax);
		radiusMaxPanel.add(radiusMaxJTF);
//		radiusMaxPanel.setAlignmentX(RIGHT_ALIGNMENT);

		radiusIncPanel.add(orderRadiusInc);
		radiusIncPanel.add(radiusIncJTF);
//		radiusIncPanel.setAlignmentX(RIGHT_ALIGNMENT);

		NoisePanel.add(orderNoise);
		NoisePanel.add(noiseToleranceJTF);
//		NoisePanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		widthPanel.add(orderWidth);
		widthPanel.add(cropWidthJTF);
//		widthPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		debugCropPanel.add(debugMode);
		debugCropPanel.add(cropperMode);
		
		panel2.add(infoPanel);
		panel2.add(radiusMinPanel);
		panel2.add(radiusMaxPanel);
		panel2.add(radiusIncPanel);
		panel2.add(NoisePanel);
		panel2.add(widthPanel);
		panel2.add(debugCropPanel);
		return panel2;
	}
	
	public static void setAttributes(){
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
		Attributes.setAttributes("rMin", minRadius);
		Attributes.setAttributes("rMax", maxRadius);
		Attributes.setAttributes("rInc", incRadius);
		Attributes.setAttributes("squareWidth", widthCrop);
		Attributes.setAttributes("noise", toleranceNoise);
	}
}
