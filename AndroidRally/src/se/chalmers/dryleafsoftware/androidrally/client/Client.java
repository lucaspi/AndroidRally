package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.shared.*;



public class Client extends ACClient{
	private String gameID = "-1";

	public Client(){
		super(new Connection(), new DataHandler());
		
		getDataHandler().handle(getConnection().send(getID()+"$"), getConnection());
	}

	private String loadID() {
		// TODO Auto-generated method stub
		//TODO should load id from file local on unit
		return "-1";
	}

	public void setGameID(String s) {
		System.out.println("Game ID set to:"+s);
		this.gameID=s;		
	}
	
	@Override
	public void setID(String iD) {
		super.setID(iD);
		System.out.println("Client ID set to:" + iD);
		
	}
	
	public String getGameID() {
		return this.gameID;		
	}


}
