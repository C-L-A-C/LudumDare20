package controles;

import jeu.DonneesJeu;

public abstract class Controleur {

	/**
	 * Joueur qu'on controle
	*/
	protected Controlable joueur;
	
	public Controleur(Controlable c)
	{
		joueur = c;
	}
	
	public void attacher(Controlable c)
	{
		joueur = c;
	}
	
	public abstract void doActions(DonneesJeu d);
}
