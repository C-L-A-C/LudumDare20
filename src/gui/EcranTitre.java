package gui;

import menu.PLabel;
import processing.core.PApplet;
import utils.Utils;
import config.Config;
import config.ConfigKey;
import graphiques.Assets;
import gui.MenuPrincipal;

public class EcranTitre extends Scene {

	
	public void setup(PApplet p) {
		super.setup(p);
		SceneHandler.playSoundFast("assets/sounds/musique.wav");
	}
	
	@Override
	public void draw() {
		p.background(0);
		PLabel titre = new PLabel(p.width / 2, p.height / 2 - 50, "CAN'T  STOP  PRODUCING", p);
		titre.textSize(40);
		titre.refreshSize(p);
		titre.setColor(Utils.color(128, 0, 0));
		titre.afficher(p);
		PLabel pressAKey = new PLabel(p.width / 2, 4 * p.height /5, "PRESS A KEY", p);
		pressAKey.textSize(20);
		pressAKey.refreshSize(p);
		pressAKey.setColor(128);
		pressAKey.afficher(p);
		
		this.handleButtons();
	}
	
	@Override
	public void keyPressed() {
		SceneHandler.setRunning(new MenuPrincipal(p));
	}
}
