package jeu.produit;

public enum TypeProduit {
	DECHET("tas"),
	METAL("nugget"),
	BOIS("bois"),
	MOUTON("mouton"),
	SABLE("sable"),
	OR_BRUT("or brut"),
	EPEE("sword", 125),
	RAIL("rail", 150),
	COUTEAU("couteau", 125),
	BIJOU("Couronne", 90),
	METAL_FUSION("metal fusion", 50),
	TOLE("tole", 25),
	PLANCHE("planche", 50),
	CHARBON("charbon", 50),
	PLASTIQUE("plastique"),
	LAINE("pelote de laine", 90),
	VIANDE("viande", 90),
	VERRE("verre", 75),
	POISON("poison"), 
	PIERRE("pierre", 0);
	
	private String spriteName;
	private int pts;
	
	private TypeProduit(String spriteName)
	{
		this(spriteName, 0);
	}
	
	private TypeProduit(String spriteName, int pts)
	{
		this.spriteName = spriteName;
		this.pts = pts;
	}
	
	public String getSpriteName()
	{
		return spriteName;
	}
	
	public int getPoints()
	{
		return pts;
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
