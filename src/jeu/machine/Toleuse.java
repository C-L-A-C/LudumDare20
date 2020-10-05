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

public class Toleuse extends Machine {
	
	public Toleuse(float x, float y, TypeDirectionTapis dir)
	{
		super(x, y, new Animation(new Tileset("aplatisseur", 2, 1), 0, 1, 4), dir);
	}

	
	protected void remplirRecettes(List<Recette> recettes) {
		Recette r = new Recette(2, 10, TypeMiniJeu.VISSE_VIS);
		r.ajouterIngredient(TypeProduit.METAL);
		r.ajouterProduit(TypeProduit.TOLE);
		recettes.add(r);
	}
	
	@Override
	public String getImageName() {
		return "aplatisseur";
	}

}
