package main;

import java.util.Random;

import config.Config;
import gui.Jeu;
import gui.SceneHandler;
import utils.Utils;

public class Main {

	public static void main(String[] args) {
		
		SceneHandler.launch(new Jeu());
	}

}
