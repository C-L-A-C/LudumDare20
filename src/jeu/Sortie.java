package jeu;


import java.util.List;

import collision.Rectangle;
import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.produit.Produit;

public class Sortie extends Entite {

	public static int W = 40, H = 40;
	
	public Sortie(float x, float y) {
		super(x, y, new AffichageImage(Assets.getImage("default")));
		
	
		this.forme = new Rectangle(pos, W, H);
	}

	public void reduireCollisions(List<Produit> l) {
		for(Produit p: l) {
			if (p.collision(this)) {
				float w = p.getForme().getW() -2;
				float h = p.getForme().getH() -2;
				if (w<=0 || h<=0) {
					p.detruire();
					l.remove(p);
				} else {
					p.setTaille(w, h);
				}
			}
		}
	}
	
	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}
}
