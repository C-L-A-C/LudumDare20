package gui;

import config.Config;
import config.ConfigKey;
import controles.ControleurClavier;
import graphiques.Assets;
import jeu.ControleurNiveau;
import jeu.DonneesJeu;
import jeu.Horloge;
import processing.core.PApplet;

public class Jeu extends Scene {
	
	private DonneesJeu jeu;
	private ControleurNiveau niveau;
	private ControleurClavier controleur;
	private Horloge clock;

	@Override
	public void setup(PApplet p) {
		super.setup(p);
		
		Assets.init(p);
		clock = new Horloge(80);
		
		jeu = new DonneesJeu();
		
		controleur = new ControleurClavier(jeu.getJoueur());
		
		// créé le niveau courant
		niveau = new ControleurNiveau(jeu);
		if(!niveau.setNiveauCourant("niveau 4")) {
			System.out.println("Erreur : le niveau n'a pas pu etre recupere");
		}
		
	}

	@Override
	public void draw() {
		p.background(70);
		p.rectMode(PApplet.CORNER);

		if (! jeu.estEnMiniJeu())
			controleur.doActions(jeu);
		
		jeu.afficher(p);
		clock.afficher(p);
		
		jeu.evoluer((long) (clock.getSeconds() * 1000));
		
		if (clock.journeeFinie())
		{
			System.out.println("Le jeu est fini, on a " + (jeu.estGagne() ? "gagné" : "perdu"));
			// Charger niveau suivant ou afficher menu niveau
		}
	}
	
	@Override
	public void keyPressed() {
		controleur.keyPressed(p.keyCode);
		if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_PAUSE)) {
			MenuPause pause = new MenuPause(p, this);
			this.clock.stopClock();
			SceneHandler.setRunning(pause);
		}
		else if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_OVERLAY))
			jeu.setAffichageOverlay(true);
		
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().keyPressed(p.keyCode);
	}
	
	@Override
	public void keyReleased() {
		controleur.keyReleased(p.keyCode);

		if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_OVERLAY))
			jeu.setAffichageOverlay(false);
		
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().keyReleased(p.keyCode);
	}

	@Override
	public void mouseReleased() {
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().mouseReleased(p.mouseX, p.mouseY, p.mouseButton);
	}

	@Override
	public void mousePressed() {
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().mousePressed(p.mouseX, p.mouseY, p.mouseButton);
	}

	/**
	 * @return the clock
	 */
	public Horloge getClock() {
		return clock;
	}
	
	

}
