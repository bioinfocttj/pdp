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

import ij.IJ;

import javax.swing.JPanel;

public final class AlgoFactory {
	// This class is used to select the good algorithm
	
	private static AlgoFactory instance = null;
	
	private AlgoFactory() {
		super();
	}

	public final static AlgoFactory getInstance() {
		if (AlgoFactory.instance == null) {
			synchronized(AlgoFactory.class) {
				if (AlgoFactory.instance == null) {
					AlgoFactory.instance = new AlgoFactory();
				}
			}
		}
		return AlgoFactory.instance;
	}

	public enum algorithm { 
		Difference_of_Gaussian, Dilate_Difference, Image_Correlation, About_Pick_EM ;
		
		static JPanel getPickPanel(String panel){
			algorithm panelValue = algorithm .valueOf(panel);
			switch (panelValue) {
				case Difference_of_Gaussian : 
					return PanelDoG.create(); 
				case Dilate_Difference : 
					return PanelDilateDiff.create();
				case Image_Correlation : 
					return PanelImCorr.create();
				case About_Pick_EM : 
					return About.create();
				default: 
					return About.create();
			}
		}
		
		static double[][] getPicker(String picker){
			algorithm pick = algorithm .valueOf(picker);
			switch (pick) {
				case Difference_of_Gaussian : 
					IJ.showStatus("Start of DoG picking");
					PanelDoG.setAttributes();
					return DoG.sliceSelection();
				case Dilate_Difference :
					IJ.showStatus("Start of Dilate Difference picking");
					PanelDilateDiff.setAttributes();
					return DilateDiff.sliceSelection();
				case Image_Correlation : 
					IJ.showStatus("Start of Image Correlation picking");
					PanelImCorr.setAttributes();
					return ImCorr.sliceSelection();
				case About_Pick_EM : return null;
				default:IJ.showMessage("unknown picking method, Difference of Gaussian applied instead");
				PanelDoG.setAttributes();
				return DoG.sliceSelection();
			}
		}
		
		static void getPickerPreview(String picker){
			algorithm pick = algorithm .valueOf(picker);
			IJ.showStatus("Start of Preview");
			switch (pick) {
				case Difference_of_Gaussian : 
					IJ.showStatus("Start of DoG picking");
					PanelDoG.setAttributes();
					DoG.picking();
					break;
				case Dilate_Difference :
					IJ.showStatus("Start of Dilate Difference picking");
					PanelDilateDiff.setAttributes();
					DilateDiff.picking();
					break;
				case Image_Correlation : 
					IJ.showStatus("Start of Image Correlation picking");
					PanelImCorr.setAttributes();
					ImCorr.picking();
					break;
				case About_Pick_EM : 
					break;
				default: 
					IJ.showMessage("error unknown picking method");
					break;
			}
		}
	}
}