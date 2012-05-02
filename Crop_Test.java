import ij.plugin.frame.PlugInFrame;

public class Crop_Test extends PlugInFrame{

	// Retire une erreur
	private static final long serialVersionUID = 1L;
	
	public Crop_Test() {
			super("Crop test");
			new Cropper();
	}
	
	public void run(String arg){
		
	}
}
