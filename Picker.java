import ij.ImagePlus;

import java.util.Vector;

abstract public class Picker {
// This class allows to call the picking algorithms

	protected static Vector<Double> xtab=new Vector<Double>();
	protected static Vector<Double> ytab=new Vector<Double>();
	protected static Vector<Double> slice=new Vector<Double>();
	protected static ImagePlus im;
	protected static double[][]array;
	protected static double tolerance;
	protected static String cropMode;
	protected static String noiseT;
	protected static boolean cropperMode;
	
	static double[][] resultConverter(){
		int arrayLength = xtab.size();
		Object[] tempX = new String[arrayLength];
		Object[] tempY = new String[arrayLength];
		Object[] tempZ = new String[arrayLength];
		tempX = xtab.toArray();
		tempY = ytab.toArray();
		tempZ = slice.toArray();
		double[] xArray = new double[arrayLength];
		double[] yArray = new double[arrayLength];
		double[] zArray = new double[arrayLength];
		for (int i=0; i < arrayLength; i++){
			String temp = String.valueOf(tempX[i]);
			xArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempY[i]);
			yArray[i] = Double.parseDouble(temp);
			temp = String.valueOf(tempZ[i]);
			zArray[i] = Double.parseDouble(temp);
		}
		double[][] coordinates = new double[3][arrayLength];
		coordinates[0]=xArray;
		coordinates[1]=yArray;
		coordinates[2]=zArray;
		return coordinates;
	}
		

}