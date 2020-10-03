package jeu;

import collision.Rectangle;
import graphiques.AffichageRectangle;
import processing.core.PApplet;

public class Mur extends Entite{
		
	protected float w, h;
	
	public Mur(float x, float y, float w, float h) {
		super(x, y, new AffichageRectangle(0));
		this.w = w;
		this.h = h;
		forme = new Rectangle(pos, this.w, this.h);
	}

	@Override
	public void afficher(PApplet p) {
		if (estDetruit())
			return; 
		p.fill(255, 255, 255);
		p.rect(pos.x, pos.y, w, h);
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}
}
