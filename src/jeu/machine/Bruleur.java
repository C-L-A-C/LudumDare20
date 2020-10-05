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

	
	public void afficher(PApplet p) {
		apparence.afficher(p,  (int)pos.x,  (int)pos.y - 30,  (int)(1.2 * forme.getW()), (int)(2 * forme.getH()));
	}
	
	
	@Override
	protected void remplirRecettes(List<Recette> listeRecettes) {
		Recette r = new Recette(2, 10, TypeMiniJeu.VISSE_VIS);
		r.ajouterIngredient(TypeProduit.BOIS);
		r.ajouterProduit(TypeProduit.CHARBON);
		listeRecettes.add(r);
	}

}
