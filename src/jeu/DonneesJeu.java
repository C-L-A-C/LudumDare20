package jeu;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import collision.Forme;
import collision.Point;
import collision.Rectangle;
import graphiques.Assets;
import graphiques.Tileset;
import gui.SceneHandler;
import jeu.machine.Machine;
import jeu.mini.MiniJeu;
import jeu.mini.TypeMiniJeu;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;
import jeu.tapis.Selecteur;
import jeu.tapis.Sortie;
import jeu.tapis.Tapis;
import jeu.tapis.TypeDirectionTapis;

import processing.sound.*;

public class DonneesJeu {
	private static final float MAX_DISTANCE_MACHINE = 50;

	private long failedMinijeut0;
	private boolean failedMinijeu;
	
	private Horloge clock;

	private Joueur joueur;
	private Scroll scroll;
	private ControleurEvenements eCtrl;
	private List<Tapis> listeTapis;
	private List<Produit> listeProduits;
	private List<Machine> listeMachines;
	private List<Sortie> listeSorties;
	private int playSound;
	private List<Selecteur> listeSelecteurs;

	private Objectif objectifs;
	private MiniJeu miniJeuCourant;
	
	private boolean afficherOverlay;

	private boolean toutPeteParcequeCestBloque;

	//private PVector debugPos;

	public DonneesJeu() {
		int viewW = 640, viewH = 400;

		joueur = new Joueur(0, 0);
		scroll = new Scroll(viewW, viewH, viewW, viewH);
		listeTapis = new ArrayList<>();
		listeProduits = new ArrayList<>();
		listeSorties = new ArrayList<>();
		playSound = 0;
		listeSelecteurs = new ArrayList<>();
		
		this.failedMinijeu = false;
		listeMachines = new ArrayList<>();
		objectifs = new Objectif();
			
		miniJeuCourant = null;
		afficherOverlay = false;
		toutPeteParcequeCestBloque = false;
		
		// Preloading sounds
		SceneHandler.preloadSound("assets/sounds/failed.mp3");
		SceneHandler.preloadSound("assets/sounds/positive_beep.wav");
	}
	
	public void ajouterObjectif(TypeProduit type, int nb)
	{
		objectifs.ajouterObjectif(type, nb);
		System.out.println(type);
		for (Selecteur s: listeSelecteurs)
			s.ajouterProduitFiltre(type);
	}

	public Entite checkCollision(Entite e) {
		float eW = e.getForme().getW(), eH = e.getForme().getH();

		int width = (int) scroll.getTotalW(), height = (int) scroll.getTotalH();
		Rectangle rectMonde = new Rectangle(eW, eH, width - 2 * eW + 1, height - 2 * eH + 1);
		if (e == joueur && !e.collision(rectMonde))
			return new Entite(0, 0, null) {protected void faireCollision(Entite collider, DonneesJeu d) {}}; //TODO: pas propre

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
		
		for (Produit m : listeProduits) {
			if (e != m && e.collision(m)) {
				return m;
			}
		}
		
		for (Sortie s : listeSorties) {
			if (e != s && e.collision(s)) {
				return s;
			}
		}

		return null;
	}

	public void evoluer(long t) {
		joueur.evoluer(t, this);
		// changement des vitesses des produits
		for (Produit p : listeProduits) {
			p.adhererTapis(this);
			p.evoluer(t, this);
		}
		
		for (Sortie s: listeSorties) {
			List<Produit> produitsSortis = new ArrayList<>();
			produitsSortis = s.reduireCollisions(listeProduits, t);
			if (produitsSortis != null) {
				for (Produit p : produitsSortis) {
					objectifs.ajouterProduitReussi(p.getType());
					listeProduits.remove(p);
				}
			}
		}
		
		for (Machine m : listeMachines)
		{
			m.evoluer(t, this);
			if (m.getTempsBloque(t) > 5000)
			{
				toutPeteParcequeCestBloque = true;
			}
		}

		if (estEnMiniJeu()) {	
			
			if (!miniJeuCourant.evoluer() && !failedMinijeu) {
				if(!miniJeuCourant.estReussi()) {
					failedMinijeut0 = System.currentTimeMillis();
					failedMinijeu = true;
					playSound = -1;
				} else {
					miniJeuCourant.getMachine().finirActivation(miniJeuCourant.estReussi());
					miniJeuCourant = null;
					playSound = 1;
				}
			} 
			
			if(failedMinijeu && this.failedMinijeut0 + 2200 < System.currentTimeMillis()) { // 2200 le temps que le sound se termine
				failedMinijeu = false;
				miniJeuCourant.getMachine().finirActivation(miniJeuCourant.estReussi());
				miniJeuCourant = null;
			}
		}
		
		eCtrl.evoluer(listeProduits);
	}

	
	public boolean estGagne()
	{
		return !toutPeteParcequeCestBloque && objectifs.sontSatisfaits();
	}
	
	public boolean estFini()
	{
		return toutPeteParcequeCestBloque || objectifs.sontSatisfaits();

	}

