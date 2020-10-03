package jeu.produit;

import graphiques.Apparence;
import jeu.EntiteMobile;

public abstract class Produit extends EntiteMobile {

	protected Produit(float x, float y, Apparence a) {
		super(x, y, a);
	}

}
