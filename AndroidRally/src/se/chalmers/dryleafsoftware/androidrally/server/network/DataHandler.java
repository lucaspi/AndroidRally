package se.chalmers.dryleafsoftware.androidrally.server.network;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.server.Game;
import se.chalmers.dryleafsoftware.androidrally.server.Switch;

public class DataHandler{
	private int newID = 0;
	private int newGameID = 0;
	private List<Game> games;
	
	
	public DataHandler(List<Game> games) {
		this.games=games;
		// TODO Auto-generated constructor stub
	}

	public void handle(String data, Connection connection) {
		String clientID = data.substring(0, data.indexOf('$'));
		if (clientID.equalsIgnoreCase("-1")){
			clientID = generateNewClientID()+"";
			connection.send(clientID + "$");
			return;
		}
		
		
		//TODO check if received id is legit?
		
		
		String data2 = data.substring(data.indexOf('$')+1);
		String gameID = data2.substring(0, data2.indexOf('$'));
		
		if (gameID.equalsIgnoreCase("-1")){
			connection.send("Test, no game id");
			
			if ( games.size()<1 || ! games.get(games.size()-1).addClient(clientID)){
				connection.send("Test, new game created");
				Game newGame = createNewGame(); 
				games.add(newGame);
				newGame.addClient(clientID);
				
			}

			connection.send(clientID + "$" + (games.get(games.size()-1)).getID() + "$");
			return;
//			//TODO

		} else {
			connection.send("Test, game id search");
			for (Game game : games){
				if (game.getID().equalsIgnoreCase(gameID)){
					connection.send("gameexists");
					for (String id : game.getClients()){
						if (id.equalsIgnoreCase(clientID)){
							Switch.handle(connection, clientID, game, data2.substring(data2.indexOf('$')+1));
							break;
						}
					}
					break;
					
				}
			}				
					//TODO send data
			return;
		}
			
	}
		

	private Game createNewGame() {
		Game g = new Game(generateNewGameID(), 30, 8, 24, 120, null);
		// TODO Auto-generated method stub
		return g;
		
	}

	private String generateNewGameID() {
		newGameID++;
		// TODO Auto-generated method stub
		return newGameID+"";
	}

	private int generateNewClientID() {
		newID++;
		// TODO Auto-generated method stub
		return newID;
	}
	
}
