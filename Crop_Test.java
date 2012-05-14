//add Licence GPL and description of the plugin and his authors

import ij.plugin.frame.PlugInFrame;

@SuppressWarnings("serial")
public class Crop_Test extends PlugInFrame{
	
	public Crop_Test() {
			super("Crop test");
			new Cropper();
	}
	
	public void run(String arg){
		
	}
}
