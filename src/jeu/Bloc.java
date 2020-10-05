package jeu;

import collision.Rectangle;
import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.tapis.Tapis;

public class Bloc extends Entite {

	public Bloc(float x, float y) {
		super(x, y, new AffichageImage(Assets.getImage("bloc")));
		forme = new Rectangle(pos, Tapis.W, Tapis.H);
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {}

}
