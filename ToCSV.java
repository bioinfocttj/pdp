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
	
	static void generateCsvFile(double[][] coords){
			
			ResultsTable result = new ResultsTable(); //result table
			int counter = coords[0].length;
			for (int i=0; i<counter; i++){
				// Getting X values
				double posx = coords[0][i];
				
				// Getting Y values
				double posy = coords[1][i];
				
				// Getting Z (slices) values
				double posz = coords[2][i];
				
				result.incrementCounter();
				result.addValue("X",posx);
				result.addValue("Y",posy);
				result.addValue("Slice",posz);
			}
			result.show("result");
	}
}