import java.io.FileWriter;
import java.io.IOException;
 
public class ToCSV {
	
	private static double[][] coordinates;
		

	static void generateCsvFile(String sFileName, double[][] coords){
		try {
			
			FileWriter writer = new FileWriter(sFileName);
			// print in the previous directory
 
			writer.append("X");
			writer.append(',');
			writer.append("Y");
			writer.append(',');
			writer.append("Slice");
			writer.append('\n');
			
			coordinates = coords;
			
			int counter = coordinates[0].length;
			for (int i= 0; i<counter ;i++){
			
				// Getting X values
				double posx = (double) coordinates[0][i];
				
				// Getting Y values
				double posy = (double) coordinates[1][i];
				
				// Getting Z (slices) values
				double posz = (double) coordinates[2][i];
				
				writer.append(String.valueOf(posx));
				writer.append(',');
				writer.append(String.valueOf(posy));
				writer.append(',');
				writer.append(String.valueOf(posz));
				writer.append('\n');
			}
			
			//generate whatever data you want
			
			writer.flush();
			writer.close();
		}
		
		catch(IOException e) {
			e.printStackTrace();
		} 
	}
}