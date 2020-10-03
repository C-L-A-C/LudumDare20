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
		this(x, y, new AffichageImage(getImage(type)));
		this.type = type;
	}

	protected Produit(float x, float y, Apparence a) {
		super(x, y, a);
		forme = new Rectangle(pos, 30, 30);
		this.setLayer(1);
	}


	private static PImage getImage(TypeProduit type) {
		switch(type)
		{
			default:
				return Assets.getImage("default");
		}
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
	
	
	@Override
	public void afficher(PApplet p) {
		if (estDetruit())
			return; 
		p.fill(0, 255, 0);
		p.rect(pos.x, pos.y, 30, 30);
	}

}
