package jeu.tapis;

import processing.core.PVector;

public enum TypeDirectionTapis {
	BAS(0, 1), DROITE(1, 0), HAUT(0, -1), GAUCHE(-1, 0);
	
	private PVector vect;
	
	private TypeDirectionTapis(int x, int y)
	{
		vect = new PVector(x, y);
	}
	
	public PVector vecteurDirecteur()
	{
		return vect;
	}
}
