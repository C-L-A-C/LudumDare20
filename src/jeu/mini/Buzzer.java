package jeu.mini;

import java.util.ArrayList;
import java.util.List;

import collision.Point;
import collision.Rectangle;
import jeu.machine.Machine;
import processing.core.PApplet;
import processing.core.PVector;

public class Buzzer extends MiniJeu {
	
	private List<PVector> points;
	private boolean perdu, gagne, commence;
	private int hauteur;

	protected Buzzer(Machine machine) {
		super(machine);
		points = new ArrayList<>();
		perdu = false;
		gagne = false;
		commence = false;
		hauteur = 32;
	}

	@Override
	public void afficher(PApplet p) {
		
		p.noStroke();
		p.fill(128);
		p.rect(50, 50, p.width - 100, p.height - 100);
		
		p.strokeWeight(1);
		p.stroke(20);

		PVector debut = new PVector(60, (float) getForX(p, 70));
		PVector fin = new PVector(p.width - 100, (float) getForX(p, p.width - 100));

		p.fill(20, 255, 20);
		p.rect(debut.x, debut.y - hauteur, 10, hauteur);
		p.fill(255, 255, 20);
		p.rect(fin.x, fin.y - hauteur, 10, hauteur);
		p.strokeWeight(3);
		
		float lastY = (float) getForX(p, 70);
		for (int x = 70; x < p.width -100; x++) {
			float y = (float) getForX(p, x);
			p.line(x - 1, lastY, x, y);
			p.line(x - 1, lastY - hauteur, x, y - hauteur);
			lastY = y;
		}
		
		if ((new Rectangle(60, (float) getForX(p, 70) - hauteur, 10, hauteur)).collision(new Point(p.mouseX, p.mouseY)))
			commence = true;
		
		if (! commence) {
			p.fill(255);
			p.textSize(32);
			p.text("Placez vous dans la zone verte !", p.width / 2, 100);
		} else if (p.mouseX > 70) {
			
			p.stroke(200, 200, 0);
			
			PVector lastPos = points.isEmpty() ? null : points.get(0);
			for (PVector pos : points)
			{
				p.line(lastPos.x, lastPos.y, pos.x, pos.y);
				lastPos = pos;
			}
			
			lastPos = points.isEmpty() ? new PVector(p.mouseX, p.mouseY) : points.get(points.size() - 1);
			
			points.add(new PVector(p.mouseX, p.mouseY));
			double targetY = getForX(p, p.mouseX);

			if (p.mouseX >= p.width - 100)
				gagne = true;
			else if (p.mouseY <= targetY - hauteur - 3 || p.mouseY >= targetY - 3 || p.mouseX < lastPos.x)
				perdu = true;
		}

	}
	
	public double getForX(PApplet p, int x)
	{
		return (p.height - 100) / 2 + 50 + p.noise(x / 100.f) * (p.height - 100) / 2;
	}

	@Override
	public boolean evoluer() {
		return !perdu && !gagne;
	}

	@Override
	public boolean estReussi() {
		return !perdu;
	}

}
