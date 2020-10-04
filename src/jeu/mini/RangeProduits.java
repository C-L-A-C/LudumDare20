package jeu.mini;

import java.util.LinkedList;
import java.util.List;
import jeu.machine.Machine;
import processing.core.PApplet;

public class RangeProduits extends MiniJeu {
	static final int WIDTH_TAPIS_ROULANT = 200;
	static final int HEIGHT_TAPIS_ROULANT = 320;
	private List<ProduitsRanges> produits;
	private boolean reussi;
	private long timeNextProduit;
	private int nbEl;
	
	public RangeProduits(Machine machine) {
		super(machine);
		produits = new LinkedList<ProduitsRanges> ();
		this.timeNextProduit = System.currentTimeMillis();
		this.reussi = true;
		this.nbEl = 10 + (int)(Math.random()*(20 - 10));		
	}
	
	@Override
	public void afficher(PApplet p) {
		p.fill(128);
		p.rect(50, 50, p.width-100, p.height-100);
		p.fill(255, 0, 0);
		p.rect(300, 200, 50, 50);
		p.fill(20, 20, 40);
		p.rect((p.width-WIDTH_TAPIS_ROULANT)/2, p.height-HEIGHT_TAPIS_ROULANT, WIDTH_TAPIS_ROULANT, HEIGHT_TAPIS_ROULANT);
		p.fill(255, 0, 0);
		p.rect(50, p.height/2-15, 30, 30);
		p.fill(0, 255, 0);
		p.rect(p.width/2-15, 50, 30, 30);
		p.fill(0, 0, 255);
		p.rect(p.width-50-30, p.height/2-15, 30, 30);
		
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
		System.out.println(reussi);
		System.out.println(this.produits.size());
		return reussi && (this.produits.size()!=0 || nbEl!=0);
	}
	
	@Override
	public void keyPressed(int key) {
		if(this.produits.size()==0 || (key!=PApplet.LEFT && key!=PApplet.RIGHT && key!=PApplet.UP))
			return;
		if(!goodGuess(key, this.produits.get(0)))
			reussi = false;
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
	
	@Override
	public void keyReleased(int key) {
		
	}

	@Override
	public boolean estReussi() {
		return reussi;
	}
}
