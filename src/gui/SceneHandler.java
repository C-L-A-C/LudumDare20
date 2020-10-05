package gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import config.Config;
import config.ConfigKey;
import graphiques.Assets;
import processing.core.PApplet;
import processing.sound.SoundFile;
import utils.Logger;

/**
 * Gere la fenetre graphique et la scene couramment affichee
 * 
 * @author adrien
 *
 */
public class SceneHandler extends PApplet {

	public static PApplet pAppletInstance;
	private static Scene runningScene;
	private static Clip clip;

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

	public static void preloadSound(String path) {
		try {
			Assets.preload(path, "sound");
		} catch (IOException e) {
			Logger.printlnErr(e.getMessage());
		}
	}

	public static SoundFile playSound(String path, float amp, float rate, float offset, boolean replay) {
		SoundFile sound = Assets.getSound(path);
		if (Config.readBoolean(ConfigKey.MUTE) || sound==null)
			return null;

		sound.amp(amp);
		sound.cue(offset);
		sound.rate(rate);
		if (replay)
			sound.loop();
		else
			sound.play();
		return sound;
	}

	public static void playSoundFast(String path) {
		if(clip!=null)
			return;
		try {
			File file = new File(path);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-3.0f);
			if(!Config.readBoolean(ConfigKey.MUTE))
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (LineUnavailableException e3) {
			e3.printStackTrace();
		}
	}
	
	public static void mute() {
		clip.stop();
	}
	
	public static void unmute() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
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
