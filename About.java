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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings({ "serial" })
public class About extends PickPanel {
	// This class displays informations about the authors
	
	private static String description="ABOUT THE AUTORS of \"Pick EM plugin\"\n"+
		"This plugin was implemented by : \n"+
		"  T. Faux, C. Héricé, T. Paysan-Lafosse and J. Sansen \n"+
		"  with Pr. Jean-Christophe Taveau \n" +
		"  at CBMN (Chimie & Biologie des Membranes et des Nanoobjets) \n" +
		"  at University Bordeaux 1 (France).\n"+

		"  For further information contact us:\n"+
		"  Master BioInoformatique, Université Bordeaux 1, \n" +
		"  mails : thomas.faux@etu.u-bordeaux1.fr, \n" + "             charlotte.herice@etu.u-bordeaux1.fr, \n" + 
		"             typhaine.paysan-lafosse@etu.u-bordeaux1.fr, \n"+"             joris.sansen@etu.u-bordeaux1.fr";

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

