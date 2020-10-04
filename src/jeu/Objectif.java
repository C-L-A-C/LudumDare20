package jeu;

import java.util.HashMap;
import java.util.Iterator;
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
		if (objectifs.containsKey(type)) {
			int qt = reussi.getOrDefault(type, 0);
			reussi.put(type, qt + 1);
		}
	}
	
	public Map<TypeProduit, Integer> getProduitsReussis()
	{
		return new HashMap<>(reussi);
	}
	
	public Map<TypeProduit, Integer> getProduitsManquants()
	{
		Map<TypeProduit, Integer> manquants = new HashMap<>(objectifs);
		Iterator<TypeProduit> it = manquants.keySet().iterator();
		while (it.hasNext())
		{
			TypeProduit type = it.next();
			if (reussi.containsKey(type))
			{
				int qt = manquants.get(type) - reussi.get(type);
				if (qt <= 0)
					it.remove();
				else
					manquants.put(type, qt);
			}
		}
		return manquants;
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
