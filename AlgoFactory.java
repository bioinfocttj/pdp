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

import javax.swing.JPanel;

public final class AlgoFactory {
	
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
		
		static void getPicker(String picker){
			algorithm pick = algorithm .valueOf(picker);
			switch (pick) {
				case Difference_of_Gaussian : break;
				case Dilate_Difference :break;
				case Image_Correlation : break;
				case About_Pick_EM : break;
				default: break;
			}
		}

		static JPanel getPickPanel(String panel){
			algorithm panelValue = algorithm .valueOf(panel);
			switch (panelValue) {
				case Difference_of_Gaussian : return PanelDoG.create(); 
				case Dilate_Difference : return PanelDilateDiff.create();
				case Image_Correlation : return PanelImCorr.create();
				case About_Pick_EM : return About.create();
				default: return About.create();
			}
		}
	}
}
