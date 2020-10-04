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
			case PUZZLE:
				return null; //new Puzzle(machine);
			case BOUTONS_MEMOIRE:
				return new BoutonsMemoire(machine);
			default:
				return null;
		}
	}
	
	public static void jaiPerdu(PApplet p) {
		p.fill(255, 0, 0, 50);
		p.rect(0, 0, p.width, p.height);
	}
	
	public Machine getMachine()
	{
		return machine;
	}

	public abstract void afficher(PApplet p);
	/**
	 * Retourne faux si jamais le minijeu est fini
	 * @return
	 */
	public abstract boolean evoluer();
	public abstract boolean estReussi();
	
	public void keyPressed(int key) {};
	public void keyReleased(int key) {};
	public void mouseReleased(int x, int y, int button) {};
	public void mousePressed(int x, int y, int button) {};
	
}
