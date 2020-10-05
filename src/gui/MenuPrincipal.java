package gui;

import java.util.ArrayList;
import java.util.List;

import graphiques.Assets;
import jeu.DonneesJeu;
import jeu.machine.Cuiseur;
import jeu.machine.Fonderie;
import jeu.machine.Machine;
import jeu.machine.Scie;
import jeu.machine.Toleuse;
import jeu.machine.Tondeuse;
import jeu.produit.Produit;
import jeu.produit.TypeProduit;
import jeu.tapis.Selecteur;
import jeu.tapis.Tapis;
import jeu.tapis.TypeDirectionTapis;
import menu.PButton;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.Utils;

public class MenuPrincipal extends Scene {

	private PButton buttonCampaign, buttonCredits, buttonQuit;
	private List<Tapis> tapis;
	private List<Produit> produits;
	private List<Machine> machines;
	private long lastTemps, lastTempsProduit;
	private Selecteur selecteur;

	public MenuPrincipal(PApplet p) {
		this.setup(p);
		float widthButton = p.width / 3;
		float heightButton = p.height / 5;
		this.buttonCampaign = new PButton(p.width / 2, p.height / 4, widthButton, heightButton, "PLAY");
		this.buttonCredits = new PButton(p.width / 2, 2 * p.height / 4, widthButton, heightButton, "CREDITS");
		this.buttonQuit = new PButton(p.width / 2, 3 * p.height / 4, widthButton, heightButton, "QUIT");

		tapis = new ArrayList<>();
		produits = new ArrayList<>();

		lastTemps = System.currentTimeMillis();
		lastTempsProduit = 0;
		machines = new ArrayList<>();

		initTapis();
		
		
		machines.add(new Fonderie(20 + Tapis.W * 3, Tapis.H * 1, TypeDirectionTapis.HAUT));
		machines.add(new Toleuse(20 + Tapis.W * 11, Tapis.H * 1, TypeDirectionTapis.HAUT));
		machines.add(new Tondeuse(20 + Tapis.W * 3, Tapis.H * ((p.height / Tapis.H) - 2), TypeDirectionTapis.BAS));
		machines.add(new Cuiseur(20 + Tapis.W * 11, Tapis.H * ((p.height / Tapis.H) - 2), TypeDirectionTapis.BAS));
	}

	private void initTapis() {
		int nbX = (p.width - 40) / Tapis.W, nbY = (p.height - 0) / Tapis.H;
		for (int i = 0; i < nbX - 1; i++) {
			int x = i * Tapis.W + 20, y = 0;
			tapis.add(new Tapis(x, y, TypeDirectionTapis.DROITE));
		}
		for (int i = 0; i < nbY - 1; i++) {
			int x = 20 + (nbX - 1) * Tapis.W + (i >= 3 && i <= 7 ? -2 * Tapis.W : ( i == 8 ? - Tapis.W : 0)), y = i * Tapis.H + 0;
			tapis.add(new Tapis(x, y, TypeDirectionTapis.BAS));
		}
		for (int i = 4; i < 7; i++) {
			int x = 20 + (nbX - 1) * Tapis.W, y = i * Tapis.H + 0;
			tapis.add(new Tapis(x, y, TypeDirectionTapis.BAS));
		}
		for (int i = 0; i < nbX - 1; i++) {
			int x = (nbX - i - 1) * Tapis.W + 20, y = (nbY - 1) * Tapis.H + 0;
			tapis.add(new Tapis(x, y, TypeDirectionTapis.GAUCHE));
		}
		for (int i = 0; i < nbY - 1; i++) {
			int x = 20, y = (nbY - i - 1) * Tapis.H + 0;
			tapis.add(new Tapis(x, y, TypeDirectionTapis.HAUT));
		}

		tapis.add(new Tapis(Tapis.W + 20, Tapis.H * nbY / 2, TypeDirectionTapis.GAUCHE));
		tapis.add(new Tapis(20 + (nbX - 1) * Tapis.W, 3 * Tapis.H, TypeDirectionTapis.GAUCHE));
		tapis.add(new Tapis(20 + (nbX - 2) * Tapis.W , 4* Tapis.H , TypeDirectionTapis.DROITE));
		tapis.add(new Tapis(20 + (nbX - 3) * Tapis.W , 7* Tapis.H , TypeDirectionTapis.DROITE));
		tapis.add(new Tapis(20 + (nbX - 1) * Tapis.W, 7 * Tapis.H, TypeDirectionTapis.GAUCHE));
		tapis.add(new Tapis(20 + (nbX - 2) * Tapis.W, 7 * Tapis.H, TypeDirectionTapis.BAS));
		tapis.add(new Tapis(20 + (nbX - 2) * Tapis.W, 9 * Tapis.H, TypeDirectionTapis.DROITE));
		
		selecteur = new Selecteur(20 + (nbX - 2) * Tapis.W, 3 * Tapis.H, TypeDirectionTapis.BAS, TypeDirectionTapis.GAUCHE);
		TypeProduit[] typesProduits = TypeProduit.values();
		for (int i = 0; i < typesProduits.length / 2; i ++)
			selecteur.ajouterProduitFiltre(typesProduits[i]);
			
		tapis.add(selecteur);
			

	}

	@Override
	public void draw() {
		p.background(0);
		//background
		PImage background = Assets.getImage("background");
		p.image(background, 0, 0);
		
		long courant = System.currentTimeMillis();
		long tempsBoucle = courant - lastTemps;
		lastTemps = courant;

		if (courant - lastTempsProduit > 1000) {
			genererProduitAleatoire();
			lastTempsProduit = courant;
		}

		for (Tapis t : tapis)
			t.afficher(p);

		for (Produit prod : produits) {
			prod.adhererTapis(tapis);
			PVector nextPos = PVector.add(prod.getPos(), PVector.mult(prod.getVitesse(), tempsBoucle / 1000f));
			if (!checkCol(prod)) {
				prod.setX(nextPos.x);
				prod.setY(nextPos.y);
			}
			prod.afficher(p);
		}
		
		for (Machine m : machines)
			m.afficher(p);
		
		selecteur.afficher(p);

		this.buttonCampaign.afficher(p);
		this.buttonCredits.afficher(p);
		this.buttonQuit.afficher(p);
		
		this.handleButtons();
	}

	private boolean checkCol(Produit prod) {
		for (Produit e : produits)
		{
			if (prod != e && e.collision(prod))
				return true;
		}
		return false;
	}

	private void genererProduitAleatoire() {
		if (produits.size() >= 13)
			return;
		
		int x = (int) (1.5 * Tapis.W + 20), y = Tapis.H * (p.height / Tapis.H) / 2 + Tapis.H / 2 - 10;

		Produit prod = new Produit(x, y, Utils.random(TypeProduit.values()));
		if (checkCol(prod))
			return;

		produits.add(prod);
	}

	@Override
	public void mousePressed() {
		super.mousePressed();
		if (buttonCampaign.contient(p.mouseX, p.mouseY)) {
			p.cursor(p.ARROW);
			SceneHandler.setRunning(new Jeu(2));
		}
		if (buttonCredits.contient(p.mouseX, p.mouseY)) {
			p.cursor(p.ARROW);
		}
		if (buttonQuit.contient(p.mouseX, p.mouseY)) {
			p.exit();
		}
	}

}