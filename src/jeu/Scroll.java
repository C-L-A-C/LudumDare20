package jeu;

import collision.Rectangle;
import processing.core.PVector;

public class Scroll {
	
	private PVector pos;
	private float w, h, wE, hE;
	private float totalW, totalH;
	private Rectangle forme;
	
	public Scroll(float w, float h, float viewW, float viewH)
	{
		pos = new PVector();
		setW(viewW);
		setH(viewH);
		totalW = w;
		totalH = h;
		
		wE = Joueur.W;
		hE = Joueur.H;
	}
	
	public void setW(float w)
	{
		this.w = w;
		forme = new Rectangle(pos, w, h);
	}
	
	public void setH(float h)
	{
		this.h = h;
		forme = new Rectangle(pos, w, h);
	}

	public void update(Entite followed) {
		if (followed != null)
		{
			float eX = followed.getX(), eY = followed.getY();
			setX(eX - w / 2);
			setY(eY - h / 2);
		}
	}
	

	public void setX(float x) {
		pos.x = x < 0 ? 0 : (x > totalW - w?  totalW - w : x);
	}

	public void setY(float y) {
		pos.y = y < 0 ? 0 : (y > totalH - h?  totalH - h : y);
	}
	
	public Rectangle getBB() {
		return forme;
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}

	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}

	public void setTotalW(float w) {
		if (w >= this.w)
			totalW = w;
	}

	public void setTotalH(float h) {
		if (h >= this.h)
			totalH = h;
		
	}
}
