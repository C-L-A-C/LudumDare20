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

	protected Machine(float x, float y, Apparence a) {
		super(x, y, a);
		
		recette = creerRecette();
		//TODO: faire ça propre
		int w = 32, h = 32, wZone = 100, hZone = 100;
		forme = new Rectangle(pos, w, h);
		machineActivee = false;
		sortieMachine = new ArrayList<>();
		zoneIngredients = new Rectangle(x - wZone / 2 + w / 2, y - hZone / 2 + h / 2, wZone, hZone);
		produits = new HashMap<>();
	}
	
	protected abstract Recette creerRecette();

	@Override
	public void evoluer(long t, DonneesJeu j)
	{
		// Si on doit output qqchose on le fait
		//TODO: get output Tapis
		Tapis tapis = null;
		if (! sortieMachine.isEmpty()) {
			TypeProduit type = sortieMachine.remove(0);
			//TODO : check collision
			j.ajouterProduit(new Produit(tapis.getX(), tapis.getY(), type));
		}
	}
	
	public boolean prendreIngredient(DonneesJeu j)
	{
		//Si non est plein && si non en processing
		if (machineActivee || estPrete())
				return false;
		
		Produit p = j.getProduitZone(zoneIngredients, getProduitsManquants().keySet());
		if (p == null)
			return false;
				
		return true;
		
	}
	
	/**
	 * La machine est-elle prête à être activée ? 
	 * => Tous les ingrédients sont présents
	 * @return l'état de la machin e
	 */
	public boolean estPrete()
	{
		return true; //getProduitsManquants().isEmpty();
		
	}
	
	private Map<TypeProduit, Integer> getProduitsManquants() {
		Map<TypeProduit, Integer> produitsManquants = new HashMap<>();
		for (Entry<TypeProduit, Integer> ingredient : recette.getIngredientsNecessaires())
		{
			int qtDansLaMachine = produits.getOrDefault(ingredient.getKey(), 0);
			int qtManquante = ingredient.getValue() - qtDansLaMachine;
			if (qtManquante > 0)
				produitsManquants.put(ingredient.getKey(), qtManquante);			
		}
		return produitsManquants;
	}

	public boolean activer(DonneesJeu j)
	{
		if (machineActivee || !estPrete())
			return false;
		
		//TODO: activation mini-jeu
		j.setMiniJeu(this, recette.getTypeMiniJeu());
		
		return true;
	}
	
	/**
	 * Methode permettant de terminer le processing de la machine
	 * @param succes A-t-on reussi le mini jeu ?
	 */
	public void finirActivation(boolean succes)
	{
		Set<Entry<TypeProduit, Integer>> produits;
		if (succes)
			produits = recette.getProduits();
		else
			produits = recette.getDechets();
		
		for (Entry<TypeProduit, Integer> t : produits)
		{
			for (int i = 0; i < t.getValue(); i++)
				sortieMachine.add(TypeProduit.DECHET);				
		}
	}
	
	@Override
	protected void faireCollision(Entite collider, DonneesJeu d) {		
	}


}
