package gui;

import processing.core.PApplet;

/**
 * Gere la fenetre graphique et la scene couramment affichee
 * 
 * @author adrien
 *
 */
public class SceneHandler extends PApplet {

	public static PApplet pAppletInstance;
	private static Scene runningScene;

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

	public void settings() {
		size(640, 480);
	}

	public void setup() {
		pAppletInstance = this;
		runningScene.setup(this);
	}

	public void draw() {
		runningScene.draw();
	}

	public void keyPressed() {
		runningScene.keyPressed();
		key = 0; // Avoid ESC key to terminte program
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
