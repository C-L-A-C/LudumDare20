package gui;

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
		
		niveau = new ControleurNiveau(jeu);
		niveau.setNiveauCourant("niveau 1");
	}

	@Override
	public void draw() {
		p.background(0);


		if (! jeu.estEnMiniJeu())
			controleur.doActions(jeu);
		
		jeu.afficher(p);
		clock.afficher(p);
		
		jeu.evoluer();
	}
	
	@Override
	public void keyPressed() {
		controleur.keyPressed(p.keyCode);
		
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
		if (jeu.estEnMiniJeu())
			jeu.getMiniJeu().mousePressed(p.mouseX, p.mouseY, p.mouseButton);
	}

}
