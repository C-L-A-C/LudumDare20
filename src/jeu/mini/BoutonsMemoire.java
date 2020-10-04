package jeu.mini;

import java.util.LinkedList;
import java.util.List;


import graphiques.Assets;
import gui.SceneHandler;
import jeu.machine.Machine;
import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class BoutonsMemoire extends MiniJeu {
	static int nbFoisMinijeu;
	private boolean reussi;
	private boolean continuEvoluer;
	private String[] memoire;
	private PImage charbon, couronne, poison, viande;
	
	private int minijeuX;
	private int minijeuY;
	private int minijeuW;
	private int minijeuH;
	private int tailleBouton;
	private int espaceBoutons;
	private int xDebut;
	private int yDebut;
	
	private int couleurCharbon;
	private int couleurCouronne;
	private int couleurPoison;
	private int couleurViande;
	
	private boolean memorise;
	private boolean estInterMemo;
	private long tempsClickMemo;
	private long interTempsClickMemo;
	private long tempsPrecedent;
	
	private String boutonSelectionne;
	
	private int indiceCourant;
	private int indiceMax;
	
	public BoutonsMemoire(Machine machine) {
		super(machine);
		this.reussi = true;
		continuEvoluer = true;
		
		SceneHandler.preloadSound("assets/sounds/clic_souris.mp3");
		indiceMax = 4+ Math.min(2, nbFoisMinijeu);
		indiceCourant = 0;
		nbFoisMinijeu++;
		
		charbon = Assets.getImage("charbon");
		couronne = Assets.getImage("Couronne");
		poison = Assets.getImage("poison");
		viande = Assets.getImage("viande");
		
		couleurCharbon = 255;
		couleurCouronne = 255;
		couleurPoison = 255;
		couleurViande = 255;
		
		creerMemoire();
		
		tempsClickMemo = 800;
		interTempsClickMemo = 400;
		memorise = true;
		estInterMemo = true;
		tempsPrecedent = System.currentTimeMillis();
	}
	
	@Override
	public void afficher(PApplet p) {
		minijeuX = 50;
		minijeuY = 50;
		// taille du mini jeu
		minijeuW = p.width-100;
		minijeuH = p.height-100;
		p.clip(minijeuX, minijeuY, minijeuW, minijeuH);
		p.fill(128);
		p.rect(minijeuX, minijeuY, minijeuW,minijeuH, 10);
		
		tailleBouton = 100;
		espaceBoutons = 40;
		
		xDebut = minijeuX + (int) (minijeuW - espaceBoutons - 2*tailleBouton)/2;
		yDebut = minijeuY + (int) (minijeuH - espaceBoutons - 2*tailleBouton)/2;
		
		// création des rectangles boutons
		p.fill(couleurCharbon, 0, 0);
		p.rect(xDebut, yDebut, tailleBouton, tailleBouton);
		p.fill(0, couleurCouronne, 0);
		p.rect(xDebut+tailleBouton+espaceBoutons, yDebut, tailleBouton, tailleBouton);
		p.fill(0, 0, couleurPoison);
		p.rect(xDebut, yDebut+tailleBouton+espaceBoutons, tailleBouton, tailleBouton);
		p.fill(couleurViande, couleurViande, 0);
		p.rect(xDebut+tailleBouton+espaceBoutons, yDebut+tailleBouton+espaceBoutons, tailleBouton, tailleBouton);
		
		
		p.image(charbon, xDebut+tailleBouton/2-15, yDebut+tailleBouton/2-15);
		p.image(couronne, xDebut+espaceBoutons+((int) tailleBouton*1.5f)-15, yDebut+tailleBouton/2-15);
		p.image(poison, xDebut+tailleBouton/2-15, yDebut+espaceBoutons+((int) tailleBouton*1.5f)-15);
		p.image(viande, xDebut+espaceBoutons+((int) tailleBouton*1.5f)-15, yDebut+espaceBoutons+((int) tailleBouton*1.5f)-15);
	}
	
	@Override
	public boolean evoluer() {
		
		if(memorise) {	// mémorisation
			long temps = System.currentTimeMillis();
			
			if(estInterMemo) {
				if(temps-tempsPrecedent > interTempsClickMemo) {
					// la pause inter memorisation est finie
					estInterMemo = false;
					tempsPrecedent = System.currentTimeMillis();
				} else {
					// pendant la pause les boutons ne sont pas appuyés
					couleurCharbon = 255;
					couleurCouronne = 255;
					couleurPoison = 255;
					couleurViande = 255;
				}
			} else {
				if(temps-tempsPrecedent > tempsClickMemo) {
					// le click auto est fini
					estInterMemo = true;
					
					// prépare le bouton suivant à mémoriser
					if(indiceCourant<indiceMax-1) {
						indiceCourant++;
					} else {
						// si on a atteint la fin, on passe à la récitation
						indiceCourant = 0;
						memorise = false;
						
						couleurCharbon = 255;
						couleurCouronne = 255;
						couleurPoison = 255;
						couleurViande = 255;
					}
					
					tempsPrecedent = System.currentTimeMillis();
				} else {
					// "appuie" sur le bouton automatiquement
					switch(memoire[indiceCourant]) {
					case "charbon":
						couleurCharbon = 128;
						break;
					case "couronne":
						couleurCouronne = 128;
						break;
					case "poison":
						couleurPoison = 128;
						break;
					case "viande":
						couleurViande = 128;
					}
				}
			}
			
		} 
		
		return continuEvoluer;
	}
	
	@Override
	public void mousePressed(int x, int y, int button) {
		if(!memorise && button == PApplet.LEFT) {
			if(x>=xDebut && y>=yDebut && x<=xDebut+2*tailleBouton+espaceBoutons 
					&& y<=yDebut+2*tailleBouton+espaceBoutons ) {
				if(x<=xDebut+tailleBouton) {
					if(y<=yDebut+tailleBouton) {
						couleurCharbon = 128;
						boutonSelectionne = "charbon";
					} else if(y>=yDebut+tailleBouton+espaceBoutons) {
						couleurPoison = 128;
						boutonSelectionne = "poison";
					}
					
				} else if(x>=xDebut+tailleBouton+espaceBoutons){
					if(y<=yDebut+tailleBouton) {
						couleurCouronne = 128;
						boutonSelectionne = "couronne";
					} else if(y>=yDebut+tailleBouton+espaceBoutons) {
						couleurViande = 128;
						boutonSelectionne = "viande";
					}
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(int x, int y, int button) {
		if(!memorise && button == PApplet.LEFT) {
			SceneHandler.playSound("assets/sounds/marteau.wav", 0.3f, 1, 0.0f, true);
			couleurCharbon = 255;
			couleurCouronne = 255;
			couleurPoison = 255;
			couleurViande = 255;
			
			String boutonRelache = "";
			
			if(x>=xDebut && y>=yDebut && x<=xDebut+2*tailleBouton+espaceBoutons 
					&& y<=yDebut+2*tailleBouton+espaceBoutons ) {
				if(x<=xDebut+tailleBouton) {
					if(y<=yDebut+tailleBouton) {
						boutonRelache = "charbon";
					} else if(y>=yDebut+tailleBouton+espaceBoutons) {
						boutonRelache = "poison";
					}
					
				} else if(x>=xDebut+tailleBouton+espaceBoutons){
					if(y<=yDebut+tailleBouton) {
						boutonRelache = "couronne";
					} else if(y>=yDebut+tailleBouton+espaceBoutons) {
						boutonRelache = "viande";
					}
				}
			}
			
			if(boutonRelache.equals(boutonSelectionne)) {
				verifieClick(boutonSelectionne);
			}
		}
	}
	
	/*
	 * vérifie si le click est correct
	 * */
	private void verifieClick(String boutonClicke) {
		if(boutonClicke.equals(memoire[indiceCourant])) {	// click correct
			if(indiceCourant<indiceMax-1) {		// il reste des indices à parcourir
				indiceCourant++;
			} else {			// tous les boutons ont été trouvés dans le bon ordre
				reussi = true;
				continuEvoluer = false;
			}
		} else { 	// click incorrect
			reussi = false;
			continuEvoluer = false;
		}
	}
	
	
	private void creerMemoire() {
		memoire = new String[indiceMax];
		for(int i=0;i<indiceMax;i++) {
			memoire[i] = Utils.random(new String[]{"charbon", "couronne","poison","viande"});
		}
	}

	@Override
	public boolean estReussi() {
		return reussi;
	}
}
