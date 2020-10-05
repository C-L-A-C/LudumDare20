package jeu.machine;

import java.util.List;

import graphiques.Animation;
import graphiques.Tileset;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;

public class Cuiseur extends Machine {
	
	public Cuiseur(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new Animation(new Tileset("cuisson", 2, 1), 0, 1, 4), dir);
	}
	
	
	protected void remplirRecettes(List<Recette> recettes) {
		Recette r = new Recette(6, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.MOUTON);
		r.ajouterProduit(TypeProduit.VIANDE);
		recettes.add(r);
	}


	@Override
	public String getImageName() {
		return "cuisson";
	}

}
