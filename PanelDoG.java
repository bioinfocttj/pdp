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

@SuppressWarnings({ "serial"})

public class PanelDoG extends PickPanel {

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
	private static JPanel sigmaPanel;
	private static JPanel widthNoisePanel;
	private static JPanel debugCropPanel;
	
	PanelDoG() {
		super();
	}
	
	static JPanel create(){
		// Creations of subpanels
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(450, 50));
		sigmaPanel = new JPanel();
		sigmaPanel.setPreferredSize(new Dimension(450, 50));
		widthNoisePanel = new JPanel();
		widthNoisePanel.setPreferredSize(new Dimension(450, 50));
		debugCropPanel = new JPanel();
		debugCropPanel.setPreferredSize(new Dimension(450, 50));
		// Instructions 
		helpImage = new JLabel(" You need to open a DM3-Image or a stack.", JLabel.CENTER);
		orderS1 = new JLabel(setpointS1);
		orderS2 = new JLabel(setpointS2);
		orderWidth = new JLabel(setpointWidth);
		orderNoise = new JLabel(setpointNoise);
		// Values as default
		sigma1JTF = new JTextField("20",3);
		sigma2JTF = new JTextField("15",3);
		cropWidthJTF = new JTextField("100",4);
		noiseToleranceJTF = new JTextField("10",3);
		// Chekbox for the debug mode and cropper
		debugMode = new JCheckBox( "Debug" );
		cropperMode = new JCheckBox( "Crop" );
		// Adding attributes to the panels
		infoPanel.add(helpImage);
		sigmaPanel.add(orderS1);
		sigmaPanel.add(sigma1JTF);
		sigmaPanel.add(orderS2);
		sigmaPanel.add(sigma2JTF);
		widthNoisePanel.add(orderNoise);
		widthNoisePanel.add(noiseToleranceJTF);
		widthNoisePanel.add(orderWidth);
		widthNoisePanel.add(cropWidthJTF);
		debugCropPanel.add(debugMode);
		debugCropPanel.add(cropperMode);
		panel2.add(infoPanel);
		panel2.add(sigmaPanel);
		panel2.add(widthNoisePanel);
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