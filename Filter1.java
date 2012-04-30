
//add Licence GPL and description of the plugin and his authors

import ij.IJ;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings({ "serial", "unused" })
class Filter1 extends Panel2 /*implements ActionListener*/{
	
	static JButton thresholdButton;
	static JButton mfilterButton;
	
	/////// From button "ALGO 2"
	
	Filter1(){ 
		super();
	}
	
	/*Penser a mettre les infos et description d'aide!!!*/
	//static JPanel panel2Filter1 = new JPanel();

	static JPanel create(){
		//panel2.setPreferredSize(new Dimension(400,200));
		//IJ.showMessage("Hello world");
		thresholdButton = makeButton("Threshold");
		mfilterButton = makeButton("Median Filter");
		thresholdButton.addActionListener(actionL);
		mfilterButton.addActionListener(actionL);
		panel2.add(thresholdButton);
		panel2.add(mfilterButton);
		//thresholdButton.addActionListener(IJ.run("Threshold..."));
		return panel2;
	}
	
	static ActionListener actionL = new ActionListener () {
		public void actionPerformed(ActionEvent actionE) {
			JButton button = (JButton) actionE.getSource();
			String command = actionE.getActionCommand();
			//Object source = e.getSource();
			if ( command.compareTo("Threshold") == 0 ) {
				IJ.run("Threshold...");
				//IJ.showMessage("This is the threshold button");
			}
			else if ( command.compareTo("Median Filter") == 0 ) {
				IJ.run("Median..."/*, "radius=2"*/);
				//IJ.showMessage("This is the median filter button");
			}
		}
	};

	/*public void actionPerformed(ActionEvent e) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Picking Plugin\"", sb.toString(), 700, 500);
		tw.setVisible(true);
	}*/

}
