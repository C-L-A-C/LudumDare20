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
	private int tileW;
	private int tileH;
	
	private int seed;
	
	private int tailleCasePixels;
	
	
	public ControleurEvenements(int tailleCase) {
		listeGenerateurs = new ArrayList<GenerateurEvenements>();
		tailleCasePixels = tailleCase;
	}
	
	public void evoluer(DonneesJeu j) {
		float x;
		float y;
		
		if(!listeGenerateurs.isEmpty()) {
			for(GenerateurEvenements gevent:listeGenerateurs) {
				Point entree = gevent.creerNouveauProduit();
				if(entree != null) {

					PVector vitesse = new PVector();
					
					// définit le point d'apparition exact
					
					x = entree.getCenter().x*tailleCasePixels + tailleCasePixels / 2 - Produit.W /2;
					y = entree.getCenter().y*tailleCasePixels + tailleCasePixels / 2 - Produit.H /2;
					
					
					
					// définit les vitesses initiales
					if(x>=0 && x<tileW && y>=0 && y<tileH) {
//						x += 0.25f*tailleCasePixels;
//						y -= 0.25f*tailleCasePixels;
						vitesse.x = 0;
						vitesse.y = 0;
					}
					
					if(x<0) {
//						x += 0.75f*tailleCasePixels;
//						y += 0.25f*tailleCasePixels;
						vitesse.x = 50;
						vitesse.y = 0;
					} else if(x>y){
//						x -= 0.25f*tailleCasePixels;
//						y += 0.25f*tailleCasePixels;
						vitesse.x = -50;
						vitesse.y = 0;
					}
					
					if(y<0) {
//						x += 0.25f*tailleCasePixels;
//						y += 0.75f*tailleCasePixels;
						vitesse.x = 0;
						vitesse.y = 50;
					} else if(y>x){
//						x += 0.25f*tailleCasePixels;
//						y -= 0.25f*tailleCasePixels;
						vitesse.x = 0;
						vitesse.y = -50;
					}
					
					
					Produit p = new Produit(x,y,gevent.getTypeProduit());
					p.setVitesse(vitesse);
					j.ajouterProduit(p);
				}
			}
		} else {
			System.out.println("Erreur controleur d'evenements : pas de generateur d'evenements");
		}
	}
	
	public void setGraine(int graine) {
		seed = graine;
	}
	
	public void addGenerateurEvenements(GenerateurEvenements gevent) {
		listeGenerateurs.add(gevent);
	}
	
	public void setTaille(int W, int H) {
		tileW = W;
		tileH = H;
	}
}
