package se.chalmers.dryleafsoftware.androidrally.server;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;

public class Game {
	private GameController gameController = null;
	private List<String> clientID = new ArrayList<String>();
	private int ID = -1;
	private int connectionTimer;//TODO något som räknar när denna tid är nådd och tillåter sedan clientrs to start
	private int numberOfPlayers;
	private int hoursEachRound;
	private int cardTimerSeconds;
	private String map;
	private boolean hasStarted = false;
	
	private Game(){
		super();
	}
	
	public Game(int id, int connectionTimer, int nbrOfPlayers,
			int hoursEachRound, int cardTimerSeconds, String map) {
		this();
		this.ID = id;
		this.connectionTimer=connectionTimer;
		this.numberOfPlayers=nbrOfPlayers;
		this.hoursEachRound=hoursEachRound;
		this.cardTimerSeconds=cardTimerSeconds;
		this.map=map;
	}
	

	public GameController getGameController() {
		return gameController;
	}
	/**
	 * 
	 * @return a list of the Client associated with this game.
	 */
	public List<String> getClients() {
		return clientID;
	}
	/**
	 * 
	 * @return The ID for this game.
	 */
	public int getID() {
		return ID;
	}
	/**
	 * 
	 * @return true if the client was added, otherwise false.
	 */
	public boolean addClient(String client){
		if (clientID.size()<numberOfPlayers && !hasStarted){
			clientID.add(client);
			return true;
		} else {
			return false;
		}
	}
}
