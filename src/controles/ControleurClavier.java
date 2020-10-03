package controles;

import config.Config;
import config.ConfigKey;
import jeu.DonneesJeu;
import utils.Utils;

public class ControleurClavier extends Controleur{
	
	/**
	 * Enum des etats des touches
	 */
	protected static final int ETAT_VIDE = 0, ETAT_APPUYE = 1, ETAT_RELACHE = 2;
	
	/**
	 * Etats des differentes touches
	 */
	protected int[] touches;
	
	/**
	 * Config predefinie
	 */
	private static int[] config = null;
	
	
	public ControleurClavier(Controlable c)
	{
		super(c);
		init();
		touches = new int[Controle.values().length / 2];
	}
	
	protected void init()
	{
		if (config == null)
			readDefaultConfig();
	}
	
	public static void readDefaultConfig() {
		Controle[] controles = Controle.values();
		config = new int[controles.length / 2];
		for (int i = 0; i < config.length; i++)
			config[i] = Config.readKey(ConfigKey.getConfigKeyFromControle(controles[i]));
	}

	public void keyPressed(int keycode)
	{
		//Logger.println("Touche appuyée (" + KeyEvent.getKeyText(keycode) + "), t = " + (System.nanoTime() / 1E6) % 4000, LogLevel.DEBUG);
		setValue(keycode, true);

	}
	
	public void keyReleased(int keycode)
	{
		//Logger.println("Touche relachée (" + KeyEvent.getKeyText(keycode) + "), t = " + (System.nanoTime() / 1E6) % 4000, LogLevel.DEBUG);
		setValue(keycode, false);
	}
	
	protected void setTouche(int indice, boolean val)
	{		
		touches[indice] = val ? ETAT_APPUYE : ETAT_RELACHE;
	}
	
	private void setValue(int keycode, boolean val)
	{		
		for (int i = 0; i < config.length; i++)
		{
			if (config[i] == keycode) {
				setTouche(i, val);
			}
		}
	}
	
	public void doActions(DonneesJeu d)
	{
		if (joueur == null)
			return;
		
		Controle[] controles = Controle.values();
		int offsetAppuye = controles.length / 2;
		for (int i = 0; i < touches.length; i++)
		{
			if (touches[i] == ETAT_APPUYE)
				joueur.action(controles[i], d);
			else if (touches[i] == ETAT_RELACHE)
			{
				joueur.action(controles[i + offsetAppuye], d);
				touches[i] = ETAT_VIDE;
			}
		}
	}
	
	public void liberer()
	{
		touches = null;
		joueur = null;
	}
	
}
