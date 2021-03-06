package gui;

import config.Config;
import config.ConfigKey;
import controles.ControleurClavier;
import graphiques.Assets;
import jeu.ControleurNiveau;
import jeu.DonneesJeu;
import jeu.Horloge;
import jeu.mini.BoutonsMemoire;
import jeu.mini.RangeProduits;
import processing.core.PApplet;

public class Jeu extends Scene {

	private DonneesJeu jeu;
	private ControleurNiveau niveau;
	private ControleurClavier controleur;
	private Horloge clock;
	private int numeroNiveau;

	public Jeu(int numeroNiveau) {
		// créé le niveau courant
		jeu = new DonneesJeu(numeroNiveau);

		clock = new Horloge(60);

		controleur = new ControleurClavier(jeu.getJoueur());
		niveau = new ControleurNiveau(jeu);
		this.numeroNiveau = numeroNiveau;

		if (!niveau.setNiveauCourant("niveau " + numeroNiveau)) {
			System.out.println("Erreur : le niveau n'a pas pu etre recupere");
		}

		clock = jeu.getHorloge();

		RangeProduits.resetDifficulty();
		BoutonsMemoire.resetDifficulty();
	}

	@Override
	public void setup(PApplet p) {
		super.setup(p);
	}

	@Override
	public void draw() {
		p.background(70);
		p.rectMode(PApplet.CORNER);

		if (!jeu.estEnMiniJeu())
			controleur.doActions(jeu);

		jeu.afficher(p);
		clock.afficher(p);
		
		p.image(Assets.getImage("horaire"), p.width - 75, 78, 63, 44);

		//p.text(""+jeu.getSCore(), 5, 25);

		jeu.evoluer((long) (clock.getSeconds() * 1000));

		if (clock.journeeFinie() || jeu.estFini()) {
			SceneHandler.setRunning(new EcranFinNiveau(jeu.estGagne(), numeroNiveau + (jeu.estGagne() ? 1 : 0),jeu.getScores()));
		}

		this.handleButtons();
	}

	@Override
	public void keyPressed() {
		controleur.keyPressed(p.keyCode);
		if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_PAUSE)) {
			MenuPause pause = new MenuPause(p, this);
			this.clock.stopClock();
			SceneHandler.setRunning(pause);
		} else if (p.keyCode == Config.readKey(ConfigKey.TOUCHE_OVERLAY))
			jeu.setAffichageOverlay(!jeu.getAffichageOverlay());

		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().keyPressed(p.keyCode);
	}

	@Override
	public void keyReleased() {
		controleur.keyReleased(p.keyCode);

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
		super.mousePressed();
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().mousePressed(p.mouseX, p.mouseY, p.mouseButton);
	}

	/**
	 * @return the clock
	 */
	public Horloge getClock() {
		return clock;
	}

	public int getNumeroNiveau() {
		return numeroNiveau;
	}

}
