package graphiques;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Classe gérant un ensemble d'animations, par exemple les animations de déplacements d'une même personnage dans les différentes directions
 * @author adrien
 *
 */
public class AnimationSet implements Apparence{
	private List<Animation> anims;
	private int current;
	private List<Integer> queue;

	public AnimationSet(ArrayList<Animation> _anims) throws IllegalArgumentException {
		anims = new ArrayList<>(_anims);
		queue = new ArrayList<>();
		current = 0;
	}

	public AnimationSet(Tileset tileset, int FPS, int offset) {
		int w = tileset.getTileX();
		anims = new ArrayList<>();
		for (int i = 0; i < tileset.getTileY(); i++) {
			int index = i + offset;
			anims.add(new Animation(tileset, index * w, (index + 1) * w - 1, FPS));
		}
		queue = new ArrayList<>();
		current = 0;
	}

	public void add(Animation anim) {
		anims.add(anim);
	}

	public PImage getFrame() {
		anims.get(current).resume();
		PImage img = anims.get(current).getFrame();

		if (anims.get(current).hasReset() && queue.size() > 0) {
			current = queue.get(0);
			queue.remove(0);
			img = anims.get(current).getFrame();
		}
		return img;
	}

	public int width() {
		return anims.get(current).width();
	}

	public int height() {
		return anims.get(current).height();
	}

	public void resize(int w, int h) {
		for (Animation a : anims)
			a.resize(w, h);
	}

	/**
	 * Change l'animation en cours
	 * @param key indice de l'animation
	 */
	public void change(int key) {
		if (key < 0 || key >= anims.size())
			throw new IllegalArgumentException("Animation invalide");
		
		anims.get(current).pause();
		current = key;
	}

	/**
	 * Ajoute une animation à la queue d'animation
	 * Elle sera jouée une seule fois, quand l'animation courante sera terminée
	 * @param key indice de l'animation
	 */
	public void queue(int key) {
		if (key < 0 || key >= anims.size())
			throw new IllegalArgumentException("Animation invalide");
		
		queue.add(key);
	}

	@Override
	public void afficher(PApplet p, int x, int y, int w, int h) {
		p.image(getFrame(), x, y, w, h);
	}
}