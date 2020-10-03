package jeu;

import collision.Rectangle;
import controles.Controlable;
import controles.Controle;
import graphiques.Assets;
import graphiques.Animation;
import graphiques.AnimationSet;
import graphiques.Tileset;
import jeu.machine.Machine;


public class Joueur extends EntiteMobile implements Controlable {
	
	public final static int W = 48, H = 48;	

	public Joueur(float x, float y) {
		super(x, y, new AnimationSet(new Tileset(Assets.getImage("perso"), 4, 4), 5, 0));
		forme = new Rectangle(pos, W, H);
	}
	

	@Override
	public void action(Controle c, DonneesJeu jeu) {		
		float vitesseMax = 140;
		Machine m = null;
		
		int animation = -1;
		
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
			m = jeu.getNearestMachine(this);
			if (m != null)
				m.prendreIngredient(jeu);
			break;
		default:
			break;
		}
		
		//TODO: rendre ça plus propre
		if (animation != -1)
			((AnimationSet) apparence).change(animation);
		
		vitesse.limit(vitesseMax);

	}
}
