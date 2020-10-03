package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donneesJeu;
	private ControleurEvenements eCtrl;
	
	public ControleurNiveau(DonneesJeu jeu) {
		this.donneesJeu = jeu;
	}
	
	/*
	 * récupère le niveau de nom "nom"
	 * dans le fichier des niveaux
	 * */
	private void parseNiveau(String nom) {
		// TODO chemin du fichier des niveaux
		String cheminNiveaux = "";
		
		try {
		      File fichierNiveaux = new File(cheminNiveaux);
		      Scanner scanner = new Scanner(fichierNiveaux);
		      while (scanner.hasNextLine()) {
		        String ligne = scanner.nextLine();
		        
		        if(ligne == "niveau="+nom+"\n" ) {
		        	// TODO faire l'analyse syntaxique du niveau
		        	
		        	
		        }
		      }
		      scanner.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Erreur dans la lecteure du fichier des niveaux");
		      e.printStackTrace();
		    }
	}
	
	/*
	 * définit quel niveau on joue
	 * */
	public void setNiveauCourant(String nom) {
		// récupère les données du jeu
		parseNiveau(nom);
	}
}
