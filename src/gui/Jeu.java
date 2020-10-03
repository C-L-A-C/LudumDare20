package gui;

import config.Config;
import config.ConfigKey;
import controles.ControleurClavier;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Horloge;
import processing.core.PApplet;

public class Jeu extends Scene {
	
	private DonneesJeu jeu;
	private ControleurClavier controleur;
	private Horloge clock;

	@Override
	public void setup(PApplet p) {
		super.setup(p);
		
		Assets.init(p);
		clock = new Horloge(80);
		
		jeu = new DonneesJeu();
		controleur = new ControleurClavier(jeu.getJoueur());
	}

	@Override
	public void draw() {
		p.background(0);
		p.rectMode(PApplet.CORNER);
		controleur.doActions(jeu);
		
		jeu.afficher(p);
		clock.afficher(p);
		
		jeu.evoluer();
	}
	
	@Override
	public void keyPressed() {
		controleur.keyPressed(p.keyCode);
		if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_PAUSE)) {
			MenuPause pause = new MenuPause(this);
			SceneHandler.setRunning(pause);
		}
	}
	
	@Override
	public void keyReleased() {
		controleur.keyReleased(p.keyCode);
	}

}
