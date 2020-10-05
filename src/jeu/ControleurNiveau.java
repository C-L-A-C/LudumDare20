package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import collision.Point;
import jeu.machine.Bruleur;
import jeu.machine.Cuiseur;
import jeu.machine.Fonderie;
import jeu.machine.Scie;
import jeu.machine.Toleuse;
import jeu.machine.Tondeuse;
import jeu.produit.TypeProduit;
import jeu.tapis.PontTapis;
import jeu.tapis.Selecteur;
import jeu.tapis.Sortie;
import jeu.tapis.Tapis;
import jeu.tapis.TapisRapide;
import jeu.tapis.TeleporteurEntree;
import jeu.tapis.TeleporteurSortie;
import jeu.tapis.TypeDirectionTapis;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donneesJeu;
	private ControleurEvenements eCtrl;

	// taille d'une case en pixels
	private int tailleCasePixels;
	// taille du niveau en nombre de cases
	private int largeur;
	private int hauteur;
	private Map<Integer, TeleporteurEntree> tpEntrees;
	private Map<Integer, TeleporteurSortie> tpSorties;

	public ControleurNiveau(DonneesJeu jeu) {
		this.donneesJeu = jeu;

		tailleCasePixels = Tapis.W;

		eCtrl = new ControleurEvenements(tailleCasePixels);
		jeu.setControleurEvenements(eCtrl);
		
		tpEntrees = new HashMap<>();
		tpSorties = new HashMap<>();
	}

	/*
	 * récupère le niveau de nom "nom" dans le fichier des niveaux
	 */
	private boolean parseNiveau(String nom) {
		boolean caSestBienPasse = false;
		// chemin du fichier des niveaux
		String cheminNiveaux = "assets/niveaux.txt";

		// variables pour parser les différentes parties d'un niveau
		boolean parcourirFichier = true;
		boolean analyseSpawn = false;
		boolean analyseTempsNiveau = false;
		boolean analyseTerrain = false;
		boolean analyseObjectifs = false;
		boolean analyseGenerateurs = false;
		largeur = 0;
		hauteur = 0;

		try {
			File fichierNiveaux = new File(cheminNiveaux);
			Scanner scanner = new Scanner(fichierNiveaux);
			while (parcourirFichier && scanner.hasNextLine()) {
				String ligne = scanner.nextLine();

				if (ligne.equals(nom)) {
					// on sort de la boucle while après l'analyse du niveau
					parcourirFichier = false;
					ligne = scanner.nextLine();
					
					String[] taille = ligne.split(" ", 3);
					
					if (taille[0].equals("taille")) {

						largeur = Integer.parseInt(taille[1]);
						hauteur = Integer.parseInt(taille[2]);
						eCtrl.setTaille(largeur,hauteur);
						
						donneesJeu.setMapDimensions(largeur * tailleCasePixels, hauteur * tailleCasePixels);

						analyseSpawn = true;

					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : pas de taille specifiee");
					}
				}
				
				if(analyseSpawn) {
					ligne = scanner.nextLine();
					
					String[] s = ligne.split(" ", 2);
					if (s[0].equals("joueur")) {
						
						String[] coords= s[1].replace("(","").replace(")","").split(";",2);
						
						int joueur_x = tailleCasePixels*Integer.parseInt(coords[0]);
						int joueur_y = tailleCasePixels*Integer.parseInt(coords[1]);
						donneesJeu.getJoueur().setX(joueur_x);
						donneesJeu.getJoueur().setY(joueur_y);
						
						ligne = scanner.nextLine();
						if ((ligne.split(" ",2))[0].equals("temps")) {
							analyseTempsNiveau = true;
						} else {
							System.out
									.println("Erreur dans la lecture du fichier des niveaux : pas de temps de niveau specifie");
						}
						
					} else {
						System.out
								.println("Erreur dans la lecture du fichier des niveaux : pas d'apparition du joueur specifie");
					}
				}
				
				if(analyseTempsNiveau) {
					int tempsNiveau = Integer.parseInt(ligne.split(" ",2)[1]);
					donneesJeu.setTimer(tempsNiveau);
					
					ligne = scanner.nextLine();
					if (ligne.equals("terrain")) {
						analyseTerrain = true;
					} else {
						System.out
								.println("Erreur dans la lecture du fichier des niveaux : pas de terrain specifie");
					}
				}

				if (analyseTerrain) {
					for (int j = 0; j < hauteur; j++) {
						ligne = scanner.nextLine();

						String[] entites = ligne.replaceAll("[ \t]+", " ").split(" ", largeur);
						for (int i = 0; i < largeur; i++) {
							System.out.println(j + ";" + i);
							String chaineLue = entites[i];
							System.out.println(chaineLue);
							analyseEntite(chaineLue, i, j);
						}
					}

					ligne = scanner.nextLine();
					String[] s = ligne.split(" ", 0);

					if (s[0].equals("objectifs")) {
						analyseObjectifs = true;
					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : "
								+ "taille du terrain incorrecte ou pas d'objectif(s) specifie(s)");
					}
					
					if (! tpEntrees.keySet().equals(tpSorties.keySet()))
						System.err.println("Erreur des téléporteurs : ils ne font pas la paire");
				}
				
				if (analyseObjectifs) {
					String[] objectifs = ligne.split(" ", 0);
					
					
					if (objectifs[0].equals("objectifs")) {
						for (int i = 1; i < objectifs.length; i++) {
							String[] objectif = objectifs[i].replace("(","").replace(")","").split(";", 2);
							TypeProduit type = TypeProduit.getFromName(objectif[0]);
							donneesJeu.ajouterObjectif(type,
														Integer.parseInt(objectif[1]));
						}
					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
								+ "des objectifs du niveau");
					}
					
					ligne = scanner.nextLine();
					// sépare le mot "gevents" et la graine du controleur d'événements
					String[] s = ligne.split(" ", 2);

					if (s[0].equals("gevents")) {
						analyseGenerateurs = true;
						eCtrl.setGraine(Integer.parseInt(s[1]));
						ligne = scanner.nextLine();
					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : "
								+ "pas de generateur d'evenements specifies");
					}
				}

				if (analyseGenerateurs) {

					while (scanner.hasNextLine() && !ligne.split(" ",1)[0].equals("fin niveau")) {

						// tableau contenant normalement "generator", le type du produit et la graine
						String[] s = ligne.split(" ", 4);

						if (s[0].equals("generator")) {
							TypeProduit type = produitCorrespondant(s[1]);
							int spawntime = Integer.parseInt(s[2].replace("spawntime=",""));
							int offset = Integer.parseInt(s[3].replace("offset=",""));
							
							GenerateurEvenements gevents = new GenerateurEvenements(type,spawntime,offset);

							ligne = scanner.nextLine();
							String[] entrees = ligne.split(" ", 0);
							if (entrees[0].equals("entrees") && entrees.length > 1) {
								for (int i = 1; i < entrees.length; i++) {
									String[] coords = entrees[i].replace("(","").replace(")","").split(";", 2);
									gevents.addEntree(new Point((float) Integer.parseInt(coords[0]) * 1.0f,
											(float) Integer.parseInt(coords[1]) * 1.0f));
								}
							} else {
								System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
										+ "des entrees des generateurs d'evenements");
							}

							eCtrl.addGenerateurEvenements(gevents);
						} else {
							System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
									+ "des generateurs d'evenements");
						}
						ligne = scanner.nextLine();
					}

					if (ligne.equals("fin niveau")) {
						caSestBienPasse = true;
					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : syntaxe de fin de niveau incorrecte");
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
		TypeProduit type = TypeProduit.getFromName(produit);
		if (type == null) {
			type = TypeProduit.DECHET;
			System.out.println(
					"Erreur dans la lecture du fichier des niveaux : produit inconnu, mauvaise syntaxe du produit");
		}
		return type;
	}

	private void analyseEntite(String chaineLue, int i, int j) {
		TypeDirectionTapis direction = getDirectionFromChar(chaineLue.charAt(0));
		if (direction != null)
			chaineLue = chaineLue.substring(1);
		
		TypeDirectionTapis directionAuxiliaire = getDirectionFromChar(chaineLue.charAt(chaineLue.length() - 1));
		if (directionAuxiliaire != null)
			chaineLue = chaineLue.substring(0, chaineLue.length() - 1);

		int x = i * this.tailleCasePixels, y = j * this.tailleCasePixels;

		switch (chaineLue) {
		case "f":
			//floor
			break;
		case "t":
			donneesJeu.addTapis(new Tapis(x, y, direction));
			break;
		case "T":
			donneesJeu.addTapis(new TapisRapide(x, y, direction));
			break;
		case "P":
			donneesJeu.addTapis(new PontTapis(x, y, direction));
			break;
		case "S":
			donneesJeu.addSelecteurFin(x, y, directionAuxiliaire, direction);
			break;
		case "Tol":
			donneesJeu.addMachine(new Toleuse(x, y, direction));
			break;
		case "Bru":
			donneesJeu.addMachine(new Bruleur(x, y, direction));
			break;
		case "Sci":
			donneesJeu.addMachine(new Scie(x, y, direction));
			break;
		case "Cui":
			donneesJeu.addMachine(new Cuiseur(x, y, direction));
			break;
		case "Ton":
			donneesJeu.addMachine(new Tondeuse(x, y, direction));
			break;
		case "Fon":
			donneesJeu.addMachine(new Fonderie(x, y, direction));
			break;
		case "s":
			donneesJeu.addSortie(new Sortie(x, y));
			break;
		case "b":
			donneesJeu.addBloc(new Bloc(x, y));
			break;
		default:
			if (chaineLue.startsWith("TPin"))
			{
				int id = Integer.parseInt(chaineLue.substring(4));
				TeleporteurEntree e = new TeleporteurEntree(x, y, direction);
				TeleporteurSortie s = tpSorties.get(id);
				e.associerSortie(s);
				tpEntrees.put(id, e);
				donneesJeu.addTapis(e);
			}
			else if (chaineLue.startsWith("TPout"))
			{
				int id = Integer.parseInt(chaineLue.substring(5));
				TeleporteurSortie s = new TeleporteurSortie(x, y, direction);
				TeleporteurEntree e = tpEntrees.get(id);
				if (e != null)
					e.associerSortie(s);
				tpSorties.put(id, s); 
				donneesJeu.addTapis(s);
			}
			else
				System.err.println("Token inconnu : " + chaineLue);
		}
	}

	private TypeDirectionTapis getDirectionFromChar(char c) {
		switch (c) {
		case '^':
			return TypeDirectionTapis.HAUT;
		case 'v':
			return TypeDirectionTapis.BAS;
		case '<':
			return TypeDirectionTapis.GAUCHE;
		case '>':
			return TypeDirectionTapis.DROITE;
		}
		return null;
	}

	/*
	 * @return si 'false', le niveau a mal été récupéré définit quel niveau on joue
	 */
	public boolean setNiveauCourant(String nom) {
		// récupère les données du jeu
		return parseNiveau(nom);
	}
}
