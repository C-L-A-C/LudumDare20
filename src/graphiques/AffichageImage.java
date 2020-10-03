package graphiques;

import processing.core.PApplet;
import processing.core.PImage;

public class AffichageImage implements Apparence {
	
	private PImage img;
	
	public AffichageImage(PImage img)
	{
		this.img = img;
	}

	@Override
	public void afficher(PApplet p, int x, int y, int w, int h) {
		p.image(img, x, y, w, h);
	}
	
	

}
