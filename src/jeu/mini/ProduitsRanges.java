package jeu.mini;

import graphiques.AffichageImage;
import graphiques.Assets;
import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class ProduitsRanges {
	private static final int HEIGHT_WINDOW = 480;
	private static final int WIDTH_WINDOW = 640;
	private int width;
	private int height;
	private static int speed = -4;
	private int x, y;
	private int type;
	private boolean dirty;
	private PImage img;
	
	
	ProduitsRanges() {
		this.width = 32;
		this.height = 32;
		this.type = Utils.random(0, 3);
		this.x = Utils.random((WIDTH_WINDOW-RangeProduits.WIDTH_TAPIS_ROULANT)/2+8, (WIDTH_WINDOW+RangeProduits.WIDTH_TAPIS_ROULANT)/2-this.width-8);
		this.y = HEIGHT_WINDOW;
		switch(this.type) {
			case 0:
				this.img = Assets.getImage("mouton");
				break;
			case 1:
				this.img = Assets.getImage("rail");
				break;
			case 2:
				this.img = Assets.getImage("sword");
				break;
		}
		this.dirty = false;
	}
	
	public boolean evoluer() {
		if(y>=HEIGHT_WINDOW - RangeProduits.HEIGHT_TAPIS_ROULANT - (width-10)) {
			y += speed;
			return true;
		} else {
			speed = -1;
			this.width -= 2;
			this.height -= 2;
			if(this.width==0)
				this.dirty = true;
			return false;
		}
	}
	
	public void afficher(PApplet p) {
		AffichageImage image = new AffichageImage(img);
		image.afficher(p, x, y, this.width, this.height);
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean getDirty() {
		return this.dirty;
	}
	
	public static void setSpeed(int sp) {
		speed = sp;
	}
}
