package jeu.produit;

public enum TypeProduit {
	DECHET("tas 2 détritut"),
	METAL("nugget"),
	BOIS,
	MOUTON("mouton"),
	SABLE("sable"),
	OR_BRUT("or brut"),
	EPEE("sword"),
	RAIL("rail"),
	COUTEAU("couteau"),
	VITRE,
	BIJOU("Couronne"),
	METAL_FUSION("metal fusion"),
	TOLE("tole"),
	PLANCHE("planche"),
	CHARBON("charbon"),
	PLASTIQUE("plastique"),
	ESSENCE,
	LAINE,
	VIANDE,
	VERRE("verre"),
	POISON("poison");
	
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
