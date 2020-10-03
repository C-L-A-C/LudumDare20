package jeu;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donnees_jeu;
	
	public ControleurNiveau(DonneesJeu jeu) {
		this.donnees_jeu = jeu;
	}
	
	/*
	 * récupère le niveau de nom "nom"
	 * dans le fichier des niveaux
	 * */
	private void parseNiveau(String nom) {
		// TODO chemin du fichier des niveaux
		String chemin_niveaux = "";
		
		try {
		      File fichier_niveaux = new File(chemin_niveaux);
		      Scanner scanner = new Scanner(fichier_niveaux);
		      while (scanner.hasNextLine()) {
		        String data = scanner.nextLine();
		        // TODO faire l'analyse syntaxique
		      }
		      scanner.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Erreur dans la lecteure du fichier des niveaux");
		      e.printStackTrace();
		    }
	}
	
	public void setNiveauCourant(String nom) {
		parseNiveau(nom);
	}
}
