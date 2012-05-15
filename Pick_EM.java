/*
Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
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
	
	private String[] params   = {
			"algo=",
			"sig1=",
			"sig2=",
			"dil1=",
			"dil2=",
			"minrad=",
			"maxrad=",
			"radinc=",
			"noise=",
			"sqwidth=",
			"debug=",
			"crop="
	};
	/*private float[] paramVals = {
			0,   // "algo=",
			0,   // "sig1=",
			0,   // "sig2=",
			0,   // "dil1=",
			0,   // "dil2=",
			0, // "minrad=",
			1,   // "maxrad=",
			1,   // "radinc=",
			1,   // "noise=",
			115, // "sqwidth=",
			-35, // "debug=",
			0    // "crop="
	};*/
		
		public void run(String args) {
			//String st = Macro.getOptions();
			IJ.showMessage(args);
			if (args != null) {
				StringTokenizer ex1; // Declare StringTokenizer Objects
				ex1 = new StringTokenizer(args); //Split on Space (default)
				while (ex1.hasMoreTokens()) {
					String str = ex1.nextToken();
					//IJ.showMessage(str);
					Attributes.getInstance();
					//int i = str.lastIndexOf("algo");
					//IJ.showMessage(Integer.toString(i));
					for (int j = 0; j<params.length; j++) {
						String pattern = params[j];
						if (str.lastIndexOf(pattern) > -1) { 
							int pos = str.lastIndexOf(pattern) + pattern.length();
							 String param = str.substring(pos);
							 IJ.showMessage(param);
							 Attributes.setAttributes(params[j],param);
							//IJ.log("" + params[j] + ": " + paramVals[j]); 
						}
					}
				}
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