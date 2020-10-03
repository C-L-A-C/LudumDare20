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
	private TypeProduit typeProduit;
	private int seed;
	private List<Point> entrees;
	
	public GenerateurEvenements(TypeProduit produit, int graine) {
		this.typeProduit = produit;
		this.seed = graine;
		
		entrees = new ArrayList<Point>();
	}
	
	public Point creerNouveauProduit() {
		if(!entrees.isEmpty()) {
			return choisirEntree();
		} else {
			System.out.println("Erreur generateur d'evenements : un generateur d'evenement a ete defini sans entree(s)");
			return null;
		}
	}
	
	private Point choisirEntree() {
		if(seed==1) {
			int i = (int) ((Math.random() * (entrees.size()-1)));
			return entrees.get(i);
		}
		return new Point(1,1);
	}
	
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}
	
	public void addEntree(Point entree) {
		this.entrees.add(entree);
	}
}
