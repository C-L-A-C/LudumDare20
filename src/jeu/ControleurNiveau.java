package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import collision.Point;
import jeu.produit.TypeProduit;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donneesJeu;
	private ControleurEvenements eCtrl;
	
	private int tailleCasePixels;
	private int largeur;
	private int hauteur;
	
	public ControleurNiveau(DonneesJeu jeu) {
		this.donneesJeu = jeu;
		
		eCtrl = new ControleurEvenements();
		
		tailleCasePixels = 50;
	}
	
	/*
	 * récupère le niveau de nom "nom"
	 * dans le fichier des niveaux
	 * */
	private boolean parseNiveau(String nom) {
		boolean caSestBienPasse = false;
		// chemin du fichier des niveaux
		String cheminNiveaux = "assets/niveaux.txt";
		
		//variables pour parser les différentes parties d'un niveau
		boolean parcourirFichier = true;
		boolean analyseTerrain = false;
		boolean analyseGenerateurs = false;
		largeur = 0;
		hauteur = 0;
		
		try {
		      File fichierNiveaux = new File(cheminNiveaux);
		      Scanner scanner = new Scanner(fichierNiveaux);
		      while (parcourirFichier && scanner.hasNextLine()) {
		        String ligne = scanner.nextLine();
		        
		        if(ligne.equals(nom)) {
		        	// on sort de la boucle while après l'analyse du niveau
		        	parcourirFichier = false;
		        	ligne = scanner.nextLine();
		        	if(ligne.equals("taille")) {
		        		
		        		ligne = scanner.nextLine();
		        		
		        		ligne.replace("\n", "");
		        		String[] taille = ligne.split(" ",2);
		        		
			        	largeur = Integer.parseInt(taille[0]);
			        	hauteur = Integer.parseInt(taille[1]);
			        	
			        	ligne = scanner.nextLine();
			        	if(ligne.equals("terrain")) {
			        		analyseTerrain = true;
			        	} else {
			        		System.out.println("Erreur dans la lecture du fichier des niveaux : pas de terrain specifie");
			        	}
		        		
		        	} else {
		        		System.out.println("Erreur dans la lecture du fichier des niveaux : pas de taille specifiee");
		        	}
		        }
		        
		        if(analyseTerrain) {
					for(int j=0;j<hauteur;j++) {
						ligne = scanner.nextLine();
						ligne.replace("\n", "");
						
						String[] entites = ligne.split(";", largeur);
						for(int i=0;i<largeur;i++) {
							String chaineLue = entites[i];
							analyseEntite(chaineLue,i,j);
						}
					}
					
					ligne = scanner.nextLine();
					
					if(ligne.equals("gevents")) {
						analyseGenerateurs = true;
					} else {
		        		System.out.println("Erreur dans la lecture du fichier des niveaux : "
		        				+ "taille du terrain incorrecte ou pas de generateur d'evenements specifies");
		        	}
				}
		        
		        
		        if(analyseGenerateurs) {
		        	int nbrGEvents = 0;
		        	
		        	while(scanner.hasNextLine() && !ligne.equals("fin niveau")) {
		        		ligne = scanner.nextLine();
		        		ligne.replace("\n", "");
		        		
		        		// tableau contenant normalement "generator", le type du produit et la graine
		        		String[] s = ligne.split(" ",3);
		        		
		        		if(s[0].equals("generator")) {
		        			GenerateurEvenements gevents = new GenerateurEvenements(
		        					this.produitCorrespondant(s[1]),
		        					Integer.parseInt(s[2]));
		        			
		        			ligne = scanner.nextLine();
			        		ligne.replace("\n", "");
			        		String[] entrees = ligne.split(";",0);
			        		if(entrees[0].equals("entrees") && entrees.length>1) {
			        			for(int i=1;i<entrees.length;i++) {
				        			String[] coords = entrees[i].split(" ",2);
				        			
				        			gevents.addEntree(new Point(Integer.parseInt(coords[0]),
				        				Integer.parseInt(coords[1])));
			        			}
			        		} else {
			        			System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
			        			 		+ "des entrees des generateurs d'evenements");
			        		}
			        		
			        		ligne = scanner.nextLine();
			        		ligne.replace("\n", "");
			        		String[] sorties = ligne.split(";",0);
			        		if(sorties[0].equals("sorties") && sorties.length>1) {
			        			for(int i=1;i<sorties.length;i++) {
			        				String[] coords = sorties[i].split(" ",2);
			        				System.out.println("Sortie : ("+coords[0]+";"+coords[1]+")");
			        				
			        				eCtrl.addSortie(new Point(Integer.parseInt(coords[0]),
			        						Integer.parseInt(coords[1])));
			        			}
			        		} else {
			        			System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
			        			 		+ "des sorties des generateurs d'evenements");
			        		}
		        		} else {
		        			 System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
		        			 		+ "des generateurs d'evenements");
		        		}
		        			
		        		nbrGEvents++;
		        	}
		        	
		        	if(ligne.equals("fin niveau")) {
		        		caSestBienPasse = true;
		        	}
		        }
		        
		      }
		      scanner.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Erreur dans la lecture du fichier des niveaux");
		      e.printStackTrace();
		    }
		
		return caSestBienPasse;
	}
	
	private TypeProduit produitCorrespondant(String produit) {
		switch(produit) {
			case "METAL":
				return TypeProduit.METAL;
			default:
		}
		System.out.println("Erreur dans la lecture du fichier des niveaux : produit inconnu, mauvaise syntaxe du produit");
		return TypeProduit.METAL;
	}
	
	private void analyseEntite(String chaineLue,int i, int j) {
		TypeDirectionTapis direction = TypeDirectionTapis.DROITE;
		boolean aUneDirection = true;
		System.out.println("chaine lue : " +chaineLue);
		
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
	 * @return si 'false', le niveau a mal été récupéré
	 * définit quel niveau on joue
	 * */
	public boolean setNiveauCourant(String nom) {
		// récupère les données du jeu
		return parseNiveau(nom);
	}
}
