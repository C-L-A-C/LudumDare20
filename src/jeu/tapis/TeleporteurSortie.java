package jeu.tapis;

import graphiques.Assets;
import jeu.produit.Produit;
import processing.core.PApplet;
import processing.core.PVector;

public class TeleporteurSortie extends Tapis {

	public TeleporteurSortie(float x, float y, TypeDirectionTapis direction) {
		super(x, y, direction);
	}

	public void teleporter(Produit p) {
		p.setTaille(Produit.W, Produit.H);
		p.setX(getX() + getForme().getW() / 2 - Produit.W / 2);
		p.setY(getY() + getForme().getH() / 2 - Produit.H / 2);
		p.setVitesse(new PVector());
	}
	
	@Override
	public void afficher(PApplet p)
	{
		int imageW = W + 5, imageH = H;
		
		p.pushMatrix();
		p.translate(getX() + getForme().getW() / 2, getY() + getForme().getH() / 2);
		
		p.rotate(PApplet.radians(90 - direction.ordinal() * 90));
		
		p.image(Assets.getImage("entree"), - imageW / 2, - imageH / 2, imageW, imageH);
		p.popMatrix();
	}
	
	//Big magouille : on renvoie la mauvaise layer pour l'affichage mais dans les tests de collision on prend l'attribut layer direct
	@Override
	public int getLayer()
	{
		return 1;
	}
}
