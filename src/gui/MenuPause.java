package gui;

import menu.PButton;
import processing.core.PApplet;

public class MenuPause extends Scene {
	
	private PButton buttonResume, buttonReturnMenu, buttonQuit;
	private Jeu jeu;
	
	public MenuPause(PApplet p, Jeu jeu) {
		this.setup(p);
		float widthButton = p.width / 3;
		float heightButton = p.height / 5;
		this.buttonResume = new PButton(p.width / 2, p.height / 4 , widthButton, heightButton, "RESUME");
		this.buttonReturnMenu = new PButton(p.width / 2, 2 * p.height / 4, widthButton, heightButton, "RETURN MENU");
		this.buttonQuit = new PButton(p.width / 2, 3 * p.height / 4 , widthButton, heightButton, "QUIT");
		this.jeu = jeu;
	}
	
	
	@Override
	public void draw() {
		p.background(0);
		this.buttonResume.afficher(p);
		this.buttonReturnMenu.afficher(p);
		this.buttonQuit.afficher(p);
	}

	@Override
	public void mousePressed() {
		if (buttonResume.contient(p.mouseX, p.mouseY)) {
			this.jeu.getClock().restartClock();
			SceneHandler.setRunningWithoutClosing(this.jeu);
		}
		if (buttonReturnMenu.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new MenuPrincipal(p));
		}
		if (buttonQuit.contient(p.mouseX, p.mouseY)) {
			p.exit();
		}
	}

}
