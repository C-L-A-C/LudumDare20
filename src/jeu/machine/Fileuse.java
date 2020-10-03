package jeu.machine;

import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;

public class Fileuse extends Machine {
	
	public Fileuse(float x, float y)
	{
		super(x, y, new AffichageImage(Assets.getImage("soudeuse")));
	}

	@Override
	protected Recette creerRecette() {
		Recette r = new Recette(2, 10, TypeMiniJeu.RANGE_PRODUIT);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.CABLE);
		return r;
	}

}
