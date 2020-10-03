package graphiques;

import processing.core.PApplet;

public class AffichageRectangle implements Apparence {
	
	private int color;

	public AffichageRectangle(int color) {
		this.color = color;
	}

	@Override
	public void afficher(PApplet p, int x, int y, int w, int h) {
		p.noStroke();
		p.fill(color);
		p.rect(x, y, w, h);
	}

}
