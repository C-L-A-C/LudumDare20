package graphiques;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;

public class Tileset {
	private int w, h, wTile, hTile;
	private List<PImage> images;

	public Tileset(PImage img, int nbX, int nbY) {
		w = nbX;
		h = nbY;
		wTile = img.width / w;
		hTile = img.height / h;
				
		images = new ArrayList<>();
		for (int y = 0; y < nbY; y++) 
			for (int x = 0; x < nbX; x++)
				images.add(img.get(x * wTile, y * hTile, wTile, hTile));
	}
	
	public Tileset(String imageName, int nbX, int nbY) {
		this(Assets.getImage(imageName), nbX, nbY);
	}

	public PImage get(int index) {		
		return images.get(index);
	}

	public int getTileX() {
		return w;
	}

	public int getTileY() {
		return h;
	}

	public int getTileW() {
		return wTile;
	}

	public int getTileH() {
		return hTile;
	}
}
