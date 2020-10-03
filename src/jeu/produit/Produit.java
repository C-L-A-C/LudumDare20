package jeu.produit;

import collision.Rectangle;
import graphiques.Apparence;
import jeu.DonneesJeu;
import jeu.EntiteMobile;
import jeu.Tapis;
import processing.core.PApplet;

public abstract class Produit extends EntiteMobile {

	protected Produit(float x, float y, Apparence a) {
		super(x, y, a);
		forme = new Rectangle(pos, 30, 30);
		this.setLayer(1);
	}
	
	
	
	public void testTapis(DonneesJeu donnees) {
		//teste si le produit est sur un tapis, et sette sa vitesse si oui
		for (Tapis t : donnees.getListeTapis()) {
			if (((Rectangle)this.getForme()).checkInclusionIn((Rectangle)t.getForme())) {
				System.out.println("tic");
				vitesse.x = 0;
				vitesse.y = 0;
				switch(t.getDirection()) {
				case HAUT:
					vitesse.y = -10;
					return;
				case BAS:
					vitesse.y = 10;
					return;
				case GAUCHE:
					vitesse.x = -10;
					return;
				case DROITE:
					vitesse.x = 10;
					return;
				}
			}
		}

	}
	
	
	@Override
	public void afficher(PApplet p) {
		if (estDetruit())
			return; 
		p.fill(0, 255, 0);
		p.rect(pos.x, pos.y, 5, 5);
	}

}
