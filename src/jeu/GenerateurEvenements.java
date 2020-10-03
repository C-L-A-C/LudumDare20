package jeu;

import jeu.produit.Produit;

/*
 * Un générateur d'événements gère la création 
 * d'un produit 
 * */
public class GenerateurEvenements {
	private Produit produitGenere;
	
	public GenerateurEvenements(Produit produit) {
		this.produitGenere = produit;
	}
}
