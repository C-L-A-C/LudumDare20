package gui;

import java.util.ArrayList;
import java.util.List;

import menu.PButton;
import menu.PLabel;
import processing.core.PApplet;
import processing.core.PFont;

public class EcranFinNiveau extends Scene {
	
	private PButton retour, jouer;
	private boolean gagne;
	private int niveauSuivant;
	
	private int[] scores;
	private List<PLabel> labels;
	
	
	public EcranFinNiveau(boolean gagne, int niveauSuivant, int[] scores) {
		this.scores = scores;
		this.gagne = gagne;
		this.niveauSuivant = niveauSuivant;
		labels = new ArrayList<>();
	}
	
	@Override
	public void setup(PApplet p)
	{
		super.setup(p);

		PFont fontClassic = p.createFont("assets/Asap-Regular.otf", 28);
		p.textFont(fontClassic);
		
		String[] texts = {
				"You have " + (gagne ? "won" : "failed") + " !",
				"Total produced items : " + scores[1],
				"Time left : " + scores[2] + "s",
				"Products on conveyor belts : " + scores[3],
				"Waste on conveyor belts : " + scores[4],				
					};
		for (int i = 0; i < texts.length; i++)
			labels.add(new PLabel(p.width / 2, ((i == 0 ? -1 : 0) + (i + 2)) * p.height / 14, 300, 80, texts[i]));
		
		for (int i = 0; i < 4; i++)
		{
			PLabel label = new PLabel(p.width / 2 + 200, (i + 3) * p.height / 14, 50, 80, (scores[5 + i] >= 0 ? "+ " : "") + scores[5 + i]);
			label.setWidth(50);
			labels.add(label);
		}

		labels.add(new PLabel(p.width / 2, 7 * p.height / 14, 200, 80, "Total score"));
		labels.add(new PLabel(p.width / 2 + 200, 7 * p.height / 14, 200, 80, ""+scores[0]));
		jouer = new PButton(p.width / 2, 10 * p.height / 14, 200, 80, gagne ? "NEXT LEVEL" : "RETRY");
		retour = new PButton(p.width / 2, 13 * p.height / 14 - 10, 200, 80, "BACK TO MENU");
		
		p.cursor(p.ARROW);
	}

	@Override
	public void draw() {
		p.background(0);
		
		for (PLabel l : labels)
			l.afficher(p);
		
		jouer.afficher(p);
		retour.afficher(p);
		
		p.stroke(255);
		p.strokeWeight(3);
		p.line(p.width / 2 + 160, 3 * p.height / 14 - 15, p.width / 2 + 160, 7 * p.height / 14 + 15);
		p.line(p.width / 2 - 150, 7 * p.height / 14 - 10, p.width / 2 + 230, 7 * p.height / 14 - 10);
		
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
