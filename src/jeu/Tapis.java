package jeu;

import java.util.HashMap;
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
	protected static Map<TypeDirectionTapis, Animation> animations;
	
	public Tapis(float x, float y, TypeDirectionTapis direction) {
		super(x, y, null);
		
		initAnimations();
	
		this.forme = new Rectangle(pos, W, H);
		this.direction = direction;
	}
	
	private static void initAnimations() {
		if (animations == null)
		{
			animations = new HashMap<>();
			Tileset tileset = new Tileset("tapis_roulant", 5, 4);
			for (int i = 0; i < 4; i++)
			{
				Animation animation = new Animation(tileset, i * 5, i * 5 + 4, 15);
				animation.setBegining(System.currentTimeMillis());
				animations.put(TypeDirectionTapis.values()[i], animation);
			}
		}
	}
	
	@Override
	public void afficher(PApplet p)
	{
		PImage img = animations.get(direction).getFrame();
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

}
