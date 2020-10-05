package jeu.produit;

public enum TypeProduit {
	DECHET("tas"),
	METAL("nugget"),
	BOIS("bois"),
	MOUTON("mouton"),
	SABLE("sable"),
	OR_BRUT("or brut"),
	EPEE("sword"),
	RAIL("rail"),
	COUTEAU("couteau"),
	BIJOU("Couronne"),
	METAL_FUSION("metal fusion"),
	TOLE("tole"),
	PLANCHE("planche"),
	CHARBON("charbon"),
	PLASTIQUE("plastique"),
	LAINE("pelote de laine"),
	VIANDE("viande"),
	VERRE("verre"),
	POISON("poison"), 
	PIERRE("pierre");
	
	private String spriteName;
	
	private TypeProduit()
	{
		this(null);
	}
	
	private TypeProduit(String spriteName)
	{
		this.spriteName = spriteName;
	}
	
	public String getSpriteName()
	{
		return spriteName;
	}

	public static TypeProduit getFromName(String produit) {
		for(TypeProduit t : values())
		{
			if (t.toString().equals(produit))
				return t;
		}
		return null;
	}
}
