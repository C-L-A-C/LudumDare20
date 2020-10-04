package jeu.machine;

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

	
	public void afficher(PApplet p) {
		apparence.afficher(p,  (int)pos.x,  (int)pos.y - 30,  (int)(1.2 * forme.getW()), (int)(2 * forme.getH()));
	}
	
	
	@Override
	protected Recette creerRecette() {
		Recette r = new Recette(2, 10, TypeMiniJeu.RANGE_PRODUITS);
		r.ajouterIngredient(TypeProduit.BOIS);
		r.ajouterProduit(TypeProduit.CHARBON);
		return r;
	}

}
