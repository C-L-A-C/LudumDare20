package jeu.machine;

import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;

public class Toleuse extends Machine {
	
	public Toleuse(float x, float y)
	{
		super(x, y, new AffichageImage(Assets.getImage("default")));
	}

	@Override
	protected Recette creerRecette() {
		Recette r = new Recette(2, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.TOLE);
		return r;
	}

}