package jeu;

import graphiques.Assets;
import graphiques.AffichageImage;
import processing.core.PApplet;

public class Horloge {
	private long millis0;
	private int nbSecondes;
	private long pauseMillis;
	private boolean paused;
	
	
	public Horloge(int nbSecondes) {
		this.nbSecondes = nbSecondes;
		millis0 = System.currentTimeMillis();
	}
	
	public void afficher(PApplet p) {
		/**
		 * Affiche une horloge en haut à droite de l'écran.
		 * L'heure va de 9AM à 5PM en this.nbSecondes secondes.
		 */
		p.clip(0, 0, p.width, p.height);
		if(!this.paused) {
			final float time =  getSeconds();
			final double speed = (4/3.)*12*Math.PI/this.nbSecondes;//(float)8/this.nbSecondes;
			AffichageImage clock = new AffichageImage(Assets.getImage("clock"));
			clock.afficher(p, p.width-clock.getWidth()-10, 10, clock.getWidth(), clock.getHeight());
			
			p.stroke(7);
			/* Affichage de l'aiguille des minutes */
			final int mx1 = p.width-clock.getWidth()/2-10;
			final int my1 = 10+clock.getHeight()/2;
			final int mx2 = mx1+(int)(Math.cos(speed*time-Math.PI/2)*clock.getWidth()/3);
			final int my2 = my1+(int)(Math.sin(speed*time-Math.PI/2)*clock.getHeight()/3);
			p.line(mx1, my1, mx2, my2);
			
			/* Affichage de l'aiguille des heures */
			final int hx1 = p.width-clock.getWidth()/2-10;
			final int hy1 = 10+clock.getHeight()/2;
			final int hx2 = hx1+(int)(Math.cos(speed*time/12+Math.PI)*clock.getWidth()/4.5);
			final int hy2 = hy1+(int)(Math.sin(speed*time/12+Math.PI)*clock.getHeight()/4.5);
			p.line(hx1, hy1, hx2, hy2);
		}
	}
	
	public float getSeconds() {
		return (float)(System.currentTimeMillis() - this.millis0)/1000;
	}
	
	public void stopClock() {
		this.pauseMillis = System.currentTimeMillis();
		this.paused = true;
	}
	
	public void restartClock() {
		this.millis0 += System.currentTimeMillis() - this.pauseMillis;
		this.paused = false;
	}
	
	public boolean isPaused() {
		return this.paused;
	}

	public boolean journeeFinie() {
		return getSeconds() > nbSecondes;
	}
}
