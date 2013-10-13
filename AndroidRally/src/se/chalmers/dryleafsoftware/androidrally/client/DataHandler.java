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
		if (client.getClientID()==-1){
			try {
				String newID = data.substring(0, data.indexOf('$'));
				int i = -1;
				i = Integer.parseInt(newID);
				client.setClientID(i);
			} catch (NumberFormatException e){
				e.printStackTrace();
				System.out.println("ERROR! Could not parse new ID.");
			}
			
		}
		
		if (data.substring(0, data.indexOf('$')+1).equalsIgnoreCase("0")){
			int i = -1;
			try {
				String tempData = data.substring(0, data.indexOf('$'));
				i = Integer.parseInt(data.substring(0, tempData.indexOf('$')));
				client.setGameID(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		
	}
	

}
