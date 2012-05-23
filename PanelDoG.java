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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings({ "serial"})

public class PanelDoG extends PickPanel {

	private static String setpointS1 = "Sigma 1                : ";
	private static String setpointS2 = "Sigma 2                : ";
	private static String setpointWidth = "Square width    : ";
	private static String setpointNoise = "Noise tolerance : ";
	private static String sig1;
	private static String sig2;
	private static String widthSquare;
	private static String toleranceNoise;
	
	private static JCheckBox debugMode;
	private static JCheckBox cropperMode;
	
	private static JTextField sigma1JTF;
	private static JTextField sigma2JTF;
	private static JTextField noiseToleranceJTF;
	private static JTextField cropWidthJTF;
	
	private static JLabel orderS1;
	private static JLabel orderS2;
	private static JLabel orderWidth;
	private static JLabel orderNoise;
	private static JLabel helpImage;
	
	private static JPanel infoPanel;
	private static JPanel sigma1Panel;
	private static JPanel sigma2Panel;
	private static JPanel widthPanel;
	private static JPanel noisePanel;
	private static JPanel debugCropPanel;
	
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
//		panel2.setAlignmentX(LEFT_ALIGNMENT);
		//panel2.setBorder(BorderFactory.createLineBorder(Color.black));


		// Creations of subpanels
		infoPanel = new JPanel();
		sigma1Panel = new JPanel();
		sigma2Panel = new JPanel();
		widthPanel = new JPanel();
		noisePanel = new JPanel();
		debugCropPanel = new JPanel();
		// Instructions 
//		helpImage = new JLabel(" You need to open a DM3-Image or a stack.", JLabel.CENTER);
		orderS1 = new JLabel(setpointS1);
		orderS2 = new JLabel(setpointS2);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		sigma1JTF = new JTextField("20",4);
		sigma2JTF = new JTextField("15",4);
		cropWidthJTF = new JTextField("100",4);
		noiseToleranceJTF = new JTextField("10",4);
		// Chekbox for the debug mode and cropper
		debugMode = new JCheckBox( "Debug" );
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panels
		sigma1Panel.add(orderS1);
		sigma1Panel.add(sigma1JTF);
//		sigma1Panel.setAlignmentX(RIGHT_ALIGNMENT);

		sigma2Panel.add(orderS2);
		sigma2Panel.add(sigma2JTF);
//		sigma2Panel.setAlignmentX(RIGHT_ALIGNMENT);

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
		panel2.add(sigma1Panel);
		panel2.add(sigma2Panel);
		panel2.add(widthPanel);
		panel2.add(noisePanel);
		panel2.add(debugCropPanel);
		return panel2;
	}
	
	public static void setAttributes(){
		// Getting data entered by user
		sig1 = sigma1JTF.getText();
		sig2 = sigma2JTF.getText();
		widthSquare = cropWidthJTF.getText();
		toleranceNoise = noiseToleranceJTF.getText();

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
		
		Attributes.setAttributes("sig1",sig1);
		Attributes.setAttributes("sig2",sig2);
		Attributes.setAttributes("squareWidth", widthSquare);
		Attributes.setAttributes("noise", toleranceNoise);
	}
}
