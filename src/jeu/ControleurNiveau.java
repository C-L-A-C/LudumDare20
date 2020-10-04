package jeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import collision.Point;
import jeu.machine.Toleuse;
import jeu.produit.TypeProduit;

public class ControleurNiveau {
	// donnees du jeu
	private DonneesJeu donneesJeu;
	private ControleurEvenements eCtrl;

	// taille d'une case en pixels
	private int tailleCasePixels;
	// taille du niveau en nombre de cases
	private int largeur;
	private int hauteur;

	public ControleurNiveau(DonneesJeu jeu) {
		this.donneesJeu = jeu;

		tailleCasePixels = Tapis.W;

		eCtrl = new ControleurEvenements(tailleCasePixels);
		jeu.setControleurEvenements(eCtrl);
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
		boolean analyseTerrain = false;
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
					if (ligne.equals("taille")) {

						ligne = scanner.nextLine();

						ligne.replace("\n", "");
						String[] taille = ligne.split(" ", 2);

						largeur = Integer.parseInt(taille[0]);
						hauteur = Integer.parseInt(taille[1]);

						ligne = scanner.nextLine();
						if (ligne.equals("terrain")) {
							analyseTerrain = true;
						} else {
							System.out
									.println("Erreur dans la lecture du fichier des niveaux : pas de terrain specifie");
						}

					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : pas de taille specifiee");
					}
				}

				if (analyseTerrain) {
					for (int j = 0; j < hauteur; j++) {
						ligne = scanner.nextLine();
						ligne.replace("\n", "");
						System.out.println(j);
						String[] entites = ligne.split(";", largeur);
						for (int i = 0; i < largeur; i++) {
							System.out.println(entites[i]);
							String chaineLue = entites[i];
							analyseEntite(chaineLue, i, j);
						}
					}

					ligne = scanner.nextLine();
					ligne.replace("\n", "");
					// sépare le mot "gevents" et la graine du controleur d'événements
					String[] s = ligne.split(" ", 2);

					if (s[0].equals("gevents")) {
						analyseGenerateurs = true;
						eCtrl.setGraine(Integer.parseInt(s[1]));
						ligne = scanner.nextLine();
					} else {
						System.out.println("Erreur dans la lecture du fichier des niveaux : "
								+ "taille du terrain incorrecte ou pas de generateur d'evenements specifies");
					}
				}

				if (analyseGenerateurs) {

					while (scanner.hasNextLine() && !ligne.equals("fin niveau")) {
						ligne.replace("\n", "");

						// tableau contenant normalement "generator", le type du produit et la graine
						String[] s = ligne.split(" ", 3);

						if (s[0].equals("generator")) {
							GenerateurEvenements gevents = new GenerateurEvenements(this.produitCorrespondant(s[1]),
									Integer.parseInt(s[2]));

							ligne = scanner.nextLine();
							ligne.replace("\n", "");
							String[] entrees = ligne.split(";", 0);
							if (entrees[0].equals("entrees") && entrees.length > 1) {
								for (int i = 1; i < entrees.length; i++) {
									String[] coords = entrees[i].split(" ", 2);
									gevents.addEntree(new Point((float) Integer.parseInt(coords[0]) * 1.0f,
											(float) Integer.parseInt(coords[1]) * 1.0f));
								}
							} else {
								System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
										+ "des entrees des generateurs d'evenements");
							}

							ligne = scanner.nextLine();
							ligne.replace("\n", "");
							String[] sorties = ligne.split(";", 0);
							if (sorties[0].equals("sorties") && sorties.length > 1) {
								for (int i = 1; i < sorties.length; i++) {
									String[] coords = sorties[i].split(" ", 2);

									eCtrl.addSortie(
											new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
								}
							} else {
								System.out.println("Erreur dans la lecture du fichier des niveaux : mauvaise syntaxe "
										+ "des sorties des generateurs d'evenements");
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
		TypeDirectionTapis direction = TypeDirectionTapis.DROITE;
		boolean aUneDirection = true;

		switch (chaineLue.charAt(0)) {
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

		if (aUneDirection) {
			chaineLue = chaineLue.substring(1);
		}

		int x = i * this.tailleCasePixels, y = j * this.tailleCasePixels;

		switch (chaineLue) {
		case "t":
			donneesJeu.addTapis(new Tapis(x, y, direction));
			break;
		case "T":
			donneesJeu.addTapis(new TapisRapide(x, y, direction));
			break;
		case "P":
			donneesJeu.addTapis(new PontTapis(x, y, direction));
			break;
		case "Tol":
			donneesJeu.addMachine(new Toleuse(x, y));
		default:
		}
	}

	/*
	 * @return si 'false', le niveau a mal été récupéré définit quel niveau on joue
	 */
	public boolean setNiveauCourant(String nom) {
		// récupère les données du jeu
		return parseNiveau(nom);
	}
}
