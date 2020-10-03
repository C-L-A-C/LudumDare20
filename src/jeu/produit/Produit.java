package jeu.produit;

import collision.Rectangle;
import graphiques.Apparence;
import jeu.DonneesJeu;
import jeu.EntiteMobile;
import jeu.Tapis;
import processing.core.PApplet;
import processing.core.PImage;
import graphiques.Assets;
import graphiques.AffichageImage;

public class Produit extends EntiteMobile {
	
	private TypeProduit type;
	
	public Produit(float x, float y, TypeProduit type)
	{
		super(x, y, new AffichageImage(getImage(type)));
		forme = new Rectangle(pos, 20, 20);
		this.setLayer(1);
		this.type = type;
	}


	private static PImage getImage(TypeProduit type) {
		String name = type.getSpriteName();
		if (name == null)
			name = "default";
		return Assets.getImage(name);
	}
	
	public TypeProduit getType()
	{
		return type;
	}
	
	
	
	public void testTapis(DonneesJeu donnees) {
		//teste si le produit est sur un tapis, et sette sa vitesse si oui
		boolean pleaseStop = true;
		for (Tapis t : donnees.getListeTapis()) {
			if (this.collision(t.getForme())) {
				pleaseStop = false;
			}
			if (((Rectangle)this.getForme()).checkInclusionInNearCenter((Rectangle)t.getForme())) {
				switch(t.getDirection()) {
				case HAUT:
					vitesse.y = -50;
					vitesse.x =  0;
					break;
				case BAS:
					vitesse.y = 50;
					vitesse.x = 0;
					break;
				case GAUCHE:
					vitesse.x = -50;
					vitesse.y = 0;
					break;
				case DROITE:
					vitesse.x = 50;
					vitesse.y = 0;
					break;
				}
			}
		}
		if (pleaseStop) {
			vitesse.x = 0;
			vitesse.y = 0;
		}

	}


}
