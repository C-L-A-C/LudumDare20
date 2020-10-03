package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donneesJeu;
	private ControleurEvenements eCtrl;
	
	private int tailleCasePixels;
	private int largeur;
	private int hauteur;
	
	public ControleurNiveau(DonneesJeu jeu) {
		this.donneesJeu = jeu;
		tailleCasePixels = 50;
	}
	
	/*
	 * récupère le niveau de nom "nom"
	 * dans le fichier des niveaux
	 * */
	private void parseNiveau(String nom) {
		// chemin du fichier des niveaux
		String cheminNiveaux = "assets/niveaux.txt";
		boolean parcourir = true;
		boolean analyseTerrain = false;
		boolean analyseGenerateurs = false;
		largeur = 0;
		hauteur = 0;
		
		try {
		      File fichierNiveaux = new File(cheminNiveaux);
		      Scanner scanner = new Scanner(fichierNiveaux);
		      while (parcourir && scanner.hasNextLine()) {
		        String ligne = scanner.nextLine();
		        
		        if(ligne.equals("niveau="+nom)) {
		        	// on sort de la boucle while
		        	parcourir = false;
		        	ligne = scanner.nextLine();
		        	
		        	if(ligne.substring(0,6).equals("width=")) {
		        		// récupère la largeur spécifiée sur le fichier
		        		String larg = "";
		        		for(int i=6;i<ligne.length();i++) {
		        			char c = ligne.charAt(i);
		        			if(c != '\n') {
		        				larg += c;
		        			}
		        		}
		        		largeur = Integer.parseInt(larg);
		        		ligne = scanner.nextLine();
		        		
		        		if(ligne.substring(0,7).equals("height=")) {
			        		// récupère la hauteur spécifiée sur le fichier
			        		String haut = "";
			        		for(int i=7;i<ligne.length();i++) {
			        			char c = ligne.charAt(i);
			        			if(c != '\n') {
			        				haut += c;
			        			}
			        		}
			        		hauteur = Integer.parseInt(haut);
			        		ligne = scanner.nextLine();
			        		
			        		// analyse de la composition du terrain
			        		if(ligne.equals("terrain")) {
			        			analyseTerrain = true;
			        		} else {
			        			System.out.println("Erreur dans la lecture du fichier des niveaux : pas de terrain specifie");
			        		}
			        		
			        	} else {
			        		System.out.println("Erreur dans la lecture du fichier des niveaux : pas de hauteur specifiee");
			        	}
		        		
		        	} else {
		        		System.out.println("Erreur dans la lecture du fichier des niveaux : pas de largeur specifiee");
		        	}
		        }
		        
		        if(analyseTerrain) {
					for(int j=0;j<hauteur;j++) {
						ligne = scanner.nextLine();
						int i = 0;
						int charPos = 0;
						char c = ligne.charAt(charPos);
						
						while(i<largeur) {
							String chaineLue = "";
							while(c != ';' && c != '\n') {
								chaineLue += c;
								charPos++;
								c = ligne.charAt(charPos);
							}
							
							charPos++;
							if(charPos != ligne.length()) {
								c = ligne.charAt(charPos);
							}
							
							// crée l'objet associé à la chaîne
							analyseChaine(chaineLue,i,j);
							i++;
						}
					}
					ligne = scanner.nextLine();
					if(ligne.equals("gevents")) {
						analyseGenerateurs = true;
					} else {
		        		System.out.println("Erreur dans la lecture du fichier des niveaux : "
		        				+ "taille du terrain incorrecte ou pas de generateur d'evenements specifie");
		        	}
				}
		        
		        if(analyseGenerateurs) {
		        	ligne = scanner.nextLine();
		        	while(scanner.hasNextLine() && !ligne.equals("fin niveau")) {
		        		ligne = scanner.nextLine();
		        	}
		        }
		        
		      }
		      scanner.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Erreur dans la lecture du fichier des niveaux");
		      e.printStackTrace();
		    }
	}
	
	private void analyseChaine(String chaineLue,int i, int j) {
		TypeDirectionTapis direction = TypeDirectionTapis.DROITE;
		boolean aUneDirection = true;
		
		switch(chaineLue.charAt(0)) {
		case '^':
			direction = TypeDirectionTapis.HAUT;
			break;
		case 'v':
			direction = TypeDirectionTapis.BAS;
			break;
		case '<':
			direction = TypeDirectionTapis.GAUCHE;
			break;
		case '>':
			break;
		default:
			aUneDirection = false;
		}
		
		if(aUneDirection) {
			chaineLue = chaineLue.substring(1);
		}
		
		switch(chaineLue) {
		case "t":
			donneesJeu.addTapis(new Tapis(
					i*this.tailleCasePixels,
					j*this.tailleCasePixels,
					this.tailleCasePixels,
					this.tailleCasePixels,
					direction));
			break;
		default:
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
