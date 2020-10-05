package gui;

import menu.PButton;
import menu.PLabel;
import processing.core.PApplet;

public class EcranFinNiveau extends Scene {
	
	private PLabel titre;
	private PButton retour, jouer;
	private boolean gagne;
	private int niveauSuivant;
	
	public EcranFinNiveau(boolean gagne, int niveauSuivant)
	{
		this.gagne = gagne;
		this.niveauSuivant = niveauSuivant;
	}
	
	@Override
	public void setup(PApplet p)
	{
		super.setup(p);

		titre = new PLabel(p.width / 2, 1 * p.height / 8, 200, 80, "You have " + (gagne ? "won" : "failed") + " !");
		jouer = new PButton(p.width / 2, 4 * p.height / 8, 200, 80, gagne ? "NEXT LEVEL" : "RETRY");
		retour = new PButton(p.width / 2, 6 * p.height / 8, 200, 80, "BACK TO MENU");
	}

	@Override
	public void draw() {
		p.background(0);
		
		titre.afficher(p);
		jouer.afficher(p);
		retour.afficher(p);
		
	}
	
	@Override
	public void mousePressed()
	{
		if (jouer.contient(p.mouseX, p.mouseY))
		{
			Jeu jeu = new Jeu(niveauSuivant);
			SceneHandler.setRunning(jeu);
		}
		else if (retour.contient(p.mouseX, p.mouseY))
			SceneHandler.setRunning(new MenuPrincipal(p));
	}

}
