//add Licence GPL and description of the plugin and his authors
 
import ij.process.*;
import ij.ImagePlus;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.*;

@SuppressWarnings({ "serial"})

public class PickFrame extends JFrame implements ActionListener {

	ImageProcessor ip;
	//static ImagePlus imgSource;
	//ImagePlus imp;
	int type;
	JPanel mainPanel;
	private JPanel paneltitle;
	JPanel panel1;
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
		}
	}

	private void initGUI(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridbag = new GridBagLayout();
		//GridBagConstraints gbc = new GridBagConstraints();
		//gbc.gridy = 1;
		setLayout(gridbag);
		mainPanel = new JPanel();	
		paneltitle = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

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
		panel1.add(algoList, BorderLayout.PAGE_START);
		panel1.add(choice, BorderLayout.PAGE_END);
		
		//////////////////////// Panel for Preview & Reset & Apply & Help

		previewButton = makeButton("Preview");
		applyButton = makeButton("Apply");
		helpInfoButton = makeButton("Help & Info");
		helpInfoButton.addActionListener(new InfoHelpViewerPickPlug());
		
		//adding to Preview & Reset & Apply & Help box
		panel3.add(previewButton);
		panel3.add(applyButton);
		panel3.add(helpInfoButton);

		///////////////// Principal panel
		mainPanel.setLayout(new GridLayout(3,0)); //GridLayout(int rows, int cols)
		mainPanel.setVisible(true);
		
		//add(panelPrincipal); 
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
			
			pack();
			AlgoFactory.algorithm.getPicker(comboSelection);
		}
		else if (command.equals("Preview")){
			String algo = (String)algoList.getSelectedItem();
			if (algo.equals("Difference_of_Gaussian")){
				//DoG dogPicker = new DoG();
				Attributes.getInstance();
				PanelDoG.setAttribute();
				DoG.sliceSelection();
			}

			else if (algo.equals("Image_Correlation")){
				/*double [][] resultArray = */
				//ImCorr imCorrPicker = new ImCorr();
				Attributes.getInstance();
				PanelImCorr.setAttribute();
				ImCorr.sliceSelection();
			}

			else if (algo.equals("Dilate_Difference")){
				/*double [][] resultArray = */
				//DilateDiff dilateDiffPicker= new DilateDiff();
				Attributes.getInstance();
				PanelDilateDiff.setAttributes();
				DilateDiff.sliceSelection();
			}
		}
		
	}
}