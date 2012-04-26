
//add Licence GPL and description of the plugin and his authors

import ij.IJ;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings({ "serial", "unused" })
class About extends Panel2 /*implements ActionListener*/{
	
	/*Penser a mettre les infos et description d'aide!!!*/
	//static JPanel panel2About = new JPanel();
	
	private static String description="ABOUT THE AUTORS of \"Picking Plugin\"\n"+
		"This plugin was implemented by Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		"under Professor Jean-Christophe Taveau's supervision at CBMN (Chimie & Biologie des Membranes et des Nanoobjets) at University Bordeaux 1 (France).\n"+
		"CONTACT INFO\n"+
		"For further information contact us:\n"+
		"Master BioInoformatique, Université Bordeaux 1 Talence, mail : prenom.nom@etu.u-bordeaux1.fr \n";

	static JTextArea info = new JTextArea(description/*,50,50*/);
	
	About() {
		super();
	}
	
	static JPanel create(){
		//panel2About.setPreferredSize(new Dimension(450,250));
		//panel2About.setLocation(10, 90);
		// Create text info shown as default
		//String temp=description;
		//info.setPreferredSize(new Dimension(400, 200));
		JScrollPane scrollPane = new JScrollPane(info/*, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS*/);
		//info.setLineWrap(true);
		//info.setText(description);
		//IJ.showMessage("hello world");
		// Add text to panel 2
		panel2.add(scrollPane);
		
		return panel2;
	}

	/*public void actionPerformed(ActionEvent e) 
	{
		StringBuffer sb = new StringBuffer();
		sb.append(description);
		TextWindow tw = new TextWindow("About \"Picking Plugin\"", sb.toString(), 700, 500);
		tw.setVisible(true);
	}*/

}

