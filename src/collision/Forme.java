package collision;

import processing.core.PVector;

public abstract class Forme{
		
	public boolean collision(Forme f) {
		if (f instanceof Rectangle)
			return collision((Rectangle) f);
		else if (f instanceof Cercle)
			return collision((Cercle) f);
		else if (f instanceof Point)
			return collision((Point) f);
		else
			return false;
	}
	
	public abstract PVector getCenter();
	public abstract PVector getNearest(float x, float y);
	public abstract Forme getTranslation(PVector dir);
	public abstract float getW();
	public abstract float getH();

	//Package private
	abstract boolean collision(Rectangle r);
	abstract boolean collision(Cercle c);
	abstract boolean collision(Point p);
}
