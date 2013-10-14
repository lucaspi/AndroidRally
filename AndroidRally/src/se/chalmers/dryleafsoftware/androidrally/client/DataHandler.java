package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.client.Connection;

public class DataHandler{
	private Client client;
	
	public DataHandler(Client c) {
		this.client=c;
		// TODO Auto-generated constructor stub
	}

	public void verifyClientID() {
		String clientID = client.getConnection().send(client.getID() + "$");
		clientID = clientID.substring(0, clientID.indexOf('$'));
		if (! clientID.equalsIgnoreCase("-1") &&
				! clientID.equalsIgnoreCase("0")){
			client.setID(clientID);			
		}		
	}
	
	public void getNewGameID() {
		String data = client.getConnection().send(client.getID() + "$-1$");
		String data2 = data.substring(data.indexOf('$')+1);
		
		String gameID = data2.substring(0, data2.indexOf('$'));
		
		if (client.getGameID().equalsIgnoreCase("-1") &&
				! gameID.equalsIgnoreCase("-1") &&
				! gameID.equalsIgnoreCase("0")){
			client.setGameID(gameID);
		}		
	}

}
