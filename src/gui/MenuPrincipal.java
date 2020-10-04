package gui;

import menu.PButton;
import processing.core.PApplet;


public class MenuPrincipal extends Scene {

	private PButton buttonCampaign, buttonCredits, buttonQuit;
	
	public MenuPrincipal(PApplet p) {
		this.setup(p);
		float widthButton = p.width / 3;
		float heightButton = p.height / 5;
		this.buttonCampaign = new PButton(p.width  / 2,  p.height / 4 , widthButton, heightButton, "CAMPAIGN");
		this.buttonCredits = new PButton(p.width / 2, 2 * p.height  / 4 , widthButton, heightButton, "CREDITS");
		this.buttonQuit = new PButton(p.width / 2, 3 * p.height / 4 , widthButton, heightButton, "QUIT");
	}
	
	@Override
	public void draw() {
		p.background(0);
		this.buttonCampaign.afficher(p);
		this.buttonCredits.afficher(p);
		this.buttonQuit.afficher(p);
	}
	
	@Override
	public void mousePressed() {
		if (buttonCampaign.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new Jeu(2));
		}
		if (buttonCredits.contient(p.mouseX, p.mouseY)) {
			//TODO
		}
		if (buttonQuit.contient(p.mouseX, p.mouseY)) {
			p.exit();
		}
	}

}
