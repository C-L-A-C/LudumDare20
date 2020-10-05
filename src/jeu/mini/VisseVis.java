package jeu.mini;

import java.util.ArrayList;
import java.util.List;

import graphiques.Assets;
import gui.SceneHandler;
import jeu.machine.Machine;
import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class VisseVis extends MiniJeu {
	private static final int HEIGHT_WINDOW = 480;
	private static final int WIDTH_WINDOW = 640;
	private static final int MIN_X = 100;
	private static final int MIN_Y = 100;
	private static final int HEIGHT = HEIGHT_WINDOW - 2*MIN_Y;
	private static final int WIDTH = WIDTH_WINDOW - 2*MIN_X;
	private static final int VIS_SIZE_X = 32;
	private static final int VIS_SIZE_Y = 32;
	private PImage visDevissee;
	private PImage visVissee;
	private PImage mouton;
	private boolean end;
	private List<int[]> tVis;
	private int nbVis;
	private boolean reussi;

	protected VisseVis(Machine machine) {
		super(machine);
		
		this.nbVis = 0;
		int nbObj = Utils.random(10, 20);
		this.tVis = new ArrayList<int[]> ();
		for(int i=0; i<nbObj; i++) {
			while(true) {
				int[] vis = {Utils.random(MIN_X, MIN_X+WIDTH), Utils.random(MIN_Y, MIN_Y+HEIGHT), Math.random()>0.2 ? 0 : -1}; // [0]=x [1]=y [2]=state (0=devissé, 1=vissé, -1=mouton)
				boolean ok = true;
				for(int[] v2 : tVis)
					ok = ok && !visSintersecte(vis, v2);
				if(!ok)
					continue;
				if(vis[2]==0)
					this.nbVis++;
				this.tVis.add(vis); 
				break;
			}
		}
		
		visDevissee = Assets.getImage("visup");
		visVissee = Assets.getImage("visdown");
		mouton = Assets.getImage("mouton");
		this.reussi = true;
		this.end = false;
		
		SceneHandler.preloadSound("assets/sounds/marteau.wav");
		SceneHandler.pAppletInstance.cursor(Assets.getImage("marteauup"), 10, 10);
	}
	
	private static boolean visSintersecte(int[] v1, int[] v2) {
		return Math.max(v1[0], v2[0]) < Math.min(v1[0]+VIS_SIZE_X, v2[0]+VIS_SIZE_X)
			    && Math.max(v1[1], v2[1]) < Math.min(v1[1]+VIS_SIZE_Y, v2[1]+VIS_SIZE_Y);
	}

	@Override
	public void afficher(PApplet p) {
		p.clip(50, 50, p.width-100, p.height-100);
		p.fill(128);
		p.rect(50, 50, p.width-100, p.height-100, 10);
		for(int[] vis : tVis)
			if(vis[2]==0)
				p.image(visDevissee, vis[0], vis[1]);
			else if(vis[2]==1)
				p.image(visVissee, vis[0], vis[1]);
			else
				p.image(mouton, vis[0], vis[1]);
		if(end)
			p.cursor(p.ARROW);
	}

	@Override
	public boolean evoluer() {
		return !end;
	}

	@Override
	public void mousePressed(int x, int y, int button) {
		if(button!=PApplet.LEFT)
			return;
		SceneHandler.pAppletInstance.cursor(Assets.getImage("marteaudown"), 10, 10);
		for(int[] vis : tVis) {
			if(vis[0]<x && vis[0]+VIS_SIZE_X>x && vis[1]<y && vis[1]+VIS_SIZE_Y>y) {
				if(vis[2]==0) {
					vis[2] = 1;
					SceneHandler.playSound("marteau", (float)0.4, 1, (float)0.7, false);
					nbVis--;
				} else if(vis[2]==-1) {
					this.reussi = false;
					this.end = true;
				}
				break;
			}
		}
		if(nbVis==0)
			this.end = true;
	}
	
	@Override
	public void mouseReleased(int x, int y, int button) {
		if(button!=PApplet.LEFT)
			return;
		SceneHandler.pAppletInstance.cursor(Assets.getImage("marteauup"), 10, 10);
	}

	@Override
	public boolean estReussi() {
		return this.reussi;
	}
	
}
