package jeu.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import collision.Rectangle;
import graphiques.Apparence;
import gui.SceneHandler;
import jeu.DonneesJeu;
import jeu.Entite;
import jeu.produit.Produit;
import jeu.produit.Recette;
import jeu.produit.TypeProduit;
import jeu.tapis.Tapis;
import jeu.tapis.TypeDirectionTapis;

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

	protected abstract void remplirRecettes(List<Recette> listeRecettes);

	@Override
	public void evoluer(long t, DonneesJeu j) {
		// Si on doit output qqchose on le fait
		if (!sortieMachine.isEmpty()) {
			Tapis tapis = j.getTapisInDirection((int) (getX() + getForme().getW() / 2), (int) (getY() + getForme().getH() / 2), direction);
			TypeProduit type = sortieMachine.get(0);
			Produit p = new Produit(tapis.getX() + Tapis.W / 2 - 10, tapis.getY() + Tapis.H / 2 - 10, type);
			
			if (j.checkCollision(p) == null) {
				j.ajouterProduit(p);
				sortieMachine.remove(0);
			}
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
		
		else {
			for (Recette r : listeRecettes)
			{
				if (r.getIngredientsNecessaires().stream().filter(e -> p.getType() == e.getKey()).count() != 0)
				{
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
		
		for (Recette r : recettesPossibles)
		{
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

	private Recette getRecetteCourante() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {
	}

}
