package menu;

import processing.core.PApplet;
import utils.Utils;

public class PText extends PLabel {
	
	protected String placeholder;
	private boolean selected, displayPlaceholderWhenSelected;
	
	public PText(float x, float y, String value, PApplet p) {
		super(x, y, value, p);
	}
	
	public PText(float x, float y, float w, float h) {
		this(x, y, w, h, "");
	}
	
	public PText(float x, float y, float w, float h, String value) {
		super(x, y, w, h, value);
		selected = displayPlaceholderWhenSelected = false;
	}
	
	public void setPlaceholder(String p)
	{
		if (p != null)
			placeholder = p;
	}
	
	public void displayPlaceholderSelected(boolean b)
	{
		displayPlaceholderWhenSelected = b;
	}
	
	@Override
	public void afficher(PApplet p)
	{	
		String prev = label;
		int prevC = color;
		
		if (selected)
		{
			setColor(p.lerpColor(color, Utils.color(0, 0, 255), .5f));
			if (label.isEmpty() && displayPlaceholderWhenSelected)
				setLabel(placeholder);
		}
		else if (label.isEmpty())
		{
			setColor(p.lerpColor(color, Utils.color(120), .5f));
			setLabel(placeholder);
			//refreshSize(p);
		}
		
		super.afficher(p);
		
		setLabel(prev);
		setColor(prevC);	
		//refreshSize(p);	
			
	}
	
	public boolean click(int x, int y)
	{
		boolean wasSelected = selected;
		selected = contient(x, y);
		if (selected && ! wasSelected)
			setLabel("");
		return selected;
	}
	
	@Override
	public void keyStroke(char key, int keycode)
	{
		if (!selected || key > 255)
			return;
		
		switch(key)
		{ 
			case PApplet.BACKSPACE:
				if (label.length() > 0)
					label = label.substring(0, label.length() - 1);
				break;
			case PApplet.SHIFT:
			case PApplet.CONTROL:
			case PApplet.ALT:
				break;
			default: 
				label += key;
				break;
		}
	}

	public void setSelected(boolean b) {
		selected = b;
	}

	public boolean isSelected() {
		return selected;
	}

}
