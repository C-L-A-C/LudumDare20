package jeu;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collision.Rectangle;
import controles.ControleurClavier;
import graphiques.AffichageRectangle;
import jeu.machine.Machine;
import jeu.machine.Toleuse;
import jeu.mini.MiniJeu;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;

public class DonneesJeu {
	private static final float MAX_DISTANCE_MACHINE = 50;

	private int largeurNiveauPixels;
	private int hauteurNiveauPixels;
	private long failedMinijeut0;
	private boolean failedMinijeu;
	private long tempsDernierProduitCree;
	
	private Joueur joueur;
	private Scroll scroll;
	private List<Tapis> listeTapis;
	private List<Produit> listeProduits;
	private ControleurEvenements eCtrl;
	private List<Machine> listeMachines;

	private MiniJeu miniJeuCourant;

	public DonneesJeu() {
		int viewW = 640, viewH = 480;
		largeurNiveauPixels = viewW;
		hauteurNiveauPixels = viewH;
		tempsDernierProduitCree = 0;
		
		joueur = new Joueur(0, 0);
		scroll = new Scroll(viewW*2, viewH*2, viewW, viewH);
		listeTapis = new ArrayList<>();
		listeProduits = new ArrayList<>();
		
		this.failedMinijeu = false;
		listeMachines = new ArrayList<>();
		
		miniJeuCourant = null;

	}

	public Entite checkCollision(Entite e) {
		float eW = e.getForme().getW(), eH = e.getForme().getH();

		int width = largeurNiveauPixels, height = hauteurNiveauPixels;
		Rectangle rectMonde = new Rectangle(eW, eH, width - 2 * eW + 1, height - 2 * eH + 1);
		if (e == joueur && !e.collision(rectMonde))
			return new Mur(0, 0, 0, 0);

		if (e != joueur && e.collision(joueur))
			return joueur;

		for (Tapis t : listeTapis) {
			if (e != t && e.collision(t)) {
				return t;
			}
		}
		
		for (Machine m : listeMachines) {
			if (e != m && e.collision(m)) {
				return m;
			}
		}

		return null;
	}

	public void evoluer(long t) {
		joueur.evoluer(t, this);
		// changement des vitesses des produits
		for (Produit p : listeProduits) {
			p.testTapis(this);
			p.evoluer(t, this);
		}

		if (estEnMiniJeu()) {
			if (!miniJeuCourant.evoluer()) {
				if(!miniJeuCourant.estReussi() && !failedMinijeu) {
					failedMinijeut0 = System.currentTimeMillis();
					failedMinijeu = true;
				}
			}
		}
		
		if(t-tempsDernierProduitCree>5000) {
			Produit nouveauProduit = eCtrl.creerNouveauProduit();
			if(nouveauProduit != null) {
				listeProduits.add(nouveauProduit);
				
			}
			
			tempsDernierProduitCree = t;
		}
	}

	public void afficher(PApplet p) {

		scroll.update(joueur);
		p.noStroke();

		p.pushMatrix();
		p.translate(-(int) scroll.getX(), -(int) scroll.getY());

		for (Tapis t : listeTapis) {
			t.afficher(p);
		}
		
		for (Produit prod: listeProduits) {
			prod.afficher(p);
		}

		for (Machine machine: listeMachines) {
			machine.afficher(p);
		}

		joueur.afficher(p);

		p.popMatrix();

		if (estEnMiniJeu())
			miniJeuCourant.afficher(p);
		
		if(failedMinijeu) {
			if(this.failedMinijeut0 + 1000 < System.currentTimeMillis()) {
				failedMinijeu = false;
				miniJeuCourant.getMachine().finirActivation(miniJeuCourant.estReussi());
				miniJeuCourant = null;
			} else {
				p.fill(255, 0, 0, Math.min((float)(System.currentTimeMillis() - this.failedMinijeut0)/100*128, 128));
				p.rect(0, 0, p.width,  p.height);
			}
		}
	}

	public void ajouterProduit(Produit produit) {
		listeProduits.add(produit);
	}

	public Joueur getJoueur() {
		return joueur;
	}
	
	public void addTapis(Tapis tapis) {
		this.listeTapis.add(tapis);
	}

	public void addMachine(Machine m) {
		listeMachines.add(m);		
	}

	/**
	 * @return the listeTapis
	 */
	public List<Tapis> getListeTapis() {
		return listeTapis;
	}

	/*
	 * définit le controleur des événements (apparition des produits) pour la scène
	 * courante
	 */
	public void setControleurEvenements(ControleurEvenements eventCtrl) {
		this.eCtrl = eventCtrl;
	}

	public Produit getProduitZone(Rectangle zone, Set<TypeProduit> keySet) {
		for (Produit p : listeProduits) {
			if (keySet.contains(p.getType()) && p.collision(zone))
				return p;
		}
		return null;
	}

	public void setMiniJeu(Machine machine, TypeMiniJeu type) {
		miniJeuCourant = MiniJeu.createMiniJeu(type, machine);
		joueur.setVitesse(new PVector(0, 0));
	}

	public boolean estEnMiniJeu() {
		return getMiniJeu() != null;
	}

	public MiniJeu getMiniJeu() {
		return miniJeuCourant;
	}

	public Machine getNearestMachine(Entite e) {
		return listeMachines.stream().filter(m -> m.distanceA(e) < MAX_DISTANCE_MACHINE).min(new Comparator<Entite>() {
			@Override
			public int compare(Entite e1, Entite e2) {
				return (int) (e1.distanceA(e) - e2.distanceA(e));
			}
		}).orElse(null);
	}

}
