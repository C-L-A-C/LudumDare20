package config;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import controles.Controle;
import utils.LogLevel;
import utils.Logger;


/**
 * Gestion de la configuration, exposant des methodes statique pour y acceder
 * @author adrien
 *
 */
public class Config{

	public static final String CONFIG_FILE = "config.ini";
	private static HashMap<ConfigKey, String> config = new HashMap<>();
	private static HashMap<ConfigKey, Predicate<String>> checks = new HashMap<>();

	// Permet de lire la valeur de configuration correspondant a key sous forme booleenne
	public static boolean readBoolean(ConfigKey key) {
		String s = read(key);
		return Boolean.parseBoolean(s);
	}

	// Permet de lire la valeur de configuration correspondant a key sous forme de texte
	public static String readString(ConfigKey key) {
		return read(key);
	}


	// Permet de lire la valeur de configuration correspondant a key sous forme d'un entier
	public static int readInt(ConfigKey key) {
		try{
			return Integer.parseInt(read(key));
		}catch (NumberFormatException e)
		{
			Logger.printlnErr("Impossible de lire la valeur de configuration de "+key+" : valeur numérique invalide");
		}
		return 0;
	}
	
	// Permet de lire la valeur de configuration correspondant a key sous forme d'un identifiant de touche (clavier)
	public static int readKey(ConfigKey key) {
		String s = read(key);
		return stringToKey(s);
	}
	
	// Ne devrait ptete pas etre la : transforme un identifiant de touche en texte
	public static String keyToString(int value) {
		for (Field f : KeyEvent.class.getFields())
		{
			String name = f.getName();
			
			try {
				if (name.startsWith("VK_") && f.getInt(null) == value)
				{
					return name.substring(3);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {}
		}
		return null;
	}
	// Ne devrait ptete pas etre la : retourne l'identifiant de touche depuis sa representation textuelle
	public static int stringToKey(String string) {
		try {
			return KeyEvent.class.getField("VK_" + string.toUpperCase()).getInt(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			return -1;
		}
	}
	
	// Retourne l'ensemble des clefs de configuration enregistrees
	public static Set<ConfigKey> getCles()
	{
		if (config.isEmpty())
			reloadConfig();
		return new TreeSet<ConfigKey>(config.keySet());
	}

	// Defini une valeur de configuration
	public static boolean set(ConfigKey clef, String valeur) {
		if (check(clef, valeur))
		{
			config.put(clef, valeur.trim());
			saveConfig(CONFIG_FILE);
			return true;
		}
		return false;
	}

	// Verifie la validite de type d'une valeur de configuration
	private static boolean check(ConfigKey clef, String valeur) {
		if (clef != null && valeur != null)
		{
			return checks.get(clef).test(valeur);
		}
		return false;
	}

	private static String read(ConfigKey key)
	{
		if (config.isEmpty())
			reloadConfig();
		
		return config.get(key);
	}

	private static void reloadConfig() {
		//On charge les parametre par defaut et on ecrase avec les valeurs trouvees dans le fichier de configuration
		loadDefaults();
		loadFile(CONFIG_FILE);
		
	}

	// Charge la config depuis configFile, en ecrasant les valeurs preexistantes si elles sont defini dans le fichier
	public static void loadFile(String configFile){
		try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			String ligne;
			while((ligne = reader.readLine()) != null)
			{
				//Skip comments
				if (ligne.startsWith("#")|| ligne.trim().isEmpty())
					continue;
				
				String[] pair = ligne.split("=");
				//Skip ill-formed lines
				if (pair.length != 2)
				{
					Logger.println("Ligne incorrecte dans le fichier configuration " + configFile + " : " + ligne, LogLevel.WARNING);
					continue;
				}
				//On recupere la valeur d'enum a partir de la chaine
				String str = pair[0].trim();
				ConfigKey key = ConfigKey.fromString(str);

				//Store parameters
				if (key == null)
					Logger.println("Clé incorrecte dans le fichier configuration " + configFile + " : " + str, LogLevel.WARNING);
				else
					config.put(key, pair[1].trim());
			}
		} catch (IOException e) {
			Logger.printlnErr("Atention : impossible de lire la configuration depuis " + configFile);
		}
	}

	// Sauvegarde la config dans un fichier
	public static void saveConfig(String configFile) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile)))
		{
			Set<ConfigKey> set = new TreeSet<>(config.keySet());
			for (ConfigKey key : set)
			{
				writer.write(key.getValue() + " = " + readString(key));
				writer.newLine();
			}
		} catch (IOException e) {
			Logger.printlnErr("Atention : impossible de sauvegarder la configuration vers " + configFile);
		}
		
	}

	// Charge les valeurs par defaut et les checks de type de la configuration
	private static void loadDefaults() {
		Predicate<String> checkString = str -> str != null && !str.isEmpty();
		Predicate<String> checkDir = str -> str != null && Files.isDirectory(Path.of(str));
		Predicate<String> checkInt = str -> {
			try {
				Integer.parseInt(str);
				return true;
			}catch(NumberFormatException e)
			{
				return false;
			}
		};
		//Predicate<String> checkBool = str -> str.toLowerCase().equals("true") || str.toLowerCase().equals("false");
		Predicate<String> checkKey = str -> stringToKey(str) != -1;
		
		//Default configuration and type checks
		config.put(ConfigKey.MAP_DIRECTORY, "assets/maps/");
		checks.put(ConfigKey.MAP_DIRECTORY, checkDir);
		config.put(ConfigKey.LOG_LEVEL, "2");
		checks.put(ConfigKey.LOG_LEVEL, checkInt);	
		
		
		// Default configuration for keys
		// Regarder la class Controle pour savoir l'ordre
		String[] keyDefaults = {"UP", "DOWN", "RIGHT", "LEFT", "A", "Q"};
		Controle[] controles = Controle.values();
		
		// On prend que les version "en mode appuye" de base des controles 
		for (int i = 0; i < controles.length / 2; i++)
		{
			ConfigKey key = ConfigKey.getConfigKeyFromControle(controles[i]);
			config.put(key, keyDefaults[i]);
			checks.put(key, checkKey);
		}
		
	}

}
