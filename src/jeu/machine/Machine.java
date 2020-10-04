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
import jeu.Tapis;
import jeu.TypeDirectionTapis;
import jeu.produit.Produit;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;

public abstract class Machine extends Entite {

	/**
	 * Produits dans la machine, avec leur nombre
	 */
	Map<TypeProduit, Integer> produits;

	/**
	 * Recette acceptee par cette machine
	 */
	Recette recette;

	private List<TypeProduit> sortieMachine;

	private boolean machineActivee;

	private Rectangle zoneIngredients;

	private TypeDirectionTapis direction;

	protected Machine(float x, float y, Apparence a, TypeDirectionTapis direction) {
		super(x, y, a);

		recette = creerRecette();
		// TODO: faire ça propre
		int w = Tapis.W, h = Tapis.H, wZone = Tapis.W * 3, hZone = Tapis.H * 3;
		forme = new Rectangle(pos, w, h);

		this.direction = direction;
		switch (direction) {
		case HAUT:
			zoneIngredients = new Rectangle(x - wZone / 2 + w / 2, y - h, wZone, h);
			break;
		case BAS:
			zoneIngredients = new Rectangle(x - wZone / 2 + w / 2, y + h , wZone, h);
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
	}

	protected abstract Recette creerRecette();

	@Override
	public void evoluer(long t, DonneesJeu j) {
		// Si on doit output qqchose on le fait
		// TODO: get output Tapis
		Tapis tapis = j.getTapisInDirection((int) getX(), (int) getY(), direction);
		if (!sortieMachine.isEmpty()) {
			TypeProduit type = sortieMachine.remove(0);
			// TODO : check collision
			j.ajouterProduit(new Produit(tapis.getX(), tapis.getY(), type));
		}
	}
	
	public Rectangle getZoneInRange()
	{
		return zoneIngredients;
	}

	public boolean prendreIngredient(DonneesJeu j) {
		// Si non est plein && si non en processing
		if (machineActivee || estPrete() || !sortieMachine.isEmpty())
			return false;

		Produit p = j.prendreProduitZone(zoneIngredients, getProduitsManquants().keySet());
		if (p == null)
			return false;

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
		for (Entry<TypeProduit, Integer> ingredient : recette.getIngredientsNecessaires()) {
			int qtDansLaMachine = produits.getOrDefault(ingredient.getKey(), 0);
			int qtManquante = ingredient.getValue() - qtDansLaMachine;
			if (qtManquante > 0)
				produitsManquants.put(ingredient.getKey(), qtManquante);
		}
		return produitsManquants;
	}

	public boolean activer(DonneesJeu j) {
		if (machineActivee || !estPrete())
			return false;

		// TODO: activation mini-jeu
		j.setMiniJeu(this, recette.getTypeMiniJeu());

		return true;
	}

	/**
	 * Methode permettant de terminer le processing de la machine
	 * 
	 * @param succes A-t-on reussi le mini jeu ?
	 */
	public void finirActivation(boolean succes) {

		Set<Entry<TypeProduit, Integer>> produits;
		if (succes)
			produits = recette.getProduits();
		else
			produits = recette.getDechets();

		for (Entry<TypeProduit, Integer> t : produits) {
			for (int i = 0; i < t.getValue(); i++)
				sortieMachine.add(TypeProduit.DECHET);
		}
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}

}
