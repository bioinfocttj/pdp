//add Licence GPL and description of the plugin and his authors

import ij.IJ;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


<<<<<<< HEAD
@SuppressWarnings({ "serial" })
=======
@SuppressWarnings({ "serial"})
>>>>>>> 8b52d93fd741ae1d65673123c7cf2f9c5333c782

public class Panel2 extends JFrame /*implements ActionListener*/{
	static JPanel panel2 = new JPanel();
	
/*	static int sigma1 = 0;
	static int sigma2 = 0;
*/ 
	//static JPanel create();
	
	
	
	static JButton makeButton(String name){
		JButton jButton = new JButton(name);
		//ActionEvent action = jButton.
		//jButton.addActionListener(null);
		return jButton;
	}
	
	static JCheckBox makeJCheckBox(String name){
		JCheckBox checkBox = new JCheckBox( name); 
		return checkBox;
		
	}
	
	static JTextField makeJTextField(String text){
		JTextField textField = new JTextField(text);
		//textField.addActionListener((ActionListener) textField);
		//info.setPreferredSize(new Dimension(100, 200));
		//String temp=description;
		
		//JScrollPane scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//info.setLineWrap(true);
		return textField;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JComboBox cb = (JComboBox)e.getSource();
		String algo = (String)cb.getSelectedItem();

		
		if ( command.compareTo("Threshold") == 0){
			IJ.showMessage("threshold");
			IJ.run("Threshold...");
		}
		else if ( algo.equals( "Median Filter" )){
			IJ.showMessage(algo);
			IJ.run("Median...", "radius=2");
		}
		else { IJ.showMessage("bwaaaaah!!");}
	}
	

}