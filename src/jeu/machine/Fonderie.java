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

	
	public void afficher(PApplet p) {
		apparence.afficher(p,  (int)pos.x,  (int)pos.y - 30,  (int)(1.2 * forme.getW()), (int)(2 * forme.getH()));
	}
	
	
	protected void remplirRecettes(List<Recette> recettes) {
		Recette r = new Recette(2, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.TOLE);
		recettes.add(r);
	}

}
