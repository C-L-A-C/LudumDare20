package jeu;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collision.Rectangle;
import graphiques.AffichageRectangle;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;

public class DonneesJeu {
	private Joueur joueur;
	private Scroll scroll;
	private List<Tapis> listeTapis;
	private List<Produit> listeProduits;

	public DonneesJeu() {
		int viewW = 640, viewH = 480;
		joueur = new Joueur(0, 0);
		scroll = new Scroll(viewW, viewH, viewW, viewH);
		listeTapis = new ArrayList<>();
		listeProduits = new ArrayList<>();
		// test tapis
		listeTapis.add(new Tapis(100, 100, 50, 50, TypeDirectionTapis.BAS));
		listeTapis.add(new Tapis(100, 150, 50, 50, TypeDirectionTapis.DROITE));
		//test produits
		listeProduits.add(new Produit(110, 110, new AffichageRectangle(0)) {});
		

	}

	public Entite checkCollision(Entite e) {
		float eW = e.getForme().getW(), eH = e.getForme().getH();

		int width = 640, height = 480;
		Rectangle rectMonde = new Rectangle(eW, eH, width - 2 * eW + 1, height - 2 * eH + 1);
		if (!e.collision(rectMonde))
			return new Mur(0, 0, 0, 0);

		if (e != joueur && e.collision(joueur))
			return joueur;

		for (Tapis t : listeTapis) {
			if (e != t && e.collision(t)) {
				return t;
			}
		}

		return null;
	}

	public void evoluer() {
		long t = System.currentTimeMillis();
		joueur.evoluer(t, this);
		//changement des vitesses des rpoduits
		for (Produit p : listeProduits) {
			p.testTapis(this);
			p.evoluer(t, this);
		}
	}

	public void afficher(PApplet p) {
		scroll.update(joueur);
		p.noStroke();

		p.pushMatrix();
		p.translate(-(int) scroll.getX(), -(int) scroll.getY());

		joueur.afficher(p);

		for (Tapis t : listeTapis) {
			t.afficher(p);
		}
		
		for (Produit prod: listeProduits) {
			prod.afficher(p);
		}

		p.popMatrix();
	}
	
	public void ajouterProduit(Produit produit) {
		listeProduits.add(produit);
	}

	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * @return the listeTapis
	 */
	public List<Tapis> getListeTapis() {
		return listeTapis;
	}

	

	public Produit getProduitZone(Rectangle zone, Set<TypeProduit> keySet) {
		for (Produit p : listeProduits)
		{
			if (keySet.contains(p.getType()) && p.collision(zone))
				return p;
		}
		return null;
	}

}
