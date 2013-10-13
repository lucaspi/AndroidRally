package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.network.Connection;
import se.chalmers.dryleafsoftware.androidrally.network.IDataHandler;

public class DataHandler implements IDataHandler{
	private Client client;
	
	public DataHandler(Client c) {
		this.client=c;
		// TODO Auto-generated constructor stub
	}

	public void handle(String data, Connection c) {
		try {
			if (data.contains("Test")){
				return;
			}
			
			String clientID = data.substring(0, data.indexOf('$'));
			
			if (client.getID().equalsIgnoreCase("-1") &&
					! clientID.equalsIgnoreCase("-1") &&
					! clientID.equalsIgnoreCase("0")){
				client.setID(clientID);			
			}
			
			String data2 = data.substring(data.indexOf('$')+1);
			String gameID = data2.substring(0, data2.indexOf('$'));
			
			if (client.getGameID().equalsIgnoreCase("-1") &&
					! gameID.equalsIgnoreCase("-1") &&
					! gameID.equalsIgnoreCase("0")){
				client.setGameID(gameID);
			}
			
			// TODO Auto-generated method stub
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	

}
