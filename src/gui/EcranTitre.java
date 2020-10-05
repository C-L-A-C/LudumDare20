package gui;

import menu.PLabel;
import processing.core.PApplet;
import utils.Utils;
import graphiques.Assets;
import gui.MenuPrincipal;

public class EcranTitre extends Scene {

	
	public void setup(PApplet p) {
		super.setup(p);
		SceneHandler.playSound("musique", 1, 1, 0, true);
	}
	
	@Override
	public void draw() {
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
