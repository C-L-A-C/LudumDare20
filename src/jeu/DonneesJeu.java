package jeu;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import collision.Rectangle;

public class DonneesJeu {
	private Joueur joueur;
	private Scroll scroll;
	private List<Tapis> listeTapis;

	public DonneesJeu() {
		int viewW = 640, viewH = 480;
		joueur = new Joueur(0, 0);
		scroll = new Scroll(viewW, viewH, viewW, viewH);
		// test tapis
		listeTapis = new ArrayList<>();
		listeTapis.add(new Tapis(100, 100, 50, 50, TypeDirectionTapis.BAS));

	}

	public Entite checkCollision(Entite e) {
		float eW = e.getForme().getW(), eH = e.getForme().getH();

		int width = 640, height = 480;
		Rectangle rectMonde = new Rectangle(eW, eH, width - 2 * eW + 1, height - 2 * eH + 1);
		if (!e.collision(rectMonde))
			return new Mur(0, 0, 0, 0);

		if (e != joueur && e.collision(joueur))
			return joueur;

		for (Tapis t : listeTapis) {
			if (e != t && e.collision(t)) {
				return t;
			}
		}

		return null;
	}

	public void evoluer() {
		long t = System.currentTimeMillis();

		joueur.evoluer(t, this);
	}

	public void afficher(PApplet p) {
		scroll.update(joueur);
		p.noStroke();

		p.pushMatrix();
		p.translate(-(int) scroll.getX(), -(int) scroll.getY());

		joueur.afficher(p);

		for (Tapis t : listeTapis) {
			t.afficher(p);
		}

		p.popMatrix();
	}

	public Joueur getJoueur() {
		return joueur;
	}

}
