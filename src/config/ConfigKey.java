package config;

import java.util.HashMap;
import java.util.Map;

import controles.Controle;
import utils.Utils;

/**
 * Enumeration representant les clefs de configuration
 * @author adrien
 *
 */
public enum ConfigKey {
	
	//Clef de configuration et leur nom pour le fichier de config
	OPTION_1("Option1"),
	LOG_LEVEL("LogLevel"),
	// Le format de nom des touches est : ToucheNomTouche (Touche + NomTouche en PascalCase)
	TOUCHE_HAUT("ToucheHaut"),
	TOUCHE_BAS("ToucheBas"),
	TOUCHE_DROITE("ToucheDroite"),
	TOUCHE_GAUCHE("ToucheGauche"),
	TOUCHE_POSER_MUR("TouchePoserMur"), 
	MAP_DIRECTORY("MapsDirectory"),
	TOUCHE_MINER("ToucheMiner");
	
	
	private final static Map<String, String> noms = new HashMap<>();
	private String key;
	
	private ConfigKey(String k)
	{
		key = k;
	}
	
	//Retourne le nom brut de la clef de config
	public String getValue()
	{
		return key;
	}
	
	//Retourne la clef de configuration de la valeur de la touche voulue
	public static ConfigKey getConfigKeyFromControle(Controle c)
	{
		return fromString("Touche" + Utils.PascalCase(c.toString()));
	}
	
	//Si on veut afficher la clef de config, on affiche sa "traduction"
	public String toString()
	{
		return traduire(key);
	}
	
	//Retourne la clef de configuration depuis sa representation textuelle
	public static ConfigKey fromString(String s)
	{
		for (ConfigKey c : values())
		{
			if (c.getValue().equals(s))
				return c;
		}
		return null;
	}

	//Si on veut afficher le nom de l'option (dans un menu de selection d'option par exemple)
	private static String traduire(String cle) {
		if (noms.isEmpty())
			initNameMap();			

		String s = noms.get(cle);
		return s == null ? cle : s;
	}
	
	// Utilise par traduire()
	private static void initNameMap() {
		noms.put(OPTION_1.key, "Nom de l'option 1");
	}
}
