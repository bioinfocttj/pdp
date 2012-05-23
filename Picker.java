import ij.ImagePlus;

import java.util.Vector;

public abstract class Picker {
// This class allows to call the picking algorithms

	protected static Vector<Double> xtab = new Vector<Double>();
	protected static Vector<Double> ytab = new Vector<Double>();
	protected static Vector<Double> slice = new Vector<Double>();
	protected static ImagePlus im;
	protected static double[][]array;
	protected static double tolerance;
	protected static String cropMode;
	protected static String noiseT;
	protected static boolean cropperMode;
}