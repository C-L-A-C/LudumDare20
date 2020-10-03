package controles;

public enum Controle {
	HAUT, BAS, DROITE, GAUCHE, ACTIVE_MACHINE,
	HAUT_RELACHE(false), BAS_RELACHE(false), DROITE_RELACHE(false), GAUCHE_RELACHE(false), ACTIVE_MACHINE_RELACHE(false);
	
	private boolean enfonce;

	private Controle()
	{
		this(true);
	}
	
	private Controle(boolean appuye)
	{
		this.enfonce = appuye;
	}
	
	public boolean estEnfonce() {
		return enfonce;
	}

	public int getIndex() {
		return ordinal();
	}
}