import ij.IJ;

import java.util.Hashtable;

//add Licence GPL and description of the plugin and his authors
 
public final class Attributes {
	
	private static Attributes instance = null;
	private static Hashtable <String, String> attributes = new Hashtable<String, String>();
	
	private Attributes() {
		// La présence d'un constructeur privé supprime le constructeur public par défaut.
		// De plus, seul le singleton peut s'instancier lui même.
		super();
	}

	public final static Attributes getInstance() {
		if (Attributes.instance == null) {
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation multiple même par différents "threads".
			// Il est TRES important.
			synchronized(Attributes.class) {
				if (Attributes.instance == null) {
					Attributes.instance = new Attributes();
				}
			}
		}
		return Attributes.instance;
	}

	public static void setAttributes(String key, String value){
		attributes.put(key, value);
	}
	
	// D'autres méthodes classiques et non "static".
	public static Hashtable <String, String> getAttributes() {
		return attributes;
	}
}