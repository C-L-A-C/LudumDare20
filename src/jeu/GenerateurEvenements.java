package jeu;

import java.util.ArrayList;
import java.util.List;

import collision.Point;
import jeu.produit.TypeProduit;

/*
 * Un générateur d'événements gère la création 
 * d'un produit 
 * */
public class GenerateurEvenements {
	private TypeProduit produitGenere;
	private int seed;
	private List<Point> entrees;
	
	public GenerateurEvenements(TypeProduit produit, int graine) {
		this.produitGenere = produit;
		this.seed = graine;
		
		entrees = new ArrayList<Point>();
	}
	
	public void addEntree(Point entree) {
		this.entrees.add(entree);
	}
}
