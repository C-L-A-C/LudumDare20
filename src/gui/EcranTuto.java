package gui;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import config.ConfigKey;
import controles.Controle;
import controles.ControleurClavier;
import graphiques.Assets;
import jeu.Joueur;
import jeu.Scroll;
import jeu.machine.Bruleur;
import jeu.machine.Cuiseur;
import jeu.machine.Fonderie;
import jeu.machine.Scie;
import jeu.machine.Toleuse;
import jeu.machine.Tondeuse;
import jeu.produit.Recette;
import jeu.tapis.TypeDirectionTapis;
import menu.PButton;
import processing.core.PApplet;
import processing.core.PImage;

public class EcranTuto extends Scene {
	
	private PButton boutonRetourMenu;
	private List<LayoutRecette> layouts;
	private Scroll scroll;
	private ControleurClavier controleur;
	private float yScroll;
	
	public EcranTuto(PApplet p) {
		int viewW = 640, viewH = 400;

		scroll = new Scroll(viewW, viewH*3, viewW, viewH);
		this.setup(p);
		
		float widthButton = p.width / 2;
		float heightButton = 75.0f;
		this.boutonRetourMenu = new PButton(p.width / 2,  50.0f, widthButton, heightButton, "RETURN MENU");
		
		initialiseLayoutsRecettes();
		yScroll = 0;
		}
	
	private void initialiseLayoutsRecettes() {
		layouts = new ArrayList<>();
		
		String nom = "bruleur";
		Bruleur bruleur = new Bruleur(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : bruleur.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
		
		nom = "cuisson";
		Cuiseur cuiseur = new Cuiseur(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : cuiseur.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
		
		nom = "fonderie";
		Fonderie fonderie = new Fonderie(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : fonderie.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
		
		nom = "scie";
		Scie scie = new Scie(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : scie.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
		
		nom = "toleuse";
		Toleuse toleuse = new Toleuse(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : toleuse.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
		
		nom = "rase";
		Tondeuse tondeuse = new Tondeuse(0,0,TypeDirectionTapis.HAUT);
		for(Recette recette : tondeuse.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
	}
	
	@Override
	public void keyPressed() {
		
		if(p.keyCode == PApplet.UP) {
			yScroll += 10;
		} else if(p.keyCode == PApplet.DOWN) {
			yScroll -= 10;
		}
		
	}
	
	
	@Override
	public void draw() {
		p.pushMatrix();
		p.translate(0, yScroll);
		p.background(0);
		
		this.boutonRetourMenu.afficher(p);
		
		p.textSize(15);
		p.text("Go in front of a machine, use it and make products to win !", 300, 140);
		p.text("Controls : use the direction arrows to move on the map", 300, 160);
		p.text("C : charge a machine whith the right product (see the recipes below)", 320, 180);
		p.text("K : show what is charged in the machine", 260, 200);
		p.text("<space bar> : once charged, turn the machine on and lauch a mini-game", 320, 220);
		
		PImage imgPont = Assets.getImage("pont");
		p.image(imgPont, 40, 250);
		p.text("this is a bridge, it allows", 200, 250);
		p.text("you to cross conveyor belts", 200, 270);
		
		
		int x1 = 75;
		int x2 = p.width/2;
		int x = x1;
		boolean aDroite = false;
		int y = 320; 
		int yEspace = 10;
		
		for (LayoutRecette layout : layouts) {
			if(aDroite) {
				x = x2;
				aDroite = false;
			} else {
				x = x1;
				aDroite = true;
			}
			layout.affiche(p, x, y);
			if(!aDroite) {
				y += yEspace + layout.getHeight();
			}
		}
		
		p.popMatrix();
	}

	@Override
	public void mousePressed() {
		if (boutonRetourMenu.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new MenuPrincipal(p));
		}
	}

}
