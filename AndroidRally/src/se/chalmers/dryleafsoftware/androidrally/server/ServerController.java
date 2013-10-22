package se.chalmers.dryleafsoftware.androidrally.server;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller that manages all the functionality of the server.
 * @author Vidar Eriksson
 *
 */
public class ServerController {
	private List<Game> games = loadGames();
	private ServerListener serverListener;
	
	public ServerController(){
		serverListener = new ServerListener(games);
		serverListener.start();
		
	}


	private ArrayList<Game> loadGames() {
		// TODO Should load all games from file
		return new ArrayList<Game>();
	}
	
}
