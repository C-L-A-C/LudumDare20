package jeu;

import java.util.List;

import collision.Point;

import java.util.ArrayList;

/*
 * le controleur d'événements possède plusieurs
 * générateurs d'événements, il se charge de concilier
 * ces générateurs pour créer le bon évenement au bon moment
 * */
public class ControleurEvenements {
	private List<GenerateurEvenements> listeGenerateurs;
	private List<Point> sorties;
	
	
	public ControleurEvenements() {
		listeGenerateurs = new ArrayList<GenerateurEvenements>();
		sorties = new ArrayList<Point>();
	}
	
	public void addGenerateurEvenements(GenerateurEvenements gevent) {
		listeGenerateurs.add(gevent);
	}
	
	public void addSortie(Point sortie) {
		this.sorties.add(sortie);
	}
}
