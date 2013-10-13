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
		if (data.contains("Test")){
			return;
		}
		if (client.getID().equalsIgnoreCase("-1")){
			String newID = data.substring(0, data.indexOf('$'));
			if ( ! newID.equalsIgnoreCase("-1") && ! newID.equalsIgnoreCase("0")){
				client.setID(newID);
			}			
		}
		
		
		
		// TODO Auto-generated method stub
		
	}
	

}
