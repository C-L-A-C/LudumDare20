package jeu;

import graphiques.Apparence;
import processing.core.PVector;

public abstract class EntiteMobile extends Entite{
	/**
	 * Vitesse, en pixels/s, de l'élément
	 */
	protected PVector vitesse;
	
	/**
	 * Temps du dernier mouvement
	 */
	protected long tps;
	
	/**
	 * Derniere collision
	 */
	protected Entite lastCollision;
	
	
	protected EntiteMobile(float x, float y, Apparence a)
	{
		super(x, y, a);
		vitesse = new PVector(0, 0);
		
		tps = -1;
		lastCollision = null;
	}


	/**
	 * Permet de faire évoluer l'élément, en fonction du temps actuel
	 * @param t temps en millisecondes
	 * @param jeu Partie pour les collisions
	 */
	@Override
	public void evoluer(long t, DonneesJeu jeu)
	{
		if (tps == -1)
			tps = t;
		
		lastCollision = null;
		
		//duree en secondes
		float duree = (t - tps) / 1000f;
		tps = t;

		//vitesse * duree = deplacement
		PVector deplacement = PVector.mult(vitesse, duree);
		
		PVector anciennePos = pos.copy();
		pos.add(deplacement);
		

		Entite e = jeu.checkCollision(this);

		if (e != null)
		{
			faireCollision(e, jeu);
			e.faireCollision(this, jeu);
			lastCollision = e;
			pos.set(anciennePos);
		}
				
		/*float mag = vitesse.mag();
		float deplacementMag = mag * duree;
		
		int iterations = 1;
		if (deplacementMag > 1) //Si on se deplace plus de 1 pixels, on fragmente le mouvement
		{
			iterations = (int) deplacementMag;
			deplacement.div(iterations);
		}
		
		for (int i = 0; i < iterations; i++)
		{
			//On essaye le mouvement en x
			float x = pos.x;
			pos.x += deplacement.x;

			Element e = jeu.checkCollision(this);
			if (e != null)
			{
				pos.x = x;
				
				if (vitesse.x > 0)
					bloquageDroite = true; //On va a droite, on est bloque a droite
				else
					bloquageGauche = true;
	
				vitesse.x = 0;
					
				lastCollision[0] = e;
				
				deplacement.x = 0;
			}
			
			//On essaye le mouvement en y
			float y = pos.y;
			pos.y += deplacement.y;
			
			e = jeu.checkCollision(this);
			if (e != null)
			{
				pos.y = y;
				
				if (vitesse.y > 0)
					bloquageBas = true; // On va en bas, on est bloques en bas
				else
					bloquageHaut = true;
				
				vitesse.y = 0;
								
				lastCollision[1] = e;

				deplacement.y = 0;
			}
		}

		
		for (Element col : lastCollision)
		{
			if (col != null)
			{
				faireCollision(col, jeu);
				col.faireCollision(this, jeu);
			}
			
		}*/
	}
	
	@Override
	protected void faireCollision(Entite collider, DonneesJeu jeu)
	{
	}
	
	/**
	 * Permet de définir la vitesse précise de l'élément
	 * @param vit Vitesse à définir
	 */
	public void setVitesse(PVector vit)
	{
		vitesse.set(vit);
	}
	
	
	/**
	 * Permet de recuperer la vitesse de l'élément
	 * @return la vitesse
	 */
	public PVector getVitesse()
	{
		return vitesse.copy();
	}
}
