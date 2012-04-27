import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.*;
import ij.text.*;
import ij.plugin.frame.*;
import ij.ImagePlus;

//Java API classes
import java.io.*;
import java.util.*;
import java.lang.String.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings({ "unused", "serial" })
public class PickPlug extends JFrame implements ActionListener, ItemListener{

	ImageProcessor ip;
	static ImagePlus imgSource;
	ImagePlus imp;
	int type;
	JPanel panelPrincipal;
	JPanel paneltitle;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	PickPlug instance;
	int sigm1;
	int sigm2;

	public PickPlug(){
		super("Picking Plugin");

		IJ.run("Blobs (25K)"); // Ce sera bcp plus rapide ^^
		
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
		setLayout(gridbag);

		panelPrincipal = new JPanel();
		paneltitle = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		// Panel title

		///////////////////// Panel1 : Choice of algorithm 

		//panel1.setPreferredSize(new Dimension(450,70));
		//panel1.setLocation(10, 10);
		JLabel titlePanel1= new JLabel(" Choose a picking algorithm ", JLabel.CENTER);
		panel1.add(titlePanel1);

		// Create combo box
		String[] algos = { "DoG", "Dilatation Difference", "Image Correlation", "Algo 2", "About PickPlug" };

		//Create the combo box, default selection is the item at index 4.
		//Indices start at 0, so 4 specifies the About panel.
		JComboBox algoList = new JComboBox(algos);
		algoList.setSelectedIndex(1);
		algoList.addActionListener(this);

		//Set up the contain (contains all algorithms).
		JLabel choice = new JLabel();
		//choice.setFont(choice.getFont().deriveFont(Font.ITALIC));
		//choice.setHorizontalAlignment(JLabel.CENTER);
		//choice.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
		//choice.setPreferredSize(new Dimension(177, 122+10));
		panel1.add(algoList, BorderLayout.PAGE_START);
		panel1.add(choice, BorderLayout.PAGE_END);

		//////////////////////Panel2 : Show text info as default 
/*		
		panel2=About.create();
		JLabel info = new JLabel();
		info.setPreferredSize(new Dimension(500, 70));
		info.setText("Welcome to the Picking Plugin ");
		// Add text to panel 2
		panel2.add(info);
*/
		//////////////////////// Panel for Preview & Reset & Apply & Help

		//panel3.setPreferredSize(new Dimension(450,70));
		//panel3.setLocation(10, 170);
		JButton previewButton;
		previewButton = makeButton("Preview");
		JButton resetButton;
		resetButton = makeButton("Reset");
		JButton applyButton;
		//IJ.run("Undo");
		applyButton = makeButton("Apply");
		//applyButton.addActionListener(actionLi);
		JButton helpInfoButton;
		helpInfoButton = makeButton("Help & Info");
		helpInfoButton.addActionListener(new InfoHelpViewerPickPlug());
		/*JButton.setToolTipText("make a preview image"); a voir plus tard si on peut l'implementer, cause des erreurs d'attributs d'objets (voir cellcounter)*/
		
		//adding to Preview & Reset & Apply & Help box
		panel3.add(previewButton);
		panel3.add(resetButton);
		panel3.add(applyButton);
		panel3.add(helpInfoButton);

		///////////////// Principal panel
		// /*JPanel*/ panelPrincipal = new JPanel();
		//panelPrincipal.setPreferredSize(new Dimension(600,400));
		panelPrincipal.setLayout(new GridLayout(3,0)); //GridLayout(int rows, int cols)
		panelPrincipal.setVisible(true);
		
		//add(panelPrincipal); 
		panelPrincipal.add(panel1);
		panelPrincipal.add(panel2);
		panelPrincipal.add(panel3);
		add(panelPrincipal); 
		
		Runnable runner = new GUIShower(this);
		EventQueue.invokeLater(runner);
	}

	JButton makeButton(String name/*, String tooltip*/){
		JButton jButton = new JButton(name);
		//jButton.setToolTipText(tooltip);
		jButton.addActionListener(this);
		return jButton;
	}
	void validateLayout(){
		panel1.validate();
		panel2.validate();
		panel3.validate();
		panelPrincipal.validate();
		validate();
		pack();
	}
	
	/** Listens to the combo box. */
	public void actionPerformed(ActionEvent e) {
		//String command = e.getActionCommand();
		JComboBox cb = (JComboBox)e.getSource();
		String algo = (String)cb.getSelectedItem();
		//IJ.showMessage(algo);

		if ( algo.equals("About PickPlug" )){ 
			//IJ.showMessage(algo);
			panel2.removeAll();
			panelPrincipal.remove(panel2);
			panel2 = About.create();
			panelPrincipal.add(panel2);
			validateLayout();
		}
		else if ( algo.equals("DoG" )){
			panel2.removeAll(); 
			panelPrincipal.remove(panel2);
			sigm1 = Panel2.getSigma1();
			sigm2 = Panel2.getSigma2();
			/* l'idée est de dire de lancer l'algo que si l'utilisateur appuie sur apply
			 * mais c'est pas top, quand on selectionne l'algo, il faut appuyer sur apply et la on a les paramètres à rentrer
			 */
			/*if ( command.compareTo("Apply") == 0 ) {
				panel2 = PanelDoG.create();
				DoG.pick();
			}*/
			panelPrincipal.add(panel2);
			validateLayout();
		}
		else if ( algo.equals("Dilatation Difference" )){
			panel2.removeAll(); 
			panelPrincipal.remove(panel2);
			DilateDiff.pick();
			panelPrincipal.add(panel2);
			validateLayout();
		}
		else if ( algo.equals("Image Correlation" )){
			panel2.removeAll(); 
			panelPrincipal.remove(panel2);
			ImCorr.pick();
			panelPrincipal.add(panel2);
			validateLayout();
		}
		else if ( algo.equals("Algo 2" )){
			//IJ.showMessage(algo);
			panel2.removeAll(); 
			panelPrincipal.remove(panel2);
			//panel2 = Filter1()
			panel2 = Filter1.create();
			panelPrincipal.add(panel2);
			validateLayout();
		}

		/*
		else{//a mettre ds une classe (phase de test)
			panel2.setPreferredSize(new Dimension(500,70));
			// Create text info shown as default
			JLabel info = new JLabel();
			info.setPreferredSize(new Dimension(500, 70));
			info.setText("Welcome to the Picking Plugin ");
			// Add text to panel 2
			panel2.add(info);
		}*/
		
	}
	
	
	
	/*ActionListener actionLi = new ActionListener () {
		public void actionPerformed(ActionEvent actionE) {
			JButton button = (JButton) actionE.getSource();
			String command = actionE.getActionCommand();
			if ( command.compareTo("Apply") == 0 ) {
				panel2 = PanelDoG.create();
				DoG.pick();
			}
			else if ( command.compareTo("Median Filter") == 0 ) {
				IJ.showMessage("This is the median filter button");
			}
		}
	};*/
	

	// Réclamé par eclipse 
	@Override
	public void itemStateChanged(ItemEvent arg) {		
	}
}