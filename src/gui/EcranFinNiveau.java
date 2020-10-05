package gui;

import menu.PButton;
import menu.PLabel;
import processing.core.PApplet;

public class EcranFinNiveau extends Scene {
	
	private PLabel titre;
	private PButton retour, jouer;
	private boolean gagne;
	private int niveauSuivant;
	
	private int[] scores;
	private PLabel score;
	private PLabel nbProduits;
	private PLabel nbCacas;
	private PLabel tempsRestant;
	
	
	public EcranFinNiveau(boolean gagne, int niveauSuivant, int[] scores) {
		this.scores = scores;
		this.gagne = gagne;
		this.niveauSuivant = niveauSuivant;
	}
	
	@Override
	public void setup(PApplet p)
	{
		super.setup(p);
		titre = new PLabel(p.width / 2, 1 * p.height / 16, 200, 80, "You have " + (gagne ? "won" : "failed") + " !");
		score = new PLabel(p.width / 2, 2 * p.height / 16, 200, 80, "Score : "+scores[0]);
		nbProduits = new PLabel(p.width / 2, 4 * p.height / 16, 200, 80, "Products on conveyor belts : "+scores[1]);
		nbCacas = new PLabel(p.width / 2, 6 * p.height / 16, 200, 80, "Waste on conveyor belts : "+scores[2]);
		tempsRestant = new PLabel(p.width / 2, 8 * p.height / 16, 200, 80, "Time left : "+scores[3]+" s");
		jouer = new PButton(p.width / 2, 12 * p.height / 16, 200, 80, gagne ? "NEXT LEVEL" : "RETRY");
		retour = new PButton(p.width / 2, 14 * p.height / 16, 200, 80, "BACK TO MENU");
		
		p.cursor(p.ARROW);
	}

	@Override
	public void draw() {
		p.background(0);
		
		titre.afficher(p);
		jouer.afficher(p);
		retour.afficher(p);
		
		this.handleButtons();
	}
	
	@Override
	public void mousePressed()
	{
		super.mousePressed();
		if (jouer.contient(p.mouseX, p.mouseY))
		{
			Jeu jeu = new Jeu(niveauSuivant);
			SceneHandler.setRunning(jeu);
		}
		else if (retour.contient(p.mouseX, p.mouseY))
			SceneHandler.setRunning(new MenuPrincipal(p));
	}

}
