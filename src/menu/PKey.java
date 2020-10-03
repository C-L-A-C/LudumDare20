package menu;

import java.awt.event.KeyEvent;

import processing.core.PApplet;

public class PKey extends PText {
	
	private int key;
	
	public PKey(float x, float y, float w, float h, int value) {
		super(x, y, w, h, KeyEvent.getKeyText(value));
		key = value;
	}

	public PKey(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	public PKey(float x, float y, int value, PApplet p) {
		super(x, y, KeyEvent.getKeyText(value), p);
	}	

	public int getKey()
	{
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		setLabel(KeyEvent.getKeyText(this.key));
	}
	
	@Override
	public void keyStroke(char key, int keyCode)
	{
		if (isSelected())
			setKey(keyCode);
	}
	
}
