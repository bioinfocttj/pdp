/*
*Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
*This file is part of Pick_EM program
*Pick_EM is free software; you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation; either version 2 of the License, or
*(at your option) any later version.
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*You should have received a copy of the GNU General Public License along
*with this program; if not, write to the Free Software Foundation, Inc.,
*51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

import ij.IJ;
import ij.process.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings({ "serial"})

public class PickFrame extends JFrame implements ActionListener {
	// This class is for the plugin interface 
	
	ImageProcessor ip;
	
	int type;
	
	private JPanel mainPanel;
	private JPanel panel1;
	private JPanel paneltitle;
	private JPanel panel2;
	private JPanel panel3;
	
	PickFrame instance;
	
	private JButton helpInfoButton;
	private JButton previewButton;
	private JButton applyButton;
	private JButton saveButton;
	JComboBox algoList;
	
	double[][] coordXYZ;
	
	public PickFrame(){
		super("Picking Plugin");
		initGUI();
		instance = this;
		add(paneltitle);
	}

	// Show the GUI threadsafe
	private class GUIShower implements Runnable {
		final JFrame jFrame;
		public GUIShower(JFrame jFrame) {
			this.jFrame = jFrame;
		}
		
		public void run() {
			jFrame.pack();
			jFrame.setLocation(1000, 200);
			jFrame.setVisible(true);
			jFrame.setResizable(false);
		}
	}

	private void initGUI(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridbag = new GridBagLayout();
		getContentPane().setLayout(gridbag);
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(450, 600)); // Horizontal, vertical 
		paneltitle = new JPanel();
		panel1 = new JPanel();
		panel1.setLayout(gridbag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel3.setLayout(gridbag);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.SOUTH;

		///////////////////// Panel1 : Choice of algorithm 

		JLabel titlePanel1 = new JLabel(" Choose a picking algorithm ", JLabel.CENTER);
		panel1.add(titlePanel1);
		// Create combo box
		String[] algos = { "Difference_of_Gaussian", "Dilate_Difference", "Image_Correlation", "About_Pick_EM"};

		//Create the combo box, default selection is the item at index 4.
		//Indices start at 0, so 4 specifies the About panel.
		algoList = new JComboBox(algos);
		algoList.setSelectedIndex(3);
		algoList.addActionListener(this);

		//Set up the contain (contains all algorithms).
		JLabel choice = new JLabel();
		gridbag.setConstraints(algoList,gbc);
		panel1.add(algoList);
		gridbag.setConstraints(choice,gbc);
		panel1.add(choice);
		
		//////////////////////// Panel3 : Preview & Apply & Save & Help

		previewButton = makeButton("Preview");
		applyButton = makeButton("Apply");
		saveButton = makeButton("Show Results");
		helpInfoButton = makeButton("Help & Info");
		helpInfoButton.addActionListener(new InfoHelp());
		
		//adding to Preview & Reset & Apply & Help box
		gridbag.setConstraints(previewButton,gbc);
		panel3.add(previewButton);
		gridbag.setConstraints(applyButton,gbc);
		panel3.add(applyButton);
		gridbag.setConstraints(saveButton,gbc);
		panel3.add(saveButton);
		gridbag.setConstraints(helpInfoButton,gbc);
		panel3.add(helpInfoButton);

		///////////////// Main panel
		
		mainPanel.setLayout(new GridLayout(3,0)); 
		mainPanel.setVisible(true);
		
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		add(mainPanel); 
		Runnable runner = new GUIShower(this);
		EventQueue.invokeLater(runner);
	}

	JButton makeButton(String name){
		JButton jButton = new JButton(name);
		jButton.addActionListener(this);
		return jButton;
	}
		
	// Listens to the combo box
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		String comboSelection = null;
		if (command.equals("comboBoxChanged")){
			JComboBox cb = (JComboBox)e.getSource();
			comboSelection = (String) cb.getSelectedItem();
			panel2.removeAll();
			// Allows the panel2's update
			mainPanel.remove(panel1);
			mainPanel.remove(panel2);
			mainPanel.remove(panel3);
			panel2 = AlgoFactory.algorithm.getPickPanel(comboSelection);
			mainPanel.add(panel1);
			mainPanel.add(panel2);
			mainPanel.add(panel3);
			mainPanel.repaint();
			pack();
		}
		
		if (command.equals("Apply")){
			comboSelection = (String)algoList.getSelectedItem();
				Attributes.getInstance();
				coordXYZ = AlgoFactory.algorithm.getPicker(comboSelection);
				IJ.showStatus("End of picking");
			}
		
		else if (command.equals("Preview")){
			comboSelection = (String)algoList.getSelectedItem();
				Attributes.getInstance();
				AlgoFactory.algorithm.getPickerPreview(comboSelection);
				IJ.showStatus("End of Preview");
			}
		else if (command.equals("Show Results")){
				ToCSV.generateCsvFile(coordXYZ);
		}
	}
}