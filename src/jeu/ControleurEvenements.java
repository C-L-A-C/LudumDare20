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
				
				x = entree.getCenter().x*tailleCasePixels;
				y = entree.getCenter().y*tailleCasePixels;
				
				
				
				// définit les vitesses initiales
				if(x<0) {
					x += 0.75f*tailleCasePixels;
					y += 0.25f*tailleCasePixels;
					vitesse.x = 50;
					vitesse.y = 0;
				} else if(x>y){
					x -= 0.25f*tailleCasePixels;
					y += 0.25f*tailleCasePixels;
					vitesse.x = -50;
					vitesse.y = 0;
				}
				
				if(y<0) {
					x += 0.25f*tailleCasePixels;
					y += 0.75f*tailleCasePixels;
					vitesse.x = 0;
					vitesse.y = 50;
				} else if(y>x){
					x += 0.25f*tailleCasePixels;
					y -= 0.25f*tailleCasePixels;
					vitesse.x = 0;
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
			int i = (int) ((Math.random() * (listeGenerateurs.size())));
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
