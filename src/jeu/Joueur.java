package jeu;

import java.util.HashMap;
import java.util.Map;

import collision.Rectangle;
import controles.Controlable;
import controles.Controle;
import graphiques.Assets;
import graphiques.Animation;
import graphiques.Tileset;
import jeu.machine.Machine;


public class Joueur extends EntiteMobile implements Controlable {
	
	public final static int W = 32, H = 32;	

	public Joueur(float x, float y) {
		super(x, y, new Animation(new Tileset(Assets.getImage("spritesheet1"), 12, 8), 0, 2, 3));
		forme = new Rectangle(pos, W, H);
	}
	

	@Override
	public void action(Controle c, DonneesJeu jeu) {		
		float vitesseMax = 140;
		Machine m = null;
		
		switch(c)
		{
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
			break;
		case GAUCHE:
			vitesse.x = -vitesseMax;
			break;
		case HAUT:
			vitesse.y = -vitesseMax;
			break;
		case BAS:
			vitesse.y = vitesseMax;
			break;
		case ACTIVE_MACHINE:
			m = jeu.getNearestMachine(this);
			if (m != null)
				m.activer(jeu);
			break;
		case CHARGER_MACHINE:
			m = jeu.getNearestMachine(this);
			if (m != null)
				m.prendreIngredient(jeu);
				
			break;
		default:
			break;
		}
		
		vitesse.limit(vitesseMax);

	}
}
