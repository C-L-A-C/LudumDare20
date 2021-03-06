package collision;

import jeu.tapis.TypeDirectionTapis;
import processing.core.PVector;
import utils.Utils;

public class Rectangle extends Forme {
	
	 PVector pos;
	 float w, h;
	
	public Rectangle(PVector p, float w, float h)
	{
		pos = p;
		this.w = w;
		this.h= h;
	}
	
	// Pas de suivi de pos !
	public Rectangle(float x, float y, float w, float h) {
		this(new PVector(x, y), w, h);
	}

	@Override
	boolean collision(Rectangle r) {
		return (pos.x + w >= r.pos.x &&
				pos.x <= r.pos.x + r.w &&
				pos.y + h >= r.pos.y &&
				pos.y <= r.pos.y + r.h);
	}

	@Override
	boolean collision(Cercle c) {
		return c.collision(this);
	}

	@Override
	boolean collision(Point p) {
		return 	p.pos.x >= pos.x &&
				p.pos.x <= pos.x + w &&
				p.pos.y >= pos.y &&
				p.pos.y <= pos.y + h;
	}
	
	public String toString()
	{
		return pos.x+";"+pos.y+" "+w+";"+h;
	}

	@Override
	public PVector getCenter() {
		return PVector.add(pos, new PVector(w/2, h/2));
	}
	
	@Override
	public PVector getNearest(float x, float y) {
		PVector vec = new PVector();
		if (x >= pos.x + w)
		{
			vec.x = pos.x + w;
			vec.y = Utils.constrain(y, pos.y, pos.y + h);
		}
		else if (x <= pos.x)
		{
			vec.x = pos.x;
			vec.y = Utils.constrain(y, pos.y, pos.y + h);
		}
		else if (y <= pos.y)
		{
			vec.x = Utils.constrain(x, pos.x, pos.x + w);
			vec.y = pos.y;
		}
		else if (y >= pos.y + h)
		{
			vec.x = Utils.constrain(x, pos.x, pos.x + w);
			vec.y = pos.y + h;
		}
		else
			vec = PVector.add(pos, new PVector(w / 2, h / 2));
		
		return vec;
	}

	@Override
	public Forme getTranslation(PVector dir) {
		return new Rectangle(PVector.add(dir, pos), w, h);
	}
	
	public float getW()
	{
		return w;
	}
	
	public float getH() 
	{
		return h;
	}
	
	public float getX()
	{
		return pos.x;
	}
	
	public float getY()
	{
		return pos.y;
	}
	
	public boolean checkInclusionIn(Rectangle out) {
		return out.getX() < this.getX() && out.getY() < this.getY() && out.getX() + out.getW() > this.getX() + this.getH() && out.getY() + out.getH() > this.getY() + this.getH();
	}
	
	public boolean checkNearCenterInDirection(Rectangle obj, TypeDirectionTapis direction) {
		switch(direction) 
		{
		case HAUT:
			return obj.pos.y <= pos.y + h / 2 - obj.h / 2;
		case BAS:
			return obj.pos.y >= pos.y + h / 2 - obj.h / 2;
		case GAUCHE:
			return obj.pos.x <= pos.x + w / 2 - obj.w / 2;
		case DROITE:
			return obj.pos.x >= pos.x + w / 2 - obj.w / 2;
		}
		return false;
	}
	

	public boolean checkInclusionInNearCenter(Rectangle out) {
		//premiere version je sais que c'est pas une vraie inclusion calmez vous
		double coeff = 0.1;
		return out.getCenter().x - out.getW() * coeff < this.getCenter().x && this.getCenter().x < out.getCenter().x + out.getW() * coeff && out.getCenter().y - out.getH() * coeff < this.getCenter().y && this.getCenter().y < out.getCenter().y + out.getH() * coeff;
	}
	
	

}
