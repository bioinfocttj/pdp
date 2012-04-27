
import ij.*;
import ij.plugin.PlugIn;
import ij.process.*;

/** The class implements the Process/FFT/Math command. */
public class FFTMath implements PlugIn {

    private static String title = "Result";
            
    public void run(String arg) {
/*
        int[] wList = WindowManager.getIDList();
	String[] titles = new String[wList.length];
        for (int i=0; i<wList.length; i++) {
            ImagePlus imp = WindowManager.getImage(wList[i]);
            if (imp!=null)
                titles[i] = imp.getTitle();
            else
                titles[i] = "";
        }

            doMath(imp1, imp2);
*/
    }
/*
public boolean showDialog() {
        int[] wList = WindowManager.getIDList();
	String[] titles = new String[wList.length];
        for (int i=0; i<wList.length; i++) {
            ImagePlus imp = WindowManager.getImage(wList[i]);
            if (imp!=null)
                titles[i] = imp.getTitle();
            else
                titles[i] = "";
        }
*/
 public static ImagePlus doMath(ImagePlus imp1, ImagePlus imp2) {
        //FHT result=null;
        FHT result, h1, h2=null;
        ImageProcessor fht1, fht2;
        fht1  = (ImageProcessor)imp1.getProperty("FHT");
        if (fht1!=null)
            h1 = new FHT(fht1);
        else {
            IJ.showStatus("Converting to float");
            ImageProcessor ip1 = imp1.getProcessor();
            h1 = new FHT(ip1);
        }
        fht2  = (ImageProcessor)imp2.getProperty("FHT");
        if (fht2!=null)
            h2 = new FHT(fht2);
        else {
            ImageProcessor ip2 = imp2.getProcessor();
            if (imp2!=imp1)
                h2 = new FHT(ip2);
        }
        if (fht1==null) {
            IJ.showStatus("Transform image1");
            h1.transform();
        }
        if (fht2==null) {
            if (h2==null)
                h2 = new FHT(h1.duplicate());
                else {
                    IJ.showStatus("Transform image2");
                    h2.transform();
                }
        }
	IJ.showStatus("Complex conjugate multiply");
       	result = h1.conjugateMultiply(h2); 
	IJ.showStatus("Inverse transform");
            result.inverseTransform();
            IJ.showStatus("Swap quadrants");
            result.swapQuadrants();
            IJ.showStatus("Display image");
            result.resetMinAndMax();
            return (new ImagePlus(title, result));
           // return result;
}
}