
//add Licence GPL and description of the plugin and his authors

//ImageJ classes
import ij.gui.*;
import ij.ImagePlus;

import java.awt.*;
import java.awt.event.*;


public class PreviewImage implements ActionListener {
	private ImagePlus imp;
    private ImageCanvas canvas;
    
	public PreviewImage(ImagePlus img) {
		imp=img;
		canvas=new ImageCanvas(imp);
        ImageWindow imgWin=new ImageWindow(imp);
        imgWin.setResizable(false);
        imgWin.setSize(canvas.getSize().width+75,canvas.getSize().height+75);
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        imgWin.setLayout(gridbag);
        
        //Panel Canvas
        c.gridx = 0;
        int y=0;
        c.gridy = y++;
        c.insets = new Insets(10, 10, 10, 10);
        gridbag.setConstraints(canvas, c);
        imgWin.add(canvas);
        imgWin.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}
}
