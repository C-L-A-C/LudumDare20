package gui;

import menu.PButton;
import processing.core.PApplet;

public class MenuPause extends Scene {
	
	private PButton buttonResume, buttonQuit;
	private Jeu jeu;
	
	public MenuPause(PApplet p, Jeu jeu) {
		this.setup(p);
		float widthButton = p.width / 3;
		float heightButton = p.height / 5;
		this.buttonResume = new PButton((p.width - widthButton) / 2, (p.height - heightButton) / 3 , widthButton, heightButton, "RESUME");
		this.buttonQuit = new PButton((p.width - widthButton) / 2, 2 * (p.height - heightButton) / 3 , widthButton, heightButton, "QUIT");
		this.jeu = jeu;
	}
	
	
	@Override
	public void draw() {
		p.background(0);
		this.buttonResume.afficher(p);
		this.buttonQuit.afficher(p);
	}

	@Override
	public void mousePressed() {
		if (buttonResume.contient(p.mouseX, p.mouseY)) {
			this.jeu.getClock().restartClock();
			SceneHandler.setRunningWithoutClosing(this.jeu);
		}
		if (buttonQuit.contient(p.mouseX, p.mouseY)) {
			p.exit();
		}
	}

}
