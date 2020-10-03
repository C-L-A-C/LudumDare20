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
	
	public void keyPressed() {};
	public void keyReleased() {};
	public void mouseReleased() {};
	public void mousePressed() {};
	
}
