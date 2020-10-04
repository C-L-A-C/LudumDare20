package gui;

import graphiques.Assets;
import processing.core.PApplet;
import processing.sound.SoundFile;

/**
 * Gere la fenetre graphique et la scene couramment affichee
 * 
 * @author adrien
 *
 */
public class SceneHandler extends PApplet {

	public static PApplet pAppletInstance;
	private static Scene runningScene;
	private static SoundFile sound;
	private static SoundFile soundAmbiance;
	private static SoundFile soundMusique;
	

	public static void launch(Scene scene) {
		// Launch le main de processing
		runningScene = scene;
		PApplet.main(SceneHandler.class.getCanonicalName());
	}

	public static void setRunning(Scene s) {
		if (runningScene != null)
			runningScene.fermer();

		runningScene = s;
		s.setup(pAppletInstance);
	}
	
	public static void setRunningWithoutClosing(Scene s) {
		runningScene = s;
	}

	public void settings() {
		size(640, 480);
		Assets.init(this);		
	}

	public void setup() {
		pAppletInstance = this;
		runningScene.setup(this);
	}

	public void draw() {
		runningScene.draw();
	}
	
	public static SoundFile preloadSound(String path) {
		return new SoundFile(pAppletInstance, path);
	}
	
	public static SoundFile playSound(String path, float amp, float rate, float offset, boolean replay) {
		sound = new SoundFile(pAppletInstance, path);
		sound.amp(amp);
		sound.cue(offset);
		sound.rate(rate);
		if(replay)
			sound.loop();
		else
			sound.play();
		return sound;
	}
	
	public static SoundFile playSoundAmbiance(String path, float amp) {	
		soundAmbiance = new SoundFile(pAppletInstance, path);
		soundAmbiance.amp(amp);
		soundAmbiance.loop();
		return soundAmbiance;
	}
	
	public static SoundFile playSoundMusique(String path, float amp) {	
		soundMusique = new SoundFile(pAppletInstance, path);
		soundMusique.amp(amp);
		soundMusique.loop();
		return soundMusique;
	}

	public void keyPressed() {
		runningScene.keyPressed();
		key = 0; // Avoid ESC key to terminate program
	}

	public void keyReleased() {
		runningScene.keyReleased();
	}

	public void mousePressed() {
		runningScene.mousePressed();
	}

	public void mouseReleased() {
		runningScene.mouseReleased();
	}

}
