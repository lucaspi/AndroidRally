package se.chalmers.dryleafsoftware.androidrally.server.controller;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;
import se.chalmers.dryleafsoftware.androidrally.server.network.DataHandler;
import se.chalmers.dryleafsoftware.androidrally.server.network.ServerListener;

/**
 * This is the controller that manages all the functionality of the server.
 * @author Vidar Eriksson
 *
 */
public class ServerController {
	private List<GameController> gameControllers = loadGameControllers();
	private ServerListener serverListener;
	
	public ServerController(){
		serverListener = new ServerListener(new DataHandler(gameControllers));
		serverListener.start();
		
	}


	private ArrayList<GameController> loadGameControllers() {
		// TODO Should load all games from file
		return null;
	}
	public List<GameController> getGameControllers(){
		return gameControllers;
	}
	
}
