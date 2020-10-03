package jeu;

import java.util.List;
import java.util.ArrayList;

public class ControleurEvenements {
	private List<GenerateurEvenements> listeGenerateurs;
	
	public ControleurEvenements() {
		listeGenerateurs = new ArrayList<>();
	}
	
	public void add(GenerateurEvenements gevent) {
		listeGenerateurs.add(gevent);
	}
}
