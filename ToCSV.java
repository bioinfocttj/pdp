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

import ij.measure.ResultsTable;
 
public class ToCSV {
	// This class allows to generate a resultstable which can be saved to a csv file using ImageJ
	
	static void generateCsvFile(double[][] coords){
		
		ResultsTable result = new ResultsTable(); 
		int counter = coords[0].length;
		for (int i=0; i<counter; i++){
			// Getting X values
			int posx = (int) coords[0][i];
			
			// Getting Y values
			int posy = (int) coords[1][i];
			
			// Getting Z (slices) values
			int posz = (int) coords[2][i];
			
			result.incrementCounter();
			result.addValue("X",posx);
			result.addValue("Y",posy);
			result.addValue("Slice",posz);
		}
		result.show("result");
	}
}