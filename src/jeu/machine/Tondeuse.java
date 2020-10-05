package jeu.machine;

import java.util.List;

import graphiques.Animation;
import graphiques.Tileset;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;

public class Tondeuse extends Machine {
	
	public Tondeuse(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new Animation(new Tileset("rase", 2, 1), 0, 1, 4), dir);
	}
	
	
	protected void remplirRecettes(List<Recette> recettes) {
		Recette r = new Recette(8, 10, TypeMiniJeu.BOUTONS_MEMOIRE);
		r.ajouterIngredient(TypeProduit.MOUTON);
		r.ajouterProduit(TypeProduit.LAINE, 2);
		recettes.add(r);
	}
	
	@Override
	public String getImageName() {
		return "rase";
	}

}
