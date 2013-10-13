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
	private List<Game> games = loadGames();
	private ServerListener serverListener;
	
	public ServerController(){
		serverListener = new ServerListener(new DataHandler(games));
		serverListener.start();
		
	}


	private ArrayList<Game> loadGames() {
		// TODO Should load all games from file
		return new ArrayList<Game>();
	}
	public List<Game> getGames(){
		return games;
	}
	
}
