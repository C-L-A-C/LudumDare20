package jeu.mini;

import jeu.machine.Machine;
import processing.core.PApplet;

public abstract class MiniJeu {
	
	private Machine machine;
	
	protected MiniJeu(Machine machine)
	{
		this.machine = machine;
	}
	
	public static MiniJeu createMiniJeu(TypeMiniJeu type, Machine machine)
	{
		switch(type)
		{
			case RANGE_PRODUITS:
				return new RangeProduits(machine);
			default:
				return null;
		}
	}
	
	public Machine getMachine()
	{
		return machine;
	}

	public abstract void afficher(PApplet p);
	public abstract boolean evoluer();
	
	public void keyPressed(int key) {};
	public void keyReleased(int key) {};
	public void mouseReleased(int x, int y, int button) {};
	public void mousePressed(int x, int y, int button) {};
	
}
