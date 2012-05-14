//add Licence GPL and description of the plugin and his authors

import ij.plugin.frame.PlugInFrame;
/**
 * 
 * @author CTTJ
 *
 */
@SuppressWarnings("serial")

public class Pick_EM extends PlugInFrame {
	
	public Pick_EM() {
		super("Pick Plug");
		new PickFrame();
	}
}