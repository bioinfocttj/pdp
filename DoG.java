
//add Licence GPL and description of the plugin and his authors

import ij.IJ;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ij.*;
import ij.measure.*;
import ij.process.*;
import ij.gui.*;
import ij.gui.Roi.*;
import ij.gui.PolygonRoi.*;
import ij.gui.PointRoi.*;
import ij.plugin.*;
import ij.plugin.filter.*;
import ij.plugin.frame.*;
import java.lang.*;
import java.awt.*;
import java.io.PrintStream;

@SuppressWarnings({ "serial", "unused" })
class DoG extends PickPlug_ /*implements ActionListener*/{

	/*Penser a mettre les infos et description d'aide!!!*/
	//static JPanel panel2Algo1 = new JPanel();
	
	DoG(){
		super();
	}
	
	static void pick(){
		

		ImageCalculator ic;
		ResultsTable table;
		int counter=0;
		int[] xpoints;
		int[] ypoints;

		ImagePlus imp = WindowManager.getCurrentImage();
		
		ImagePlus imp1 = new Duplicator().run(imp);
		ImagePlus imp2= new Duplicator().run(imp1);
		int s1 = Panel2.getSigma1();
		int s2 = Panel2.getSigma2();
		String si1 = "sigma=" + s1;
		String si2 = "sigma=" + s2;
		//IJ.run(imp2, "Gaussian Blur...", "sigma=10"); commande originale
		/* comme les IJ.run n'accepte que des strings et que nous voulons mettre des variables dedans, on recaste 
		 * pour le moment on n'a pas de tableau car trop compliqué (on a essayé et pas concluant ^^)*/
		IJ.run(imp1, "Gaussian Blur...", si1); 
		IJ.run(imp2, "Gaussian Blur...", si2);
		ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp2, imp1);
		IJ.run(imp3, "Find Maxima...", "noise=10 output=List");
		table = Analyzer.getResultsTable();
		counter = table.getCounter();
		int iterator = counter-1;
		xpoints = new int[counter];
		ypoints = new int [counter];
		for (int i=0; i<counter; i++){
			double x = table.getValue("X",i);
			double y = table.getValue("Y",i);
			int xx = (int) x;
			int yy = (int) y;
			xpoints[i] = xx;
			ypoints[i] = yy;
			imp.setRoi(new PointRoi(xpoints, ypoints, counter));
		}
		imp.show();
		IJ.selectWindow("Results");
		IJ.run("Clear Results");
		IJ.run("Measure");
		

		// Tests
		
		/*//panel2Algo1.setPreferredSize(new Dimension(200,250));
		//panel2Algo1.setLocation(10, 90);
		// Create text info shown as default
		JTextArea info = new JTextArea(description);
		//info.setPreferredSize(new Dimension(100, 200));
		//String temp=description;
		////JScrollPane scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//info.setLineWrap(true);
		//info.setText(description);
		// Add text to panel 2
		panel2.add(info);*/
		
		//panel2.setPreferredSize(new Dimension(400,200));
		/*coffeaButton = makeButton("Make coffea");
		//coffeaButton.setMnemonic(KeyEvent.VK_A);
		coffeaButton.addActionListener(actionL);
		panel2.add(coffeaButton);*/
		//return panel2;
	}


	static ActionListener actionL = new ActionListener () {
		public void actionPerformed(ActionEvent actionE) {
			JButton button = (JButton) actionE.getSource();
			IJ.showMessage("Do it yourself !");
		}
	};

}
