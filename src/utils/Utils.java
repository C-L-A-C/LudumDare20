package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Predicate;

import processing.core.PApplet;

public class Utils {

	//Methode statiques (le random et le color de PApplet n'est pas statiques et c'est chiant)
	// Supprime la dependance a PApplet
	
	private static Random generator = new Random(System.currentTimeMillis());
	
	public static void setRandomGenerator(Random generator)
	{
		Utils.generator = generator;
	}
	
	public static double random(double a, double b)
	{
		return generator.nextDouble() * (b - a) + a;
	}
	
	public static float random(float a, float b)
	{
		return (float)(random((double) a, (double) b));
	}
	
	public static int random(int a, int b)
	{
		return (int)(random((double) a, (double) b));
	}
	
	public static <Type> Type random(Type[] array)
	{
		if (array == null || array.length == 0)
			return null;
		
		return array[random(0, array.length)];
	}
	
	public static <Type> Type random(List<Type> liste)
	{
		if (liste == null || liste.size() == 0)
			return null;
		
		return liste.get(random(0, liste.size()));
	}
	
	public static int color(int g)
	{
		return color(g, g, g);
	}
	
	public static int color (int g, int a)
	{
		return color(g, g, g, a);
	}
	
	public static int color(int r, int g, int b)
	{
		return color(r, g, b, 255);
	}
	
	public static int color(int r, int g, int b, int a)
	{
		//ARGB
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	
	//methode pour changer l'alpha d'une couleur
	public static int setAlpha(int color, int a)
	{
		return (color & 0xFFFFFF) | ((a & 0xFF) << 24);
	}
	
	public static float map(float x, float minD, float maxD, float minG, float maxG)
	{
		float coeff = (maxG - minG) / (maxD - minD);
		return (x - minD) * coeff + minG;
	}
	
	public static float constrain(float x, float min, float max)
	{
		return x < min ? min : (x > max ? max : x);
	}

	public static <T> List<T> clone(List<T> list) {
		return new ArrayList<T>(list);
	}
	
	//Importee depuis stackoverflow, indexOf avec predicat
	public static <T> int indexOf(List<T> list, Predicate<? super T> predicate) {
	    for(ListIterator<T> iter = list.listIterator(); iter.hasNext(); )
	        if(predicate.test(iter.next())) return iter.previousIndex();
	    return -1;
	}
	
	public static void drawIndicator(PApplet p, int x, int y, int number, int bad, int good, String label) {
		drawIndicator(p, x, y, number, bad, good, label, 4);
	}
	public static void drawIndicator(PApplet p, int x, int y, int number, int bad, int good, String label, int nbColors) {
		
		int c1 = Utils.color(0, 255, 0);
		int c2 = Utils.color(255, 0, 0);
		
		float amount;
		
		if (nbColors == -1)
		{
			amount = Utils.map(number, bad, good, 0, 1);
		}
		else
		{
			int step = number / ((good - bad) / nbColors);
			float amountPerStep = 1f / nbColors;
			amount = amountPerStep * step;			
		}
		
		p.fill(p.lerpColor(c2, c1, amount));
		p.text(label + " : " + number, x, y);
		
	}

	public static String formatBytes(double bytes) {
		final String[] unites = {"bytes", "KiB", "MiB", "GiB"};
		double bytesRel = bytes;
		
		int i = 0;
		for (i = 0; i < unites.length - 1 && bytesRel > 1024; i++, bytesRel /= 1024) ;
		
		String str;
		if (bytesRel < 10)
			str = String.format("%.2f", bytesRel);
		else if (bytesRel < 100)
			str = String.format("%.2f", bytesRel);
		else
			str = "" + (int) bytesRel;
		
		return str + " " + unites[i];
	}

	public static int signe(float data) {
		return data > 0 ? 1 : (data < 0 ? -1 : 0);
	}

	public static String PascalCase(String key) {
		String natural = key.replace('-', ' ').replace('_', ' ').toLowerCase();
		StringBuilder result = new StringBuilder();
		for (String word : natural.split(" "))
		{
			if (word.length() != 0)
				result.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
		}
		return result.toString();
	}
}
