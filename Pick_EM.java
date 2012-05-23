/*
*Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
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

import java.util.Hashtable;
import java.util.StringTokenizer;

import ij.Macro;
import ij.plugin.frame.PlugInFrame;
/**
 * 
 *@author FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
 *@version 1.7
 * 
 */
@SuppressWarnings("serial")

public final class Pick_EM extends PlugInFrame {
	// This class is initiate the plugin
	
	private String[] params = {
			"algo",
			"sig1",
			"sig2",
			"iter1",
			"iter2",
			"rMin",
			"rMax",
			"rInc",
			"noise",
			"squareWidth",
			"debug",
			"crop"
	};
		
	public void run(String args) {
		try {
			if (args != null) {
				StringTokenizer token1; // Declare StringTokenizer Objects
				token1 = new StringTokenizer(args); //Split on Space (default)
				while (token1.hasMoreTokens()) {
					String str = token1.nextToken();
					Attributes.getInstance();
					for (int j = 0; j<params.length; j++) {
						String pattern = params[j];
						if (str.lastIndexOf(pattern) > -1) { 
							int pos = str.lastIndexOf(pattern) + pattern.length()+1;
							 String param = str.substring(pos);
							 Attributes.setAttributes(params[j],param);
						}
					}
				}
				
				Hashtable<String, String> hash = Attributes.getAttributes();
				String algo = hash.get("algo");
				Attributes.getInstance();
				if (algo.equals("Difference_of_Gaussian")){
					DoG.sliceSelection();
				}

				else if (algo.equals("Image_Correlation")){
					ImCorr.sliceSelection();
				}

				else if (algo.equals("Dilate_Difference")){
					DilateDiff.sliceSelection();
				}
			}
		}
		catch (NullPointerException e1) {
			new PickFrame();
		}
	}
		
	public Pick_EM() {
		super("Pick_EM");
		String args = Macro.getOptions();
		run(args);
	}
}