package jeu;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collision.Rectangle;
import controles.ControleurClavier;
import graphiques.AffichageRectangle;
import jeu.machine.Machine;
import jeu.mini.MiniJeu;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;

public class DonneesJeu {
	private Joueur joueur;
	private Scroll scroll;
	private List<Tapis> listeTapis;
	private List<Produit> listeProduits;
	
	private MiniJeu miniJeuCourant;

	public DonneesJeu() {
		int viewW = 640, viewH = 480;
		joueur = new Joueur(0, 0);
		scroll = new Scroll(viewW, viewH, viewW, viewH);
		listeTapis = new ArrayList<>();
		listeProduits = new ArrayList<>();
		
		miniJeuCourant = null;
		
		// test tapis
		listeTapis.add(new Tapis(100, 100, 50, 50, TypeDirectionTapis.BAS));
		listeTapis.add(new Tapis(100, 150, 50, 50, TypeDirectionTapis.DROITE));
		listeTapis.add(new Tapis(150, 150, 50, 50, TypeDirectionTapis.HAUT));
		listeTapis.add(new Tapis(150, 100, 50, 50, TypeDirectionTapis.GAUCHE));

		//test produits
		listeProduits.add(new Produit(111, 111, TypeProduit.METAL));
		

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

	public void evoluer(long t) {
		joueur.evoluer(t, this);
		//changement des vitesses des produits
		for (Produit p : listeProduits) {
			p.testTapis(this);
			p.evoluer(t, this);
		}
		
		if (estEnMiniJeu())
			miniJeuCourant.evoluer();
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
		
		if (estEnMiniJeu())
			miniJeuCourant.afficher(p);
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

	public void setMiniJeu(Machine machine, TypeMiniJeu type) {
		miniJeuCourant = MiniJeu.createMiniJeu(type, machine);
	}

	public boolean estEnMiniJeu() {
		return getMiniJeu() != null;
	}

	public MiniJeu getMiniJeu() {
		return miniJeuCourant;
	}

}
