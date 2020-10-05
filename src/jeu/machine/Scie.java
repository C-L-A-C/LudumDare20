package jeu.machine;

import java.util.List;

import graphiques.Animation;
import graphiques.Tileset;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;

public class Scie extends Machine {

	public Scie(float x, float y, TypeDirectionTapis dir) {
		super(x, y, new Animation(new Tileset("scie", 2, 1), 0, 1, 2), dir);
		
	}
	
	
	@Override
	protected void remplirRecettes(List<Recette> listeRecettes) {
		Recette r = new Recette (2, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.BOIS);
		r.ajouterProduit(TypeProduit.PLANCHE);
		listeRecettes.add(r);
	}

}
