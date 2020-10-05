package jeu;

import java.util.ArrayList;
import java.util.List;

import collision.Point;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;

/*
 * Un générateur d'événements gère la création 
 * d'un produit 
 * */
public class GenerateurEvenements {
	private TypeProduit typeProduit;
	
	private long tempsDernierProduitCree;
	private long tpsApparition;
	private long decalage;
	
	private List<Point> entrees;
	
	public GenerateurEvenements(TypeProduit produit, int spawntime, int offset) {
		typeProduit = produit;
		
		tpsApparition = spawntime*1000;
		
		if(offset<spawntime) {
			decalage = -offset*1000;
		} else {
			decalage = offset*1000;
		}
		
		tempsDernierProduitCree = System.currentTimeMillis() + decalage;
		
		entrees = new ArrayList<Point>();
	}
	
	public Point creerNouveauProduit() {
		long t = System.currentTimeMillis();
		if(!entrees.isEmpty()) {
			if (t - tempsDernierProduitCree > tpsApparition) {
				tempsDernierProduitCree = t;
				return choisirEntree();
			}
		} else {
			System.out.println("Erreur generateur d'evenements : un generateur d'evenement a ete defini sans entree(s)");
		}
		return null;
	}
	
	private Point choisirEntree() {
		int i = (int) ((Math.random() * (entrees.size())));
		return entrees.get(i);
	}
	
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}
	
	public void addEntree(Point entree) {
		this.entrees.add(entree);
	}
}
