package jeu;

import java.util.List;
import java.util.ArrayList;

/*
 * le controleur d'événements possède plusieurs
 * générateurs d'événements, il se charge de concilier
 * ces générateurs pour créer le bon évenement au bon moment
 * */
public class ControleurEvenements {
	private List<GenerateurEvenements> listeGenerateurs;
	
	public ControleurEvenements() {
		listeGenerateurs = new ArrayList<>();
	}
	
	public void add(GenerateurEvenements gevent) {
		listeGenerateurs.add(gevent);
	}
}
