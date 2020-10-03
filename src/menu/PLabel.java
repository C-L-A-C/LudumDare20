package menu;

import processing.core.PApplet;
import utils.Utils;

public class PLabel extends PComponent{

	protected String label;
	protected float textSize;
	protected int color;
	protected boolean invalid;
	protected long validTime;

	
	public PLabel(float x, float y, String str, PApplet p) {
		this(x, y, computeWidth(p, 14, str), computeHeight(p, 14, str), str);
	}

	public PLabel(float x, float y, float w, float h, String str)
	{
		super(x, y, w, h);
		label = (str == null ? "" : str);
		
		textSize = 20;
		
		color = Utils.color(255, 255, 255);
		validTime = 0;
		invalid = false;
	}

	public void textSize(float s)
	{
		textSize = s;
	}
	
	public void refreshSize(PApplet p)
	{
		w = computeWidth(p, textSize, label);
		h = computeWidth(p, textSize, label);
	}
	
	public void setColor(int c)
	{
		color = c;
	}
	
	public void setInvalidFor(long dur)
	{
		invalid = true;
		validTime = System.currentTimeMillis() + dur;
	}
	
	public void setValid()
	{
		invalid = false;
	}
	
	@Override
	public void afficher(PApplet p)
	{	
		//Placement debug
		//p.rect(x + w/2, y + h/2, w, h);
		
		p.textSize(textSize);
		p.rectMode(PApplet.CENTER);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		p.noStroke();
		p.fill(invalid ? p.lerpColor(color, Utils.color(255, 0, 0), .8f): color);
		
		p.text(label, x + w/2, y + h/2 - 10, w, h);
		
		if (invalid && System.currentTimeMillis() > validTime)
		{
			invalid = false;
			validTime = 0;
		}
			
	}
	
	protected static float computeHeight(PApplet p, float size, String str) {
		p.textSize(size);
		return p.textAscent() + p.textDescent();
	}

	protected static float computeWidth(PApplet p, float size, String str) {
		p.textSize(size);
		return p.textWidth(str);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String string) {
		if (string != null)
			label = string;
	}
}
