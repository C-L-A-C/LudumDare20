package jeu.tapis;

import processing.core.PApplet;
import processing.core.PImage;

public class TapisRapide extends Tapis {

	public TapisRapide(float x, float y, TypeDirectionTapis direction) {
		super(x, y, direction);
		vitesse = 85;
	}
	
	@Override
	public void afficher(PApplet p)
	{
		PImage img = animations.get(direction.ordinal() + 4).getFrame();
		p.image(img, getX(), getY(), getForme().getW(), getForme().getH());
		p.noTint();
	}

}
