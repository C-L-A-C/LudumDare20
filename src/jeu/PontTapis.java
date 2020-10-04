package jeu;

import graphiques.Assets;
import processing.core.PApplet;
import processing.core.PImage;

public class PontTapis extends Tapis {

	public PontTapis(float x, float y, TypeDirectionTapis direction) {
		super(x, y, direction);
		this.layer = 2;
	}
	
	@Override
	public void afficher(PApplet p)
	{
		PImage img = Assets.getImage("pont");
		int imageW = (int) (W * 1.2), imageH = H;
		
		p.pushMatrix();
		p.translate(getX() + getForme().getW() / 2, getY() + getForme().getH() / 2);
		
		if (direction == TypeDirectionTapis.HAUT || direction == TypeDirectionTapis.BAS)
			p.rotate(PApplet.radians(90));
		
		p.image(img, - imageW / 2, - imageH / 2, imageW, imageH);
		p.popMatrix();
	}

}
