package jeu.machine;

import java.util.List;

import graphiques.Animation;
import graphiques.Tileset;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;

public class Bruleur extends Machine {
	
	public Bruleur(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new Animation(new Tileset("bruleur", 2, 1), 0, 1, 4), dir);
	}
	
	@Override
	public String getImageName()
	{
		return "bruleur";
	}

	
	@Override
	protected void remplirRecettes(List<Recette> listeRecettes) {
		Recette r = new Recette(2, 10, TypeMiniJeu.BOUTONS_MEMOIRE);
		r.ajouterIngredient(TypeProduit.BOIS);
		r.ajouterProduit(TypeProduit.CHARBON);
		listeRecettes.add(r);
		
		r = new Recette(3, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.SABLE, 2);
		r.ajouterProduit(TypeProduit.VERRE);
		listeRecettes.add(r);
	}

}
