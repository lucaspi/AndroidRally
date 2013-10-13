package se.chalmers.dryleafsoftware.androidrally.server;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;

public class Game {
	private int ID =-1;
	private GameController gameController;
	private List<String> clientID = new ArrayList<String>();
	
	private Game(){
		super();
	}
	public Game(int id){
		this();
		this.ID = id;
	}
	
	public Game(int id, int connectionTimer, int nbrOfPlayers,
			int hoursEachRound, int cardTimerSeconds, String map) {
		this();
		this.ID = id;
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
	public void addClient(String client){
		clientID.add(client); //TODO max cap of players number
	}
	/**
	 * 
	 * @return true if the player was added, otherwise false.
	 */
	public boolean addPlayer(String ID) {
		// TODO Auto-generated method stub
		return false;
	}
}
