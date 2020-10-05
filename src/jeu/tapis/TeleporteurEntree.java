package jeu.tapis;

import java.util.ArrayList;
import java.util.List;

import collision.Rectangle;
import graphiques.AffichageImage;
import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.produit.Produit;
import processing.core.PApplet;

public class TeleporteurEntree extends Tapis {

	private long derniereReduction;
	private boolean updateTempsReduction;
	private TeleporteurSortie sortie;
	private Rectangle trou;
	
	public TeleporteurEntree(float x, float y, TypeDirectionTapis dir) {
		super(x, y, dir);
		derniereReduction = 0;
		updateTempsReduction = false;
		forme = new Rectangle(pos, Tapis.W, Tapis.H);
		trou = new Rectangle(pos.x + 13, pos.y + 13, Tapis.W - 26, Tapis.H - 26);
		vitesse = 50;
	}
	

	public void afficher(PApplet p)
	{
		p.image(Assets.getImage("sortie"), getX(), getY(), getForme().getW(), getForme().getH());
	}
	
	public void associerSortie(TeleporteurSortie sortie)
	{
		this.sortie = sortie;
	}

	@Override
	public void evoluer(long t, DonneesJeu d) {
		for(Produit p : d.getListeProduits()) {
			if (p.collision(trou) && t-derniereReduction>10) {
				updateTempsReduction = true;
				float w = p.getForme().getW() -2;
				float h = p.getForme().getH() -2;
				p.setX(p.getX() + 1);
				p.setY(p.getY() + 1);
				if (w<=2 || h<=2) {
					sortie.teleporter(d, p);
				} else {
					p.setTaille(w, h);
				}
			}
		}
		if( updateTempsReduction) {
			updateTempsReduction = false;
			derniereReduction = t;
		}
	}
	
	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}
}
