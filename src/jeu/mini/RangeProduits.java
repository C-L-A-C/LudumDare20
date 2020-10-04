package jeu.mini;

import java.util.LinkedList;
import java.util.List;


import graphiques.Assets;

import jeu.machine.Machine;
import processing.core.PApplet;
import processing.core.PImage;

public class RangeProduits extends MiniJeu {
	static int nbFoisMinijeu;
	static final int WIDTH_TAPIS_ROULANT = 200;
	static final int HEIGHT_TAPIS_ROULANT = 320;
	private List<ProduitsRanges> produits;
	private List<ProduitsRanges> pPafs; // Produits lancés (paf) dans le bon contenant (en cours d'animation)
	private boolean reussi;
	private long timeNextProduit;
	private int nbEl;
	private PImage sheep, rail, sword;
	
	public RangeProduits(Machine machine) {
		super(machine);
		ProduitsRanges.resetDefaultSpeed();
		produits = new LinkedList<ProduitsRanges> ();
		pPafs = new LinkedList<ProduitsRanges> ();
		this.timeNextProduit = System.currentTimeMillis();
		this.reussi = true;
		this.nbEl = 10 + (int)(Math.random()*(20 - 10));
		
		sheep = Assets.getImage("mouton");
		rail = Assets.getImage("rail");
		sword = Assets.getImage("sword");
		nbFoisMinijeu++;
	}
	
	@Override
	public void afficher(PApplet p) {
		p.clip(50, 50, p.width-100, p.height-100);
		p.fill(128);
		p.rect(50, 50, p.width-100, p.height-100, 10);
		p.fill(255, 0, 0);
		p.rect(300, 200, 50, 50);
		p.fill(20, 20, 40);
		p.rect((p.width-WIDTH_TAPIS_ROULANT)/2, p.height-HEIGHT_TAPIS_ROULANT, WIDTH_TAPIS_ROULANT, HEIGHT_TAPIS_ROULANT);
		p.fill(10);
		p.rect((p.width-WIDTH_TAPIS_ROULANT)/2, p.height-HEIGHT_TAPIS_ROULANT-40, WIDTH_TAPIS_ROULANT, 40, 10, 10, 0, 0);
		
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
		for(ProduitsRanges paf : pPafs)
			paf.afficher(p);
	}
	
	@Override
	public boolean evoluer() {
		if(nbEl!=0 && this.timeNextProduit <= System.currentTimeMillis()) {
			this.timeNextProduit = System.currentTimeMillis() + (long)(300 + Math.random()*(700 - 300));
			this.produits.add(new ProduitsRanges(nbFoisMinijeu/5+1));
			this.nbEl--;
		}
		for(ProduitsRanges paf : pPafs) {
			if(!paf.evoluer()) {
				paf.markDirty();
			}
		}
		if(this.pPafs.size()!=0 && this.pPafs.get(0).getDirty()) // seul le premier peut-être dirty
			this.pPafs.remove(0);
		for(ProduitsRanges produit : produits) {
			if(!produit.evoluer()) {
				reussi = false;
				for(ProduitsRanges prod : this.produits)
					prod.divSpeed(4);
				produit.markDirty();
			}
		}
		if(this.produits.size()!=0 && this.produits.get(0).getDirty()) // seul le premier peut-être dirty
			this.produits.remove(0);
		return reussi && (this.produits.size()!=0 || nbEl!=0);
	}
	
	@Override
	public void keyPressed(int key) {
		if(!this.reussi || this.produits.size()==0 || (key!=PApplet.LEFT && key!=PApplet.RIGHT && key!=PApplet.UP))
			return;
		if(!goodGuess(key, this.produits.get(0))) {
			reussi = false;
			for(ProduitsRanges produit : this.produits)
				produit.divSpeed(4);
			this.produits.remove(0);
		} else {
			this.produits.get(0).setSpeed(key==PApplet.LEFT ? -10 : (key==PApplet.RIGHT ? 10 : 0), key==PApplet.UP ? -10 : 0);
			this.produits.get(0).pafed();
			pPafs.add(produits.get(0));
			produits.remove(0);
		}
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
	public boolean estReussi() {
		return reussi;
	}
}
