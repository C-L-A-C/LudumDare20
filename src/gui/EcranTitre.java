package gui;

import menu.PLabel;
import processing.core.PApplet;
import utils.Utils;
import graphiques.Assets;
import gui.MenuPrincipal;

public class EcranTitre extends Scene {

	
	public void setup(PApplet p) {
		super.setup(p);
		SceneHandler.playSoundMusique("assets/sounds/musique.wav", (float)1);
	}
	
	@Override
	public void draw() {
		p.background(0);
		PLabel titre = new PLabel(p.width / 2, p.height / 2, "TROUVER UN NOM", p);
		titre.textSize(40);
		titre.refreshSize(p);
		titre.setColor(Utils.color(128, 0, 0));
		titre.afficher(p);
		PLabel pressAKey = new PLabel(p.width / 2, 4 * p.height /5, "PRESS A KEY", p);
		pressAKey.textSize(20);
		pressAKey.refreshSize(p);
		pressAKey.setColor(128);
		pressAKey.afficher(p);
	}
	
	@Override
	public void keyPressed() {
		SceneHandler.setRunning(new MenuPrincipal(p));
	}


	

}
