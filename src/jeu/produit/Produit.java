package jeu.produit;

import java.util.List;

import collision.Rectangle;
import graphiques.Apparence;
import jeu.DonneesJeu;
import jeu.EntiteMobile;
import jeu.tapis.PontTapis;
import jeu.tapis.Tapis;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;
import processing.core.PImage;
import graphiques.Assets;
import graphiques.AffichageImage;

public class Produit extends EntiteMobile {

	public static final float W = 26, H = 26;
	private TypeProduit type;
	private TypeDirectionTapis lastDirection;

	public Produit(float x, float y, TypeProduit type) {
		super(x, y, new AffichageImage(getImage(type)));
		forme = new Rectangle(pos, W, H);
		this.setLayer(1);
		this.type = type;
		lastDirection = null;
	}

	public static PImage getImage(TypeProduit type) {
		String name = type.getSpriteName();
		if (name == null)
			name = "default";
		return Assets.getImage(name);
	}

	public TypeProduit getType() {
		return type;
	}

	public void adhererTapis(List<Tapis> listeTapis) {

		boolean shouldStop = true;

		// teste si le produit est sur un tapis, et sette sa vitesse si oui
		for (Tapis t : listeTapis) {


			//if (t instanceof PontTapis) System.out.println(getForme() + " <=> " + t.getForme());
			boolean enCollision = this.getForme().collision(t.getForme());

			if (enCollision)
				shouldStop = false;
			else 
				continue;
			
			TypeDirectionTapis directionTapis = t.getDirectionFor(getType());
			boolean adherence = false;
			// Si on est dans la continuite
			if (directionTapis == lastDirection || lastDirection == null)
				adherence = true; // Alors si on est en collision on continue
			else if (((Rectangle) t.getForme()).checkNearCenterInDirection((Rectangle) getForme(), lastDirection))
				adherence = true; // Si on est pas dans la continuite on adhere apres un certain temps
			
			if (adherence)
			 {

				// System.out.println("Last direction : " + lastDirection + ", new : " +
				// t.getDirection());
				// System.out.println("Position : " + pos + ", tapis : " + t.getPos());
				lastDirection = directionTapis;
				float vitesseMag = t.getVitesse();
				switch (directionTapis) {
				case HAUT:
					vitesse.y = -vitesseMag;
					vitesse.x = 0;
					break;
				case BAS:
					vitesse.y = vitesseMag;
					vitesse.x = 0;
					break;
				case GAUCHE:
					vitesse.x = -vitesseMag;
					vitesse.y = 0;
					break;
				case DROITE:
					vitesse.x = vitesseMag;
					vitesse.y = 0;
					break;
				}
			}
		}

		if (shouldStop)
			vitesse.set(0, 0);

	}
	
	public void setTaille(float w, float h) {
		forme = new Rectangle(pos, w, h);
	}

}