package jeu.produit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jeu.mini.TypeMiniJeu;

public class Recette {
	
	/**
	 * Les ingredients nécessaires à la recette, avec leur quantité
	 */
	private Map<TypeProduit, Integer> ingredients;
	/**
	 * La durée de processing de la recette;
	 */
	private Integer duree;
	/**
	 * Les produits résultant de cette recette
	 */
	private Map<TypeProduit, Integer> produits;
	/**
	 * Nombre de déchets crées en cas de fail
	 */
	private int nbDechets;
	
	private TypeMiniJeu miniJeu;
	
	
	/** 
	 * Crée une recette vide
	 */
	public Recette(int nbDechets, int duree, TypeMiniJeu type) {
		ingredients = new HashMap<>();
		this.duree = duree;
		this.nbDechets = nbDechets;
		produits = new HashMap<>();
		this.miniJeu = type;
	}
	
	public void ajouterIngredient(TypeProduit ingredient, int nb)
	{
		ingredients.put(ingredient, nb);
	}
	
	public void ajouterIngredient(TypeProduit ingredient)
	{
		ajouterIngredient(ingredient, 1);
	}
	
	public void ajouterProduit(TypeProduit produit, int nb)
	{
		produits.put(produit, nb);
	}
	
	public void ajouterProduit(TypeProduit produit)
	{
		ajouterProduit(produit, 1);
	}
	
	public int getNbIngredients()
	{
		return ingredients.values().stream().reduce(0, (i1, i2) -> i1 + i2);
	}
	
	public Set<Entry<TypeProduit, Integer>> getIngredientsNecessaires()
	{
		return ingredients.entrySet();
	}
		
	public int getDuree()
	{
		return duree;
	}
	
	public int getNbDechets()
	{
		return nbDechets;
	}
	
	/**
	 * Retourne l'ensemble des produits sous forme d'un tuple (type du produit, nombre de produit)
	 * @return
	 */
	public Set<Entry<TypeProduit, Integer>> getProduits()
	{
		return produits.entrySet();
	}
	
	public Set<Entry<TypeProduit, Integer>> getIngredients()
	{
		return ingredients.entrySet();
	}

	public Set<Entry<TypeProduit, Integer>> getDechets() {
		 Set<Entry<TypeProduit, Integer>> set = new HashSet<>();
		 set.add(new HashMap.SimpleEntry<>(TypeProduit.DECHET, getNbDechets()));
		 return set;
	}

	public TypeMiniJeu getTypeMiniJeu() {
		return miniJeu;
	}

}
