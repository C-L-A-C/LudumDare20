package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import jeu.machine.Bruleur;
import jeu.machine.Machine;
import jeu.produit.Produit;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import menu.PButton;
import menu.PLabel;
import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class EcranTuto extends Scene {
	
	private PButton boutonRetourMenu;
	private List<LayoutRecette> layouts;
	
	public EcranTuto(PApplet p) {
		this.setup(p);
		
		float widthButton = p.width / 2;
		float heightButton = 75.0f;
		this.boutonRetourMenu = new PButton(p.width / 2,  50.0f, widthButton, heightButton, "RETURN MENU");
		
		initialiseLayoutsRecettes();
		}
	
	private void initialiseLayoutsRecettes() {
		layouts = new ArrayList<>();
		
		String nom = "bruleur";
		Bruleur bruleur = new Bruleur(0,0,TypeDirectionTapis.HAUT);
		List<Recette> recettesBruleur = new ArrayList<>();
		for(Recette recette : bruleur.getListRecettes()) {
			layouts.add(new LayoutRecette(nom, recette));
		}
	}
	
	
	@Override
	public void draw() {
		p.background(0);
		
		this.boutonRetourMenu.afficher(p);
		
		int x = 100;
		int y = 200; 
		int yEspace = 10;
		
		for (LayoutRecette layout : layouts) {
			layout.affiche(p, x, y);
			y += yEspace + layout.getHeight();
		}
	}

	@Override
	public void mousePressed() {
		if (boutonRetourMenu.contient(p.mouseX, p.mouseY)) {
			SceneHandler.setRunning(new MenuPrincipal(p));
		}
	}

}