	public void afficher(PApplet p) {
		scroll.update(joueur);
		p.noStroke();

		p.pushMatrix();
		p.translate(-(int) scroll.getX(), -(int) scroll.getY());
		
		

		// On separe les tapis devant les produits de derriere les produits
		Map<Boolean, List<Tapis>> listeTapisEstDevant = listeTapis.stream()
				.collect(Collectors.partitioningBy(t -> t.getLayer() != 0));
		
		//background
		PImage background = Assets.getImage("background");
		for (int i = 0; i <= scroll.getTotalW() / background.width; i++) {
			for (int j = 0; j <= scroll.getTotalH() / background.height; j++) {
				p.image(background, i * background.width, j * background.height);
			}
		}
		
		//On affiche les differentes entites dans le bon ordre : du plus derriere au plus devant
		for (Tapis t : listeTapisEstDevant.get(false))
			t.afficher(p);
		for (Sortie s : listeSorties) 
			s.afficher(p);

		for (Produit prod : listeProduits) 
			prod.afficher(p);

		for (Tapis t : listeTapisEstDevant.get(true))
			t.afficher(p);
		
		// Pour que les selecteurs soient au dessus des produits (on peut pas les changer de layer a cause de la collision)
		// On les réaffiche c'est pas beau mais bon ntm un peu quoi
		for (Tapis t : listeSelecteurs) 
			t.afficher(p);
		
		if (afficherOverlay) {
			for (Machine m : listeMachines) {
				m.afficherOverlay(p);
			}
		}
		
		// On separe les machine en dessous du perso et au dessus du perso
		Map<Boolean, List<Machine>> listeMachineEstDevant = listeMachines.stream()
				.collect(Collectors.partitioningBy(m -> m.getY() > joueur.getY()));
		
		for (Machine machine : listeMachineEstDevant.get(false))
			machine.afficher(p);

		joueur.afficher(p);
		
		for (Machine machine : listeMachineEstDevant.get(true))
			machine.afficher(p);

		p.popMatrix();

		if (estEnMiniJeu())
			miniJeuCourant.afficher(p);
		else {
			afficherObjectif(p);
		}
		
		if (failedMinijeu) {
			p.fill(255, 0, 0, Math.min((float) (System.currentTimeMillis() - this.failedMinijeut0) / 100 * 128, 128));
			p.rect(0, 0, p.width, p.height);
		}

		if (playSound==-1)
			SceneHandler.playSound("assets/sounds/failed.mp3", (float)0.5, 1, 0, false);
		else if(playSound==1)
			SceneHandler.playSound("assets/sounds/positive_beep.wav", (float)0.5, 1, 0, false);
		playSound = 0;
	}

	private void afficherObjectif(PApplet p) {
		int h = 80;
		p.fill(190);
		p.stroke(20);
		p.rect(0, p.height - h, p.width, h);
		
		p.noStroke();
		p.fill(20);
		p.textSize(14);
		p.textAlign(PApplet.CORNER, PApplet.CORNER);
		p.text("Objectives", 5, p.height - h + 15);
		
		int i = 0, wCase = 30, hCase = 25;
		for (Map.Entry<TypeProduit, Integer> reussi : objectifs.getProduitsReussis().entrySet())
		{
			for (int j = 0; j < reussi.getValue(); j++) {
				int xCase = 5 + i * wCase, yCase = p.height - h + 25;
				p.image(Produit.getImage(reussi.getKey()), xCase + 3, yCase + 3, wCase - 6, hCase - 6);
				
				p.fill(50, 255, 100, 120);
				p.stroke(0, 150, 0);
				p.rect(xCase + 1, yCase, wCase - 2, hCase);
				i++;
			}
		}
		
		for (Map.Entry<TypeProduit, Integer> reussi : objectifs.getProduitsManquants().entrySet())
		{
			for (int j = 0; j < reussi.getValue(); j++) {
				int xCase = 5 + i * wCase, yCase = p.height - h + 25;
				p.image(Produit.getImage(reussi.getKey()), xCase + 3, yCase + 3, wCase - 6, hCase - 6);
				
				p.fill(255, 100, 50, 120);
				p.stroke(150, 0, 0);
				p.rect(xCase + 1, yCase, wCase - 2, hCase);
				i++;
			}
		}
	}
	
	public void ajouterProduit(Produit produit) {
		if (checkCollision(produit) == null)
			listeProduits.add(produit);
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void addTapis(Tapis tapis) {
		this.listeTapis.add(tapis);
	}
	
	public void setAffichageOverlay(boolean status)
	{
		afficherOverlay = status;
	}

	public void addSelecteurFin(int x, int y, TypeDirectionTapis direction, TypeDirectionTapis directionFiltree) {
		Selecteur s = new Selecteur(x, y, direction, directionFiltree);
		addTapis(s);
		listeSelecteurs.add(s);
	}

	public void addMachine(Machine m) {
		listeMachines.add(m);
	}
	
	public void addSortie(Sortie s) {
		listeSorties.add(s);
	}
	
	public Horloge getHorloge() {
		return clock;
	}
	
	public void setTimer(int secondes) {
		clock = new Horloge(secondes);
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

	public Produit prendreProduitZone(Rectangle zone, Set<TypeProduit> types) {
		for (Produit p : listeProduits) {

			if (types.contains(p.getType()) && p.collision(zone)) {
				listeProduits.remove(p);
				return p;
			}
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

	public Tapis getTapisInDirection(int x, int y, TypeDirectionTapis direction) {
		Point p = new Point(x, y);
		PVector depl = new PVector();
		switch (direction) {
		case HAUT:
			depl.set(0, -1);
			break;
		case BAS:
			depl.set(0, 1);
			break;
		case DROITE:
			depl.set(1, 0);
			break;
		case GAUCHE:
			depl.set(-1, 0);
			break;
		}
		depl.mult(Tapis.W);
		Forme collider = p.getTranslation(depl);
		//debugPos = collider.getCenter();
		return listeTapis.stream().filter(t -> t.collision(collider)).findAny().orElse(listeTapis.get(0));
	}
	
	public void setMapDimensions(int w, int h)
	{
		scroll.setTotalW(w);
		scroll.setTotalH(h);
	}
	
	public boolean getAffichageOverlay() {
		return afficherOverlay;
	}

}