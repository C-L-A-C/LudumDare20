package jeu;

import java.util.List;

import collision.Point;
import jeu.produit.Produit;
import processing.core.PVector;

import java.util.ArrayList;

/*
 * le controleur d'événements possède plusieurs
 * générateurs d'événements, il se charge de concilier
 * ces générateurs pour créer le bon évenement au bon moment
 * */
public class ControleurEvenements {
	private List<GenerateurEvenements> listeGenerateurs;
	List<Produit> produits;
	private List<Point> sorties;
	
	private int seed;
	
	private int tailleCasePixels;
	
	
	public ControleurEvenements(int tailleCase) {
		listeGenerateurs = new ArrayList<GenerateurEvenements>();
		sorties = new ArrayList<Point>();
		tailleCasePixels = tailleCase;
		produits = new ArrayList<Produit>();
	}
	
	public Produit creerNouveauProduit() {
		produits.clear();
		float x;
		float y;
		
		if(!listeGenerateurs.isEmpty()) {
			for(GenerateurEvenements gevent:listeGenerateurs) {
				Point entree = gevent.creerNouveauProduit();
				
				PVector vitesse = new PVector();
				
				// définit le point d'apparition exact
				x = (entree.getW()-0.5f)*tailleCasePixels;
				y = (entree.getH()-0.5f)*tailleCasePixels;
				
				// définit les vitesses initiales
				if(entree.getH()==-1) {
					vitesse.x = 50;
				} else {
					vitesse.x = -50;
				}
				
				if(entree.getW()==-1) {
					vitesse.y = 50;
				} else {
					vitesse.y = -50;
				}
				
				Produit p = new Produit(x,y,gevent.getTypeProduit());
				p.setVitesse(vitesse);
				produits.add(p);
			}
		} else {
			System.out.println("Erreur controleur d'evenements : pas de generateur d'evenements");
			return null;
		}
		
		return choisirProduit();
	}
	
	private Produit choisirProduit() {
		if(seed==1) {
			int i = (int) ((Math.random() * (listeGenerateurs.size()-1)));
			return produits.get(i);
		}
		return produits.get(0);
	}
	
	public void setGraine(int graine) {
		seed = graine;
	}
	
	public void addGenerateurEvenements(GenerateurEvenements gevent) {
		listeGenerateurs.add(gevent);
	}
	
	public void addSortie(Point sortie) {
		this.sorties.add(sortie);
	}
}
