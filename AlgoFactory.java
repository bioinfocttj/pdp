import ij.IJ;

import javax.swing.JPanel;

//add Licence GPL and description of the plugin and his authors
 
public final class AlgoFactory {
	
	private static AlgoFactory instance = null;
/*
	private static final String DOG = "DoG";
	private static final String DILATE = "Dilate Difference";
	private static final String IMCORR = "Image Correlation";
	private static final String ABOUT = "About PickPlug";
	*/
	
	private AlgoFactory() {
		// La présence d'un constructeur privé supprime le constructeur public par défaut.
		// De plus, seul le singleton peut s'instancier lui même.
		super();
	}

	public final static AlgoFactory getInstance() {
		if (AlgoFactory.instance == null) {
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation multiple même par différents "threads".
			// Il est TRES important.
			synchronized(AlgoFactory.class) {
				if (AlgoFactory.instance == null) {
					AlgoFactory.instance = new AlgoFactory();
				}
			}
		}
		return AlgoFactory.instance;
	}

	public enum algorithm { Difference_of_Gaussian, Dilate_Difference, Image_Correlation, About_Pick_EM ;
		static void getPicker(String picker){
			algorithm pick = algorithm .valueOf(picker);
			switch (pick) {
				case Difference_of_Gaussian : break;
				case Dilate_Difference :break;
				case Image_Correlation : break;
				case About_Pick_EM : IJ.showMessage("about");break;
				default: IJ.showMessage("default");break;
			}
		}

		static JPanel getPickPanel(String panel){
			algorithm pan = algorithm .valueOf(panel);
			switch (pan) {
				case Difference_of_Gaussian : return PanelDoG.create(); 
				case Dilate_Difference : return PanelDilateDiff.create();
				case Image_Correlation : return PanelImCorr.create();
				case About_Pick_EM : return About.create();
				default: return About.create();
			}
		}
	}
}
