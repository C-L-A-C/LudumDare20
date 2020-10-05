package gui;

import graphiques.AnimationSet;
import graphiques.Tileset;
import jeu.ControleurNiveau;
import jeu.DonneesJeu;
import processing.core.PApplet;


public class AnimFin extends Scene {
	private static final float SCALE_STEP = 0.0002f;
	private float scale, translateX, translateY;
	private long lastFrameTime;
	private long fstTime;
	private AnimationSet[] persos;
	private static final int[] persoX = {6, 8, 6, 8, 25, 27, 25, 27, 44, 46, 44, 46, 63, 65, 63, 65};
	private static final int[] persoY = {17, 26+2, 40+2, 54+2, 12+2, 26+2, 40+2, 54+2, 12+2, 26+2, 40+2, 54+2, 12+2, 26+2, 40+2, 54+2};
	private DonneesJeu jeu;
	private ControleurNiveau niveau;
	private boolean animated;

	public AnimFin(PApplet p) {
		this.setup(p);
		animated = false;
		lastFrameTime = System.currentTimeMillis();
		fstTime = lastFrameTime;
		scale = 3;
		translateX = 0;
		translateY = 0;
		persos = new AnimationSet[15];

		jeu = new DonneesJeu(true);
		niveau = new ControleurNiveau(jeu);
		if (!niveau.setNiveauCourant("niveau 10")) {
			System.out.println("Erreur : le niveau n'a pas pu etre recupere");
		}
		
		for(int i=0; i<15; i++)
			persos[i] = new AnimationSet(new Tileset("perso", 4, 4), 6, 0);
	}

	@Override
	public void draw() {
		p.scale(scale);
		p.translate(translateX, translateY);
		if(scale >= 0.25) {
			translateX += (float)(System.currentTimeMillis() - lastFrameTime)/60;
			translateY += (float)(System.currentTimeMillis() - lastFrameTime)/60;
			scale /= 1+SCALE_STEP*(float)(System.currentTimeMillis() - lastFrameTime);
			lastFrameTime = System.currentTimeMillis();
		} else
			animated = true;
		p.background(70);
		p.rectMode(PApplet.CORNER);

		jeu.afficher(p);
		
		
		for(int i=0; i<15; i++)
			persos[i].afficher(p, persoY[i]*32, persoX[i]*32, 52, 72);

		jeu.evoluer(System.currentTimeMillis() - fstTime);
		if(animated) {
			p.textSize(130);
			p.fill(230, 255, 230);
			p.text("Oh no! It's useless!", 1200, 600);
			p.text("Press any key to continue...", 1200, 1000);
		}
	}
	
	
	@Override
	public void keyPressed() {
		if(animated && SceneHandler.pAppletInstance.keyCode!=0) {
			SceneHandler.setRunning(new EcranTitre());
		}
	}
}