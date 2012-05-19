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

import java.awt.Dimension;

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
	private static JLabel helpImage;
	
	private static JPanel infoPanel;
	private static JPanel radiusPanel;
	private static JPanel widthNoisePanel;
	private static JPanel debugCropPanel;
	
	PanelImCorr() {
		super();
	}
	
	static JPanel create(){
		// Creations of subpanels
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(450, 50));
		radiusPanel = new JPanel();
		radiusPanel.setPreferredSize(new Dimension(450, 50));
		widthNoisePanel = new JPanel();
		widthNoisePanel.setPreferredSize(new Dimension(450, 50));
		debugCropPanel = new JPanel();
		debugCropPanel.setPreferredSize(new Dimension(450, 50));
		// Instructions
		helpImage = new JLabel(" Need images to the second power ", JLabel.CENTER);
		orderRadiusMin = new JLabel(setpointRMin);
		orderRadiusMax = new JLabel(setpointRMax);
		orderRadiusInc = new JLabel(setpointRInc);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		radiusMinJTF = new JTextField("20",3);
		radiusMaxJTF = new JTextField("60",3);
		radiusIncJTF = new JTextField("5",3);
		cropWidthJTF = new JTextField("100",4);
		noiseToleranceJTF = new JTextField("0.5",4);
		// Chekbox for the debug mode and cropper
		debugMode = new JCheckBox( "Debug"); 
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panel
		infoPanel.add(helpImage);
		radiusPanel.add(orderRadiusMin);
		radiusPanel.add(radiusMinJTF);
		radiusPanel.add(orderRadiusMax);
		radiusPanel.add(radiusMaxJTF);
		radiusPanel.add(orderRadiusInc);
		radiusPanel.add(radiusIncJTF);
		widthNoisePanel.add(orderNoise);
		widthNoisePanel.add(noiseToleranceJTF);
		widthNoisePanel.add(orderWidth);
		widthNoisePanel.add(cropWidthJTF);
		debugCropPanel.add(debugMode);
		debugCropPanel.add(cropperMode);
		panel2.add(infoPanel);
		panel2.add(radiusPanel);
		panel2.add(widthNoisePanel);
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