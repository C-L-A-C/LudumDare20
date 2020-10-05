package gui;

import config.Config;
import config.ConfigKey;
import graphiques.Assets;
import menu.PButton;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *  Permet de définir un menu ou un etat du jeu (jeu normal, combat, ...)
 * @author adrien
 *
 */
public abstract class Scene {
	
	protected PApplet p;
	private PImage audioImg, muteImg;
	private PButton soundBtn;
	public static boolean handleMouseHand;
	
	public void setup(PApplet p)
	{
		this.p = p;
		audioImg = Assets.getImage("audio");
		muteImg = Assets.getImage("mute");
		soundBtn = new PButton(p.width-50, p.height-40, 32, 32, Config.readBoolean(ConfigKey.MUTE) ? audioImg : muteImg);
		handleMouseHand = true;
	}
	
	public void fermer() {};

	public abstract void draw();
	
	public void handleButtons() {
		if(handleMouseHand) {
			if(PButton.getOverAButton())
				p.cursor(p.HAND);
			else
				p.cursor(p.ARROW);
		}
		PButton.resetOverAButton();
		
		if(Config.readBoolean(ConfigKey.MUTE))
			soundBtn.setImage(muteImg);
		else
			soundBtn.setImage(audioImg);
		
		soundBtn.afficher(p);
	}

	public void keyPressed() {};

	public void keyReleased() {};

	public void mouseReleased() {};

	public void mousePressed() {
		if(soundBtn.contient(p.mouseX, p.mouseY)) {
			if(Config.readBoolean(ConfigKey.MUTE)) {
				SceneHandler.unmute();
				Config.set(ConfigKey.MUTE, "false");
			} else {
				SceneHandler.mute();
				Config.set(ConfigKey.MUTE, "true");
			}

		}
	}

}
