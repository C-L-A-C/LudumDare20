package jeu.tapis;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import collision.Rectangle;
import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.produit.Produit;

public class Sortie extends Entite {

	private long derniereReduction;
	private boolean updateTempsReduction;
	
	public Sortie(float x, float y) {
		super(x, y, new AffichageImage(Assets.getImage("sortie")));
		derniereReduction = 0;
		updateTempsReduction = false;
		forme = new Rectangle(pos, Tapis.W, Tapis.H);
	}

	/*
	 * @return liste des produits qui sont bien sortis
	 * */
	public List<Produit> reduireCollisions(List<Produit> l, long t) {
		List<Produit> produitsSortis = new ArrayList<>();
		for(Produit p : l) {
			if (p.collision(this.forme) && t-derniereReduction>10) {
				updateTempsReduction = true;
				float w = p.getForme().getW() -2;
				float h = p.getForme().getH() -2;
				if (w<=2 || h<=2) {
					p.detruire();
					produitsSortis.add(p);
				} else {
					p.setTaille(w, h);
				}
			}
		}
		if( updateTempsReduction) {
			updateTempsReduction = false;
			derniereReduction = t;
		}
		return produitsSortis;
	}
	
	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}

}
