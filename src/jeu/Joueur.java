package jeu;

import collision.Rectangle;
import controles.Controlable;
import controles.Controle;
import graphiques.Assets;
import graphiques.Animation;
import graphiques.AnimationSet;
import graphiques.Tileset;
import gui.SceneHandler;
import jeu.machine.Machine;
import jeu.mini.TypeMiniJeu;
import processing.core.PApplet;

public class Joueur extends EntiteMobile implements Controlable {

	public final static int W = 30, H = 25;
	public boolean activeOnce;

	public Joueur(float x, float y) {
		super(x, y, new AnimationSet(new Tileset("perso", 4, 4), 6, 0));
		activeOnce = true;
		forme = new Rectangle(pos, W, H);
	}

	@Override
	public void afficher(PApplet p) {
		int wOffset = W, hOffset = H;
		apparence.afficher(p, (int) getX() - wOffset / 2, (int) getY() - hOffset, (int) getForme().getW() + wOffset,
				(int) getForme().getH() + hOffset);
	}

	@Override
	public void action(Controle c, DonneesJeu jeu) {
		float vitesseMax = 140;
		Machine m = null;

		int animation = -1;

		switch (c) {
		case DROITE_RELACHE:
		case GAUCHE_RELACHE:
			vitesse.x = 0;
			break;
		case HAUT_RELACHE:
		case BAS_RELACHE:
			vitesse.y = 0;
			break;
		case DROITE:
			vitesse.x = vitesseMax;
			animation = 2;
			break;
		case GAUCHE:
			vitesse.x = -vitesseMax;
			animation = 1;
			break;
		case HAUT:
			vitesse.y = -vitesseMax;
			animation = 3;
			break;
		case BAS:
			vitesse.y = vitesseMax;
			animation = 0;
			break;
		case ACTIVE_MACHINE:
			m = jeu.getNearestMachine(this);
			if (m != null)
				m.activer(jeu);
			break;
		case CHARGER_MACHINE:
			if(activeOnce) {
				m = jeu.getNearestMachine(this);
				if (m != null)
					m.prendreIngredient(jeu);
			}
			activeOnce = false;
			break;
		case CHARGER_MACHINE_RELACHE:
			activeOnce = true;
			break;
		default:
			break;
		}

		// TODO: rendre ça plus propre
		if (animation != -1)
			((AnimationSet) apparence).change(animation);

		vitesse.limit(vitesseMax);

	}
}
