package se.chalmers.dryleafsoftware.androidrally.server.network;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.network.Connection;
import se.chalmers.dryleafsoftware.androidrally.network.IDataHandler;
import se.chalmers.dryleafsoftware.androidrally.server.Game;

public class DataHandler implements IDataHandler{
	private int newID = 0;
	private int newGameID = 0;
	private List<Game> games = null;
	
	
	public DataHandler(List<Game> games) {
		this.games=games;
		// TODO Auto-generated constructor stub
	}

	public void handle(String data, Connection connection) {
		String clientID = data.substring(0, data.indexOf('$'));
		if (clientID.equalsIgnoreCase("-1")){
			clientID = generateNewClientID()+"";
			connection.send(clientID + "$");
		}
		
		
		//TODO check if received id is legit?
		
		
		String data2 = data.substring(data.indexOf('$')+1);
		String gameID = data2.substring(0, data2.indexOf('$'));
		
		if (gameID.equalsIgnoreCase("-1")){
			connection.send("Test, no game id");
			connection.send("Test, 1");
			if ( games.size()<1 ){
				connection.send("Test, first new game created");
				games.add(createNewGame());
				
			}
			connection.send("Test, 2");
			if( ! games.get(games.size()-1).addPlayer(clientID) ){
				connection.send("Test, new game added to games and player put into");
				Game newGame = createNewGame();
				games.add(newGame);
				newGame.addPlayer(clientID);
			}
			connection.send("Test, 3");
			connection.send(clientID + "$" + (games.get(games.size()-1)).getID() + "$");
//			//TODO

		} else {
			connection.send("Test, game id test");
			for (Game game : games){
				for (String s : game.getClients() ){
					if (s.equalsIgnoreCase(gameID)){
						connection.send("gameexists");
					}
				}
			}				
					//TODO send data

		}
			
	}
		

	private Game createNewGame() {
		Game g = new Game(generateNewGameID());
		// TODO Auto-generated method stub
		return g;
		
	}

	private int generateNewGameID() {
		newGameID++;
		// TODO Auto-generated method stub
		return newGameID;
	}

	private int generateNewClientID() {
		newID++;
		// TODO Auto-generated method stub
		return newID;
	}
	
}
