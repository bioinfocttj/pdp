//add Licence GPL and description of the plugin and his authors
 
import ij.process.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings({ "serial"})

public class PickFrame extends JFrame implements ActionListener {

	ImageProcessor ip;
	
	//static ImagePlus imgSource;
	//ImagePlus imp;
	
	int type;
	
	JPanel mainPanel;
	JPanel panel1;
	private JPanel paneltitle;
	private JPanel panel2;
	private JPanel panel3;
	
	PickFrame instance;
	
	JButton helpInfoButton;
	private JButton previewButton;
	private JButton applyButton;
	JComboBox algoList;
	
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
		//panel1.setPreferredSize(new Dimension(450, 150));
		panel2 = new JPanel();
		//panel2.setPreferredSize(new Dimension(450, 300));
		panel3 = new JPanel();
		panel3.setLayout(gridbag);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.SOUTH;
		//panel3.setPreferredSize(new Dimension(450, 150));

		///////////////////// Panel1 : Choice of algorithm 

		JLabel titlePanel1= new JLabel(" Choose a picking algorithm ", JLabel.CENTER);
		panel1.add(titlePanel1);
		//String DOG = "DoG";
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
		panel1.add(algoList/*, BorderLayout.PAGE_START*/);
		gridbag.setConstraints(choice,gbc);
		panel1.add(choice/*, BorderLayout.PAGE_END*/);
		
		//////////////////////// Panel3 : Preview & Apply & Help

		previewButton = makeButton("Preview");
		applyButton = makeButton("Apply");
		helpInfoButton = makeButton("Help & Info");
		helpInfoButton.addActionListener(new InfoHelp());
		
		//adding to Preview & Reset & Apply & Help box
		gridbag.setConstraints(previewButton,gbc);
		panel3.add(previewButton);
		gridbag.setConstraints(applyButton,gbc);
		panel3.add(applyButton);
		gridbag.setConstraints(helpInfoButton,gbc);
		panel3.add(helpInfoButton);

		///////////////// Principal panel
		mainPanel.setLayout(new GridLayout(3,0)); //GridLayout(int rows, int cols)
		mainPanel.setVisible(true);
		
		//add(panelPrincipal); 
		mainPanel.add(panel1/*, BorderLayout.NORTH*/);
		mainPanel.add(panel2);
		mainPanel.add(panel3/*, BorderLayout.SOUTH*/);
		add(mainPanel); 
		Runnable runner = new GUIShower(this);
		EventQueue.invokeLater(runner);
	}

	JButton makeButton(String name){
		JButton jButton = new JButton(name);
		jButton.addActionListener(this);
		return jButton;
	}
		
	/*Listens to the combo box. */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("comboBoxChanged")){
			JComboBox cb = (JComboBox)e.getSource();
			String comboSelection = (String) cb.getSelectedItem();
			panel2.removeAll();
			//on retire tout pour les rajouter dans le bon ordre, seule solution trouvee pr le mmt pr maintenir le panel 2 au milieu
			mainPanel.remove(panel1);
			mainPanel.remove(panel2);
			mainPanel.remove(panel3);
			panel2 = AlgoFactory.algorithm.getPickPanel(comboSelection);
			mainPanel.add(panel1);
			mainPanel.add(panel2);
			mainPanel.add(panel3);
			mainPanel.repaint();
			
			pack();
			AlgoFactory.algorithm.getPicker(comboSelection);
		}
		else if (command.equals("Preview")){
			String algo = (String)algoList.getSelectedItem();
			if (algo.equals("Difference_of_Gaussian")){
				//DoG dogPicker = new DoG();
				Attributes.getInstance();
				PanelDoG.setAttributes();
				double[][] coordXYZ = DoG.sliceSelection();
				ToCSV.generateCsvFile("dog.csv", coordXYZ);
			}

			else if (algo.equals("Image_Correlation")){
				/*double [][] resultArray = */
				//ImCorr imCorrPicker = new ImCorr();
				Attributes.getInstance();
				PanelImCorr.setAttribute();
				double[][] coordXYZ = ImCorr.sliceSelection();
				ToCSV.generateCsvFile("imcorr.csv", coordXYZ);
			}

			else if (algo.equals("Dilate_Difference")){
				/*double [][] resultArray = */
				//DilateDiff dilateDiffPicker= new DilateDiff();
				Attributes.getInstance();
				PanelDilateDiff.setAttributes();
				double[][] coordXYZ = DilateDiff.sliceSelection();
				ToCSV.generateCsvFile("dil.csv", coordXYZ);
			}
		}
		
	}
}