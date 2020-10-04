package jeu.mini;

import graphiques.AffichageImage;
import graphiques.Assets;
import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class ProduitsRanges {
	private static final int HEIGHT_WINDOW = 480;
	private static final int WIDTH_WINDOW = 640;
	private static int DEFAULT_SPEED_Y;
	private static final int DEFAULT_SPEED_X = 0;
	private int width;
	private int height;
	private int speedx, speedy;
	private int x, y;
	private int type;
	private boolean dirty;
	private boolean paf;
	private PImage img;
	private long prevTime;
	
	
	ProduitsRanges(int factorSpeed) {
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
		this.speedy = DEFAULT_SPEED_Y*factorSpeed;
		this.speedx = DEFAULT_SPEED_X*factorSpeed;
		this.paf = false;
		this.prevTime = System.currentTimeMillis();
	}
	
	public boolean evoluer() {
		this.y += this.speedy*(System.currentTimeMillis() - this.prevTime)/18;
		this.x += this.speedx*(System.currentTimeMillis() - this.prevTime)/18;
		this.prevTime = System.currentTimeMillis();
		if(this.paf)
			return (x>0 && x<WIDTH_WINDOW && y>0);
		
		if(y>=HEIGHT_WINDOW - RangeProduits.HEIGHT_TAPIS_ROULANT - (width-10))
			return true;
		
		this.speedx /= 4;
		this.speedy /= 4;
		this.width -= 2;
		this.height -= 2;
		if(this.width==0)
			this.dirty = true;
		return false;
	}
	
	public void afficher(PApplet p) {
		p.image(this.img, this.x, this.y);
	}
	
	public int getType() {
		return this.type;
	}
	
	public void markDirty() {
		this.dirty = true;
	}
	
	public void pafed() {
		this.paf = true;
	}
	
	public boolean getDirty() {
		return this.dirty;
	}
	
	public void setSpeed(int spx, int spy) {
		this.speedx = spx;
		this.speedy = spy;
	}
	
	public void divSpeed(int div) {
		this.speedx /= div;
		this.speedy /= div;
		DEFAULT_SPEED_Y /= div;
	}
	
	public static void resetDefaultSpeed() {
		DEFAULT_SPEED_Y = -4;
	}
}
