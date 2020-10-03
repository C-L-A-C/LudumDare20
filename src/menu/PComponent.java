package menu;

import config.Config;
import collision.Point;
import collision.Rectangle;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class PComponent {

	protected float x, y, w, h;

	
	public PComponent(float x, float y, float w, float h)
	{
		this.x = x; this.y = y;
		this.w = w; this.h= h;
	}
	
	public abstract void afficher(PApplet p);
	
	public boolean contient(int x, int y)
	{
		return new Rectangle(new PVector(this.x, this.y), getWidth(), getHeight()).collision(new Point(x, y));
	}

	public float getHeight() {
		return h;
	}

	public float getWidth() {
		return w;
	}
	
	public boolean click(int x, int y)
	{
		return false;
	}
	
	public void keyStroke(char key, int keyCode)
	{
		
	}
	
	public static PComponent creerChamp(float x, float y, float w, float h, String type, String val) {
		switch(type)
		{
		case "String":
		case "Integer":
			PText t = new PText(x, y, w, h, val);
			t.setPlaceholder("<vide>");
			return t;
		case "Boolean":
			int c = 15;
			return new PCheckBox(x + w / 2 - c, y + h / 2 - c, c, c, Boolean.parseBoolean(val));
		case "Key":
			PKey k = new PKey(x, y, w, h, Config.stringToKey(val));
			k.setPlaceholder("[Appuyez sur une touche]");
			k.displayPlaceholderSelected(true);
			return k;
		}
		return null;
	}
	
}
