//add Licence GPL and description of the plugin and his authors

import ij.IJ;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.*;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings({ "serial", "unused" })

public class Panel2 extends JFrame /*implements ActionListener*/{
	static JPanel panel2 = new JPanel();
	
	static int sigma1 = 0;
	static int sigma2 = 0; 
	//static JPanel create();
	
	
	
	static JButton makeButton(String name){
		JButton jButton = new JButton(name);
		//ActionEvent action = jButton.
		//jButton.addActionListener(null);
		return jButton;
	}
	
	JScrollPane makeJTextField(String text){
		JTextField info = new JTextField(text);
		info.setPreferredSize(new Dimension(100, 200));
		//String temp=description;
		
		JScrollPane scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//info.setLineWrap(true);
		return scrollPane;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JComboBox cb = (JComboBox)e.getSource();
		String algo = (String)cb.getSelectedItem();
		//IJ.showMessage(algo);

		/*if ( algo.equals( "Threshold" )){ 
			IJ.showMessage(algo);
			AbstractButton thresholdButton = null;
			thresholdButton.setMnemonic(KeyEvent.VK_D);
		}*/
		
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
	
	public void setSigma(int sig1, int sig2) {
		sigma1 =sig1;
		sigma2 =sig2;
	}
	
	public static int getSigma1 () {
		return sigma1;
	}
	public static int getSigma2 () {
		return sigma2;
	}
	

}