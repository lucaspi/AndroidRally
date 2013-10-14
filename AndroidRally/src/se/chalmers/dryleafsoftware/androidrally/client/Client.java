package se.chalmers.dryleafsoftware.androidrally.client;



public class Client {
	private Connection connection = new Connection();
	private String ID = loadID();
	private String gameID = "-1";
	private DataHandler dataHandler = new DataHandler(this);
	
	public Client(){
		dataHandler.verifyClientID();
		dataHandler.getNewGameID();
//		dataHandler.handle(connection.send(ID + "$" + gameID +"$"), connection);
	}

	private String loadID() {
		// TODO Auto-generated method stub
		//TODO should load id from file on client.
		return "-1";
	}

	public void setGameID(String s) {
		System.out.println("Game ID set to:"+s);
		this.gameID=s;		
	}
	
	public String getGameID() {
		return this.gameID;		
	}

	public Connection getConnection() {
		return connection;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String iD) {
		System.out.println("Client ID set to:" + iD);
		this.ID = iD;
	}
	

}
