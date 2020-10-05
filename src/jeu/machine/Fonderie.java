package jeu.machine;

import java.util.List;

import graphiques.AffichageImage;
import graphiques.Animation;
import graphiques.Assets;
import graphiques.Tileset;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;

public class Fonderie extends Machine {
	
	public Fonderie(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new Animation(new Tileset("fonderie", 4, 1), 0, 1, 4), dir);
	}

	
	protected void remplirRecettes(List<Recette> recettes) {
		Recette r = new Recette(2, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.METAL_FUSION);
		recettes.add(r);
		
		r = new Recette(5, 10, TypeMiniJeu.BOUTONS_MEMOIRE);
		r.ajouterIngredient(TypeProduit.OR_BRUT, 2);
		r.ajouterProduit(TypeProduit.BIJOU);
		recettes.add(r);
		
		r = new Recette(5, 10, TypeMiniJeu.BUZZER);
		r.ajouterIngredient(TypeProduit.PLASTIQUE);
		r.ajouterIngredient(TypeProduit.TOLE);
		r.ajouterProduit(TypeProduit.COUTEAU);
		recettes.add(r);
	}
	
	@Override
	public String getImageName() {
		return "fonderie";
	}

}
