package jeu.tapis;

import java.util.HashSet;
import java.util.Set;

import graphiques.Assets;
import jeu.produit.TypeProduit;
import processing.core.PApplet;
import processing.core.PImage;

public class Selecteur extends Tapis {
	
	protected TypeDirectionTapis directionFiltree;
	protected Set<TypeProduit> produitsFiltres;

	public Selecteur(float x, float y, TypeDirectionTapis directionFiltree, TypeDirectionTapis directionAutre) {
		super(x, y, directionAutre);
		this.directionFiltree = directionFiltree;
		this.produitsFiltres = new HashSet<>();
	}
	

	public TypeDirectionTapis getDirectionFor(TypeProduit type) {
		return produitsFiltres.contains(type) ? directionFiltree : getDirection();
	}
	
	@Override
	public void afficher(PApplet p)
	{
		PImage img = Assets.getImage("selecteur");
		int imageW = (int) (W * 1.4), imageH = (int) (H * 1.4);
		
		p.pushMatrix();
		p.translate(getX() + getForme().getW() / 2, getY() + getForme().getH() / 2);
		
		p.rotate(PApplet.radians(270 - direction.ordinal() * 90));
		
		p.image(img, - imageW / 2, - imageH / 2 +  5, imageW, imageH);
		p.popMatrix();
	}


	public void ajouterProduitFiltre(TypeProduit type) {
		produitsFiltres.add(type);
	}


}
