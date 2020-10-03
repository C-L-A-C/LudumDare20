package gui;

import controles.ControleurClavier;
import graphiques.Assets;
import jeu.ControleurNiveau;
import jeu.DonneesJeu;
import processing.core.PApplet;

public class Jeu extends Scene {
	
	private DonneesJeu jeu;
	private ControleurNiveau niveau;
	private ControleurClavier controleur;

	@Override
	public void setup(PApplet p) {
		super.setup(p);
		
		Assets.init(p);
		
		jeu = new DonneesJeu();
		
		controleur = new ControleurClavier(jeu.getJoueur());
		
		niveau = new ControleurNiveau(jeu);
		niveau.setNiveauCourant("niveau 1");
	}

	@Override
	public void draw() {
		p.background(0);

		controleur.doActions(jeu);
		
		jeu.afficher(p);
		
		jeu.evoluer();
	}
	
	@Override
	public void keyPressed() {
		controleur.keyPressed(p.keyCode);
	}
	
	@Override
	public void keyReleased() {
		controleur.keyReleased(p.keyCode);
	}

}
