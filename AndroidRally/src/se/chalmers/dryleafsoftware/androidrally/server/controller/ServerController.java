package se.chalmers.dryleafsoftware.androidrally.server.controller;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.server.Game;
import se.chalmers.dryleafsoftware.androidrally.server.network.DataHandler;
import se.chalmers.dryleafsoftware.androidrally.server.network.ServerListener;

/**
 * This is the controller that manages all the functionality of the server.
 * @author Vidar Eriksson
 *
 */
public class ServerController {
	private List<Game> games = loadGame();
	private ServerListener serverListener;
	
	public ServerController(){
		serverListener = new ServerListener(new DataHandler(games));
		serverListener.start();
		
	}


	private ArrayList<Game> loadGame() {
		// TODO Should load all games from file
		return null;
	}
	public List<Game> getGames(){
		return games;
	}
	
}
