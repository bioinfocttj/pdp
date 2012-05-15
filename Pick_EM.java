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

import ij.IJ;
import ij.Macro;
import ij.plugin.frame.PlugInFrame;
/**
 * 
 * @author FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
 *@version 1.0
 * 
 */
@SuppressWarnings("serial")

public class Pick_EM extends PlugInFrame {
	
	private String[] params = {
			"algo",
			"sigma1",
			"sigma2",
			"iteration1",
			"iteration2",
			"radiusMin",
			"radiusMax",
			"radiusInc",
			"noiseTolerance",
			"cropWidth",
			"debug",
			"crop"
	};
		
		public void run(String args) {
			//IJ.showMessage(args);
			if (args != null) {
				StringTokenizer ex1; // Declare StringTokenizer Objects
				ex1 = new StringTokenizer(args); //Split on Space (default)
				while (ex1.hasMoreTokens()) {
					String str = ex1.nextToken();
					Attributes.getInstance();
					for (int j = 0; j<params.length; j++) {
						String pattern = params[j];
						if (str.lastIndexOf(pattern) > -1) { 
							int pos = str.lastIndexOf(pattern) + pattern.length()+1;
							 String param = str.substring(pos);
							 Attributes.setAttributes(params[j],param);
							 IJ.showMessage("cle");
							 IJ.showMessage(params[j]);
							 IJ.showMessage(param);
						}
					}
				}
				

				Hashtable<String, String> hash = Attributes.getAttributes();
				String algo = hash.get("algo");
				/*if (algo.equals("Difference_of_Gaussian")){
					Attributes.getInstance();
					DoG.picking();
				}

				else if (algo.equals("Image_Correlation")){
					Attributes.getInstance();
					ImCorr.picking();
				}

				else if (algo.equals("Dilate_Difference")){
					Attributes.getInstance();
					DilateDiff.sliceSelection();
				}*/
			}
			else{
				new PickFrame();
			}
		}
		
	public Pick_EM() {
		super("Pick Plug");
		String args = Macro.getOptions();
		run(args);
	}//run("Pick EM","algo=dog sigma1=1 sigma2=2 noise=3 debug=true");
}