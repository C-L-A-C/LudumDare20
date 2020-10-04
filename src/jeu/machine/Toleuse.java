package jeu.machine;

import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.TypeDirectionTapis;

public class Toleuse extends Machine {
	
	public Toleuse(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new AffichageImage(Assets.getImage("default")), dir);
	}

	@Override
	protected Recette creerRecette() {
		Recette r = new Recette(2, 10, TypeMiniJeu.BOUTONS_MEMOIRE);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.TOLE);
		return r;
	}

}
