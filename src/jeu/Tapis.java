package jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import collision.Rectangle;
import graphiques.Animation;
import graphiques.Assets;
import graphiques.Tileset;
import processing.core.PApplet;
import processing.core.PImage;

public class Tapis extends Entite {
	
	public static final int W = 40, H = 40;
	
	protected TypeDirectionTapis direction;
	protected static List<Animation> animations;
	protected float vitesse;
	
	public Tapis(float x, float y, TypeDirectionTapis direction) {
		super(x, y, null);
		
		initAnimations();
		vitesse = 40;
	
		this.forme = new Rectangle(pos, W, H);
		this.direction = direction;
	}
	
	private static void initAnimations() {
		if (animations == null)
		{
			animations = new ArrayList<>();
			Tileset tileset = new Tileset("tapis_roulant", 5, 4);
			for (int i = 0; i < 8; i++)
			{
				Animation animation = new Animation(tileset, (i % 4) * 5, (i % 4) * 5 + 4, i < 4 ? 15 : 30);
				animation.setBegining(System.currentTimeMillis());
				animations.add(animation);
			}
		}
	}
	
	@Override
	public void afficher(PApplet p)
	{
		PImage img = animations.get(direction.ordinal()).getFrame();
		p.image(img, getX(), getY(), getForme().getW(), getForme().getH());
	}
		
	/**
	 * @return the direction
	 */
	public TypeDirectionTapis getDirection() {
		return direction;
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
		// TODO Auto-generated method stub

	}

	public float getVitesse() {
		return vitesse;
	}

}
