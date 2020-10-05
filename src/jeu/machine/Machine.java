package jeu.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import collision.Rectangle;
import graphiques.Apparence;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.produit.Produit;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.Tapis;
import jeu.tapis.TypeDirectionTapis;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Machine extends Entite {

	/**
	 * Produits dans la machine, avec leur nombre
	 */
	private Map<TypeProduit, Integer> produits;

	/**
	 * Recettes acceptees par cette machine
	 */
	private List<Recette> listeRecettes;

	private List<TypeProduit> sortieMachine;

	private boolean machineActivee;
	private Rectangle zoneIngredients;
	private TypeDirectionTapis direction;
	private Recette recetteCourante;
	private boolean bloquee;
	private long tBloque;

	protected Machine(float x, float y, Apparence a, TypeDirectionTapis direction) {
		super(x, y, a);

		listeRecettes = new ArrayList<>();
		recetteCourante = null;
		remplirRecettes(listeRecettes);

		// TODO: faire ça propre
		int w = Tapis.W, h = Tapis.H, wZone = (int) (Tapis.W * 1.5), hZone = (int) (Tapis.H * 1.5);
		forme = new Rectangle(pos, w, h);

		this.direction = direction;
		switch (direction) {
		case HAUT:
			zoneIngredients = new Rectangle(x - wZone / 2 + w / 2, y - h, wZone, h);
			break;
		case BAS:
			zoneIngredients = new Rectangle(x - wZone / 2 + w / 2, y + h, wZone, h);
			break;
		case DROITE:
			zoneIngredients = new Rectangle(x + w, y - hZone / 2 + h / 2, w, hZone);
			break;
		case GAUCHE:
			zoneIngredients = new Rectangle(x - w, y - hZone / 2 + h / 2, w, hZone);
			break;
		}

		machineActivee = false;
		sortieMachine = new ArrayList<>();
		produits = new HashMap<>();
		bloquee = false;
	}

	protected abstract void remplirRecettes(List<Recette> listeRecettes);

	public void afficherOverlay(PApplet p)
	{
		p.fill(255, 255, 0, 125);
		p.stroke(255, 255, 0);
		p.rect(zoneIngredients.getX() + 1, zoneIngredients.getY() + 1, zoneIngredients.getW() - 2, zoneIngredients.getH() - 2);
		
		p.fill(50, 200);
		p.stroke(20);
		
		int nb;
		List<TypeProduit> produitsMachine;
		if (sortieMachine.isEmpty())
		{
			if(recetteCourante == null)
				nb = 
					listeRecettes.stream().map(Recette::getNbIngredients).max((i1, i2) -> i1 - i2).orElse(0);
			else 
				nb = 
					recetteCourante.getNbIngredients();

			produitsMachine = new ArrayList<>(produits.keySet());
		}
		else {
			nb = sortieMachine.size();
			produitsMachine = sortieMachine;
		}
			
		float wMachine = getForme().getW() * 1.2f;
		float hMachine = getForme().getH() * 1.2f;
		float wCase = 40, hCase = 32;
		float wBord = 8;
		float xDep = getX() + wMachine / 2 - (wCase * nb) / 2;
		float yDep = getY() + hMachine + 5;
		
		for (int i = 0; i < nb; i++)
		{
			p.rect(xDep + i * wCase, yDep, wCase - wBord, hCase);
			if (i < produitsMachine.size())
			{
				PImage img = Produit.getImage(produitsMachine.get(i));
				p.image(img, xDep + i * wCase + 2, yDep + 2, wCase - wBord - 4, hCase - 4);
			}
		}
	}
	
	public long getTempsBloque(long t)
	{
		return bloquee ? t - tBloque : 0;
	}

	@Override
	public void evoluer(long t, DonneesJeu j) {
		// Si on doit output qqchose on le fait
		if (!sortieMachine.isEmpty()) {
			Tapis tapis = j.getTapisInDirection((int) (getX() + getForme().getW() / 2),
					(int) (getY() + getForme().getH() / 2), direction);
			TypeProduit type = sortieMachine.get(0);
			Produit p = new Produit(tapis.getX() + Tapis.W / 2 - 10, tapis.getY() + Tapis.H / 2 - 10, type);

			if (j.checkCollision(p) == null) {
				j.ajouterProduit(p);
				sortieMachine.remove(0);
				bloquee = false;
			}
			else if (! bloquee) {
				bloquee = true;
				tBloque = t;				
			}
		}
	}

	public Rectangle getZoneInRange() {
		return zoneIngredients;
	}

	public boolean prendreIngredient(DonneesJeu j) {
		if (machineActivee || estPrete() || !sortieMachine.isEmpty())
			return false;

		Produit p = j.prendreProduitZone(zoneIngredients, getProduitsManquants().keySet());
		if (p == null)
			return false;

		else {
			for (Recette r : listeRecettes) {
				if (r.getIngredientsNecessaires().stream().filter(e -> p.getType() == e.getKey()).count() != 0) {
					recetteCourante = r;
					break;
				}
			}
		}

		int qt = produits.getOrDefault(p.getType(), 0);
		produits.put(p.getType(), qt + 1);

		return true;

	}

	/**
	 * La machine est-elle prête à être activée ? => Tous les ingrédients sont
	 * présents
	 * 
	 * @return l'état de la machin e
	 */
	public boolean estPrete() {
		return getProduitsManquants().isEmpty();

	}

	private Map<TypeProduit, Integer> getProduitsManquants() {
		Map<TypeProduit, Integer> produitsManquants = new HashMap<>();
		List<Recette> recettesPossibles = new ArrayList<>();

		if (recetteCourante != null)
			recettesPossibles.add(recetteCourante);
		else
			recettesPossibles = listeRecettes;

		for (Recette r : recettesPossibles) {
			for (Entry<TypeProduit, Integer> ingredient : r.getIngredientsNecessaires()) {
				int qtDansLaMachine = produits.getOrDefault(ingredient.getKey(), 0);
				int qtManquante = ingredient.getValue() - qtDansLaMachine;
				if (qtManquante > 0)
					produitsManquants.put(ingredient.getKey(), qtManquante);
			}
		}
		return produitsManquants;
	}

	public boolean activer(DonneesJeu j) {
		if (machineActivee || !estPrete())
			return false;

		// TODO: activation mini-jeu
		j.setMiniJeu(this, recetteCourante.getTypeMiniJeu());

		return true;
	}

	/**
	 * Methode permettant de terminer le processing de la machine
	 * 
	 * @param succes A-t-on reussi le mini jeu ?
	 */
	public void finirActivation(boolean succes) {

		Set<Entry<TypeProduit, Integer>> production;
		if (succes)
			production = recetteCourante.getProduits();
		else
			production = recetteCourante.getDechets();

		for (Entry<TypeProduit, Integer> t : production) {
			for (int i = 0; i < t.getValue(); i++)
				sortieMachine.add(t.getKey());
		}
		recetteCourante = null;
		produits.clear();
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}

}
