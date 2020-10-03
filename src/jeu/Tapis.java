package jeu;

import collision.Rectangle;
import graphiques.AffichageRectangle;
import processing.core.PApplet;

public class Tapis extends Entite {
	
	protected float w, h;
	protected TypeDirectionTapis direction;
	
	public Tapis(float x, float y, float w, float h, TypeDirectionTapis direction) {
		super(x, y, new AffichageRectangle(0));
		this.w = w;
		this.h = h;
		this.forme = new Rectangle(pos, this.w, this.h);
		this.direction = direction;
	}
	
	public void afficher(PApplet p) {
		p.fill(255, 255, 255);
		p.rect(pos.x, pos.y, w, h);
		p.fill(255, 0, 0);
		switch(this.direction) {
			case HAUT:
				p.triangle(pos.x, pos.y + h, pos.x + w, pos.y + h, pos.x + w / 2, pos.y);
				break;
			case BAS:
				p.triangle(pos.x,  pos.y,  pos.x + w,  pos.y,  pos.x + w / 2,  pos.y + h);
				break;
			case GAUCHE:
				p.triangle(pos.x + w, pos.y, pos.x + w, pos.y + h, pos.x, pos.y + h / 2);
				break;
			case DROITE:
				p.triangle(pos.x,  pos.y,  pos.x + w,  pos.y + h / 2,  pos.x,  pos.y + h);				
				break;
		}
	}
		

	/**
	 * @return the w
	 */
	public float getW() {
		return w;
	}

	/**
	 * @return the h
	 */
	public float getH() {
		return h;
	}

	/**
	 * @return the direction
	 */
	public TypeDirectionTapis getDirection() {
		return direction;
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
		// TODO Auto-generated method stub

	}

}
