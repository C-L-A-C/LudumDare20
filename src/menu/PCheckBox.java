package menu;

import processing.core.PApplet;
import utils.Utils;

public class PCheckBox extends PComponent{
	
	private static final int[] LIGHT_COLORS = {Utils.color(200), Utils.color(0, 255, 0), Utils.color(255, 0, 0)};
	private static final int[] DARK_COLORS = {Utils.color(0), Utils.color(0, 100, 0), Utils.color(100, 0, 0)};

	protected boolean checked, light;
	
	public PCheckBox(float x, float y, float w, float h) {
		this(x, y, w, h, false);
	}
	
	public PCheckBox(float x, float y, float w, float h, boolean init) {
		this(x, y, w, h, init, true);
	}
	
	public PCheckBox(float x, float y, float w, float h, boolean init, boolean darkBackground) {
		super(x, y, w, h);
		checked = init;
		light = darkBackground;
	}

	@Override
	public void afficher(PApplet p) {
		int[] colors = light ? LIGHT_COLORS : DARK_COLORS;
		
		p.stroke(colors[0]);
		p.fill(colors[checked ? 1 : 2]);
		
		p.rectMode(PApplet.CORNER);
		p.rect(x, y, w, h);
	}
	
	public boolean getChecked()
	{
		return checked;
	}
	
	public boolean click(int x, int y)
	{
		if (contient(x, y))
		{
			checked = !checked;
			return true;
		}
		return false;
	}
	
	

}
