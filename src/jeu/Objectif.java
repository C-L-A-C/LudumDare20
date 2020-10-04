package jeu;

import java.util.HashMap;
import java.util.Map;

import jeu.produit.TypeProduit;

public class Objectif {
	
	private Map<TypeProduit, Integer> objectifs;
	private Map<TypeProduit, Integer> reussi;
	
	public Objectif()
	{
		objectifs = new HashMap<>();
		reussi = new HashMap<>();
	}
	
	public void ajouterObjectif(TypeProduit type, int nb)
	{
		objectifs.put(type, nb);
	}
	
	public void ajouterProduitReussi(TypeProduit type)
	{
		int qt = reussi.getOrDefault(type, 0);
		reussi.put(type, qt + 1);
	}
	
	public boolean sontSatisfaits()
	{
		for (TypeProduit type : objectifs.keySet())
		{
			if (reussi.getOrDefault(type, 0) < objectifs.get(type))
				return false;
		}
		return true;
	}

}
