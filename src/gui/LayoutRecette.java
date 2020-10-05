package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import graphiques.Assets;
import jeu.machine.Machine;
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
		PImage imgMachine = Assets.getImage(machine);
		float wMachine = imgMachine.width;
		float hMachine = imgMachine.height;
		
		float wCaseProd = 40, hCaseProd = 32;
		float wCaseMach = wMachine + 4;
		float hCaseMach = hMachine + 4;
		float wBord = 8;
		
		// taille du layout affiché
		float wTot = 45 + (wCaseProd+3)*(reactifs.size()+produits.size());
		float hTot = hCaseProd + hCaseMach-10;
		height = hTot;
		p.fill(100);
		p.rect(x, y, wTot, hTot);
		p.fill(160);
		
		
		float xDep = x+5;
		float yDep = y+30;

		
		for (int i = 0; i < reactifs.size(); i++)
		{
			p.rect(xDep + i * (wCaseProd+3), yDep, wCaseProd - wBord, hCaseProd);
			PImage img = Produit.getImage(reactifs.get(i));
			p.image(img, xDep + i * (wCaseProd+3) + 2, yDep + 2, wCaseProd - wBord - 4, hCaseProd - 4);
		
		}
		
		PImage imgFleche = Assets.getImage("fleche");
		p.image(imgFleche, xDep + reactifs.size() * (wCaseProd+3) + 2, yDep, wCaseProd - wBord - 4, hCaseProd - 4);
		
		p.image(imgMachine, xDep + reactifs.size() * (wCaseProd+3) + 10, yDep-25, wCaseProd - wBord - 4, hCaseProd - 4);
		
		
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
