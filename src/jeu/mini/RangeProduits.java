package jeu.mini;

import java.util.LinkedList;
import java.util.List;

import graphiques.Assets;
import jeu.machine.Machine;
import processing.core.PApplet;
import processing.core.PImage;

public class RangeProduits extends MiniJeu {
	static final int WIDTH_TAPIS_ROULANT = 200;
	static final int HEIGHT_TAPIS_ROULANT = 320;
	private List<ProduitsRanges> produits;
	private boolean reussi;
	private long timeNextProduit;
	private int nbEl;
	private PImage sheep, rail, sword;
	
	public RangeProduits(Machine machine) {
		super(machine);
		produits = new LinkedList<ProduitsRanges> ();
		this.timeNextProduit = System.currentTimeMillis();
		this.reussi = true;
		this.nbEl = 10 + (int)(Math.random()*(20 - 10));
		
		sheep = Assets.getImage("mouton");
		rail = Assets.getImage("rail");
		sword = Assets.getImage("sword");
	}
	
	@Override
	public void afficher(PApplet p) {
		p.fill(128);
		p.rect(50, 50, p.width-100, p.height-100);
		p.fill(255, 0, 0);
		p.rect(300, 200, 50, 50);
		p.fill(20, 20, 40);
		p.rect((p.width-WIDTH_TAPIS_ROULANT)/2, p.height-HEIGHT_TAPIS_ROULANT, WIDTH_TAPIS_ROULANT, HEIGHT_TAPIS_ROULANT);
		p.fill(10);
		p.rect((p.width-WIDTH_TAPIS_ROULANT)/2, p.height-HEIGHT_TAPIS_ROULANT-40, WIDTH_TAPIS_ROULANT, 40, 10, 10, 0, 0);
		
		drawArrow(p, 200, 200, 50, 0);
		
		p.arc(50, p.height/2, 100, 100, -p.PI/2, p.PI/2, p.OPEN);
		p.arc(p.width/2, 50, 100, 100, 0, p.PI, p.OPEN);
		p.arc(p.width-50, p.height/2, 100, 100, p.PI/2, 3*p.PI/2, p.OPEN);
		p.image(sheep, 50, p.height/2-15);
		p.image(rail, p.width/2-15, 50);
		p.image(sword, p.width-50-30, p.height/2-15);
//		p.fill(255, 0, 0);
//		p.rect(50, p.height/2-15, 30, 30);
//		p.fill(0, 255, 0);
//		p.rect(p.width/2-15, 50, 30, 30);
//		p.fill(0, 0, 255);
//		p.rect(p.width-50-30, p.height/2-15, 30, 30);
		
		for(ProduitsRanges produit : produits)
			produit.afficher(p);
	}
	
	@Override
	public boolean evoluer() {
		if(nbEl!=0 && this.timeNextProduit <= System.currentTimeMillis()) {
			this.timeNextProduit = System.currentTimeMillis() + (long)(300 + Math.random()*(700 - 300));
			this.produits.add(new ProduitsRanges());
			this.nbEl--;
		}
		for(ProduitsRanges produit : produits) {
			if(!produit.evoluer())
				reussi = false;
		}
		if(this.produits.get(0).getDirty()) // seul le premier peut-être dirty (plus près de la poubelle)
			this.produits.remove(0);
		return reussi && (this.produits.size()!=0 || nbEl!=0);
	}
	
	@Override
	public void keyPressed(int key) {
		if(!this.reussi || this.produits.size()==0 || (key!=PApplet.LEFT && key!=PApplet.RIGHT && key!=PApplet.UP))
			return;
		if(!goodGuess(key, this.produits.get(0))) {
			reussi = false;
			ProduitsRanges.setSpeed(-1);
		}
		this.produits.remove(0);
	}
	private static boolean goodGuess(int key, ProduitsRanges produit) {
		switch(key) {
			case PApplet.LEFT:
				return produit.getType()==0;
			case PApplet.UP:
				return produit.getType()==1;
			case PApplet.RIGHT:
				return produit.getType()==2;
			default:
				return false;
		}
	}
	private static void drawArrow(PApplet p, int cx, int cy, int len, int angle){  
//		p.strokeWeight(len/4);
//		p.stroke(p.GRAY);
//		p.line(cx, cy, (float)(cx+len*0.7), cy);
//		p.triangle((float)(cx+len*0.7), (float)cy-len/10, (float)(cx+len*0.7), (float)cy+len/7+len/10, (float)cx+len, (float)cy+len/7/2);
		p.pushMatrix();
		p.translate(cx, cy);
		p.strokeWeight(5);
		p.stroke(255,0,0);
		p.rotate(p.radians(angle));
		p.line(0,0,len, 0);
		p.line(len, 0, (float)(len*0.8), -8);
		p.line(len, 0, (float)(len*0.8), 8);
		p.popMatrix();
	}

	@Override
	public boolean estReussi() {
		return reussi;
	}
}
