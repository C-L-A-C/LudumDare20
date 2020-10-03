package menu;

import config.Config;
import processing.core.PApplet;

public class PField extends PComponent {


	private PComponent champ;
	private PLabel label;
	private String type;

	public PField(float x, float y, float w, float h, String label, String dataType, String val) {
		super(x, y, w, h);
		this.label = new PLabel(x, y, w / 2, h, label);
		champ = PComponent.creerChamp(x + w / 2, y, w / 2, h, dataType, val);
		type = dataType;
	}
	
	@Override
	public void afficher(PApplet p) {
		champ.afficher(p);
		label.afficher(p);
	}
	
	@Override
	public void keyStroke(char key, int keycode)
	{
		champ.keyStroke(key, keycode);
	}
	
	@Override
	public boolean click(int x, int y)
	{
		if (champ.click(x, y))
		{
			return true;
		}
		
		return super.click(x, y);
	}

	public Object getValue() {
		switch (type)
		{
			case "Integer":
				try{
					return Integer.parseInt(((PText) champ).getLabel());
				}catch(NumberFormatException e) {}
				return null;
			case "Boolean":
				return ((PCheckBox) champ).getChecked();
			case "String":
				return ((PText) champ).getLabel();
			case "Key":
				return Config.keyToString(((PKey) champ).getKey());
		}
		
		return null;
	}

	public void setStringValue(String str) {
		if (champ instanceof PText)
			((PText) champ).setLabel(str);
		else if (champ instanceof PCheckBox)
			((PCheckBox) champ).checked = Boolean.parseBoolean(str);
		else if (champ instanceof PKey)
			((PKey) champ).setKey(Config.stringToKey(str));
		
	}

	public String getStringValue() {
		Object o = getValue();
		return o == null ? "null" : o.toString();
	}

	public void setSelected(boolean b) {
		if (champ instanceof PText)
			((PText) champ).setSelected(b);
	}

	public void setInvalidFor(int d) {
		label.setInvalidFor(d);
	}

	public void setValid() {
		label.setValid();		
	}

	public Object getLabel() {
		return label.getLabel();
	}

}
