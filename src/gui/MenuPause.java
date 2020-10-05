package gui;

import menu.PButton;
import processing.core.PApplet;

public class MenuPause extends Scene {
	
	private PButton buttonResume, buttonRetry, buttonTuto, buttonReturnMenu, buttonQuit;
	private Jeu jeu;
	
	public MenuPause(PApplet p, Jeu jeu) {
		this.setup(p);
		float widthButton = p.width / 3;
		float heightButton = p.height / 6;
		this.buttonResume = new PButton(p.width / 4, p.height / 5 , widthButton, heightButton, "RESUME");
		this.buttonRetry = new PButton(3 * p.width / 4, p.height / 5, widthButton, heightButton, "RETRY");
		this.buttonTuto = new PButton(p.width / 2, 2 * p.height / 5, widthButton, heightButton, "TUTO");
		this.buttonReturnMenu = new PButton(p.width / 2, 3 * p.height / 5, widthButton, heightButton, "RETURN MENU");
		this.buttonQuit = new PButton(p.width / 2, 4 * p.height / 5 , widthButton, heightButton, "QUIT");
		this.jeu = jeu;
	}
	
	
	@Override
	public void draw() {
		p.background(0);
		this.buttonResume.afficher(p);
		this.buttonRetry.afficher(p);
		this.buttonTuto.afficher(p);
		this.buttonReturnMenu.afficher(p);
		this.buttonQuit.afficher(p);
		this.handleButtons();
	}

	@Override
	public void mousePressed() {
		super.mousePressed();
		if (buttonResume.contient(p.mouseX, p.mouseY)) {
			this.jeu.getClock().restartClock();
			SceneHandler.setRunningWithoutClosing(this.jeu);
		}
		if (buttonRetry.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new Jeu(this.jeu.getNumeroNiveau()));
		}
		if (buttonTuto.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunningWithoutClosing(new EcranTuto(p,this));
		}
		if (buttonReturnMenu.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new MenuPrincipal(p));
		}
		if (buttonQuit.contient(p.mouseX, p.mouseY)) {
			p.exit();
		}
	}

}
