package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.shared.ACClient;
import se.chalmers.dryleafsoftware.androidrally.shared.ACConnection;
import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;



public class Client extends ACClient{
	private String gameID = "-1";
	
	public Client(ACOperator o) {
		super(o);
		setGameID(loadID());
	}


	private String loadID() {
		//TODO should load id local from file on unit
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

	@Override
	protected ACConnection generateConnection(ACOperator o) {
		Connection c = new Connection(o);
		c.start();
		return c;
	}


}
