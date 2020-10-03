package graphiques;

import java.util.ArrayList;
import java.util.List;

import processing.core.PImage;

public class Tileset {
	private int w, h, wTile, hTile;
	private List<PImage> images;

	public Tileset(PImage img, int nbX, int nbY) {
		this(img, nbX, nbY, 0, 0);
	}
	
	public Tileset(String imageName, int nbX, int nbY) {
		this(Assets.getImage(imageName), nbX, nbY);
	}
	
	public Tileset(PImage img, int nbX, int nbY, int decalageX, int decalageY) {
		w = nbX;
		h = nbY;
		wTile = (img.width + 1) / w - decalageX;
		hTile = (img.height + 1) / h - decalageY;
				
		images = new ArrayList<>();
		for (int y = 0; y < nbY; y++) 
			for (int x = 0; x < nbX; x++)
				images.add(img.get(x * (wTile + decalageX), y * (hTile + decalageY), wTile - decalageX, hTile - decalageY));
	}
	
	public Tileset(String imageName, int nbX, int nbY, int decalageX, int decalageY) {
		this(Assets.getImage(imageName), nbX, nbY, decalageX, decalageY);
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
