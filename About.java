//add Licence GPL and description of the plugin and his authors

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings({ "serial" })
class About extends PickPanel {
	
	private static String description="ABOUT THE AUTORS of \"Pick EM plugin\"\n"+
		"This plugin was implemented by : \n"+
		"	Thomas Faux, Charlotte Héricé, Typhaine Paysan-Lafosse and Joris Sansen \n"+ 
		"	under Professor Jean-Christophe Taveau's supervision \n" +
		"	at CBMN (Chimie & Biologie des Membranes et des Nanoobjets) \n" +
		"	at University Bordeaux 1 (France).\n"+
		"CONTACT INFO\n"+
		"	For further information contact us:\n"+
		"	Master BioInoformatique, Université Bordeaux 1 Talence, \n" +
		"	mail : prenom.nom@etu.u-bordeaux1.fr \n";

	static JTextArea info = new JTextArea(description);
	
	About() {
		super();
	}
	
	static JPanel create(){
		JScrollPane scrollPane = new JScrollPane(info);
		panel2.add(scrollPane);
		return panel2;
	}

}

