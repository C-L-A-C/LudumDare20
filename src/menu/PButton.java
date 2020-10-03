package menu;

import processing.core.PApplet;
import processing.core.PImage;
import utils.Utils;

public class PButton extends PLabel{

	private PImage img, hoverImg;
	private boolean hoverable;
	
	public PButton(float x, float y, float w, float h, String str)
	{
		this(x, y, w, h, str, null);
	}
	
	public PButton(float x, float y, float w, float h, PImage img)
	{
		this(x, y, w, h, null, img, img);
	}
	
	public PButton(float x, float y, float w, float h, String str, PImage img)
	{
		this(x, y, w, h, str, img, img);
	}
	

	public PButton(float x, float y, float w, float h, PImage img, PImage hoverImg)
	{
		this(x, y, w, h, null, img, hoverImg);
	}
	
	public PButton(float x, float y, float w, float h, String str, PImage img, PImage hoverImg)
	{
		super(x, y, w, h, str);

		this.img = img;
		this.hoverImg = hoverImg == null ? img : hoverImg;
		
		hoverable = true;
	}
	
	public void setHoverable(boolean b)
	{
		hoverable = b;
	}
	
	public void afficher(PApplet p)
	{	
		PImage toDisplay = null;
		int prevC = color;

		if (invalid)
			setColor(Utils.color(255, 0, 0));
		
		if (hoverable && contient(p.mouseX, p.mouseY))
		{
			if (img != null)
				toDisplay = hoverImg;
			setColor(p.lerpColor(color, Utils.color(255), .5f));
		}
		else
		{
			if (img != null)
				toDisplay = img;
		}
		
		p.tint(color);

		if (toDisplay != null)
			p.image(toDisplay, x, y, w, h);
		
		if (label != null)
			super.afficher(p);
		
		setColor(prevC);
	}
}
