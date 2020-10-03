package gui;

import processing.core.PApplet;

/**
 *  Permet de définir un menu ou un etat du jeu (jeu normal, combat, ...)
 * @author adrien
 *
 */
public abstract class Scene {
	
	protected PApplet p;
	
	public void setup(PApplet p)
	{
		this.p = p;
	}
	
	public void fermer() {};

	public abstract void draw();

	public void keyPressed() {};

	public void keyReleased() {};

	public void mouseReleased() {};

	public void mousePressed() {};

}
