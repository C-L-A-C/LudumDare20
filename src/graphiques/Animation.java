package graphiques;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Classe permettant de gérer l'animation de sprites
 * @author adrien
 *
 */
public class Animation implements Apparence {

	private List<PImage> frames;
	private int index, delay;
	private long lastTime;
	private boolean paused;
	private boolean wasReset;

	/**
	 * Construit une animation à partir d'une liste d'images
	 * @param _frames Les images
	 * @param FPS nombre d'images par seconde
	 */
	public Animation(List<PImage> _frames, int FPS) {
		frames = new ArrayList<>(_frames);
		delay = 1000 / FPS;
		paused = true;
	}

	/**
	 * Construit une animation à partir d'un tileset
	 * @param tileset Tileset contenant les images de l'animation
	 * @param begin indice de la première image de l'animation dans le tileset
	 * @param end indice de la dernière image de l'animation dans le tileset
	 * @param FPS nombre d'images par seconde
	 */
	public Animation(Tileset tileset, int begin, int end, int FPS) {
		frames = new ArrayList<>();
		for (int i = begin; i <= end; i++)
			frames.add(tileset.get(i));
		
		delay = 1000 / FPS;
		paused = true;
	}

	/**
	 * Retourne l'image correspondant à l'état courant de l'animation
	 * @return l'image courante, à ne pas modifier
	 */
	public PImage getFrame() {
		wasReset = false;
		long now = System.currentTimeMillis();
		
		if (paused) {
			index = 0;
			lastTime = now;
			paused = false;
		}

		if (now - lastTime > delay) {
			int prevIndex = index;
			index += (now - lastTime) / delay;
			index %= frames.size();
			wasReset = index < prevIndex;
			lastTime = now;
		}
		return frames.get(index);
	}

	public int width() {
		return frames.get(index).width;
	}

	public int height() {
		return frames.get(index).height;
	}

	public void resize(int w, int h) {
		for (PImage f : frames)
			f.resize(w, h);
	}

	// Pause it and resume it on next call to getFrame()
	public void pause() {
		paused = true;
	}
	
	public void resume()
	{
		paused = false;
	}
	
	public boolean hasReset()
	{
		return wasReset;
	}

	@Override
	public void afficher(PApplet p, int x, int y, int w, int h) {
		p.image(getFrame(), x, y, w, h);
	}
}
