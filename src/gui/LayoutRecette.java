package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import graphiques.Assets;
import graphiques.Tileset;
import jeu.produit.Produit;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import processing.core.PApplet;
import processing.core.PImage;

public class LayoutRecette {
	
	private List<TypeProduit> reactifs, produits;
	private String machine;
	private float height;
	
	public LayoutRecette(String nom, Recette r) {
		reactifs = new ArrayList<>();
		for(Entry<TypeProduit, Integer> reactif:r.getIngredients()) {
			for(int i=0;i<reactif.getValue();i++) {
				reactifs.add(reactif.getKey());
			}
		}
		machine = nom;
		produits = new ArrayList<>();
		for(Entry<TypeProduit, Integer> produit:r.getProduits()) {
			for(int i=0;i<produit.getValue();i++) {
				produits.add(produit.getKey());
			}
		}
	}
	
	public void affiche(PApplet p, int x, int y) {
		p.rectMode(PApplet.CORNER);
		
		// récupère la première image de la machine
		Tileset machineTileset;
		if(machine.equals("fonderie")) {
			machineTileset = new Tileset(machine,4,1);
		} else {
			machineTileset = new Tileset(machine,2,1);
		}
		PImage imgMachine = machineTileset.get(0);
		
		
		float hMachine = imgMachine.height;
		
		float wCaseProd = 40, hCaseProd = 32;
		float hCaseMach = hMachine + 4;
		float wBord = 8;
		
		// taille du layout affiché
		float wTot = 45 + (wCaseProd+3)*(reactifs.size()+produits.size());
		float hTot = hCaseProd + hCaseMach-20;
		height = hTot;
		p.fill(100);
		p.rect(x, y, wTot, hTot);
		p.fill(160);
		
		
		float xDep = x+5;
		float yDep = y+30;

		// les réactifs/ingrédients
		for (int i = 0; i < reactifs.size(); i++)
		{
			p.rect(xDep + i * (wCaseProd+3), yDep, wCaseProd - wBord, hCaseProd);
			PImage img = Produit.getImage(reactifs.get(i));
			p.image(img, xDep + i * (wCaseProd+3) + 2, yDep + 2, wCaseProd - wBord - 4, hCaseProd - 4);
		
		}
		// la flèche
		PImage imgFleche = Assets.getImage("fleche");
		p.image(imgFleche, xDep + reactifs.size() * (wCaseProd+3) + 2, yDep, wCaseProd - wBord - 4, hCaseProd - 4);
		// la machine
		p.image(imgMachine, xDep + reactifs.size() * (wCaseProd+3), yDep-25, wCaseProd - wBord - 4, hCaseProd - 4);
		
		// les produits
		xDep = xDep + reactifs.size() * (wCaseProd+3) + 40;
		for (int i = 0; i < produits.size(); i++)
		{
			p.rect(xDep + i * (wCaseProd+3), yDep, wCaseProd - wBord, hCaseProd);
			PImage img = Produit.getImage(produits.get(i));
			p.image(img, xDep + i * (wCaseProd+3) + 2, yDep + 2, wCaseProd - wBord - 4, hCaseProd - 4);
		
		}
	}
	
	public float getHeight() {
		return height;
	}

}
