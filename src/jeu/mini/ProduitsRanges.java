package jeu.mini;

import processing.core.PApplet;
import utils.Utils;

public class ProduitsRanges {
	private static final int HEIGHT_WINDOW = 480;
	private static final int WIDTH_WINDOW = 640;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final int SPEED = -5;
	private int x, y;
	private int type;
	
	
	ProduitsRanges() {
		this.type = Utils.random(0, 3);
		this.x = Utils.random((WIDTH_WINDOW-RangeProduits.WIDTH_TAPIS_ROULANT)/2+8, (WIDTH_WINDOW+RangeProduits.WIDTH_TAPIS_ROULANT)/2-WIDTH-8);
		this.y = HEIGHT_WINDOW;
	}
	
	public boolean evoluer() {
		y += SPEED;
		return y>=HEIGHT_WINDOW - RangeProduits.HEIGHT_TAPIS_ROULANT - (WIDTH+10);
	}
	
	public void afficher(PApplet p) {
		p.fill(this.type==0 ? 255 : 0, this.type==1 ? 255 : 0, this.type==2 ? 255 : 0);
		p.rect(x, y, WIDTH, HEIGHT);
	}
	
	public int getType() {
		return this.type;
	}
}
