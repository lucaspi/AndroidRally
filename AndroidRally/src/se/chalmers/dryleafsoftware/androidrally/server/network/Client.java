package se.chalmers.dryleafsoftware.androidrally.server.network;


public class Client {
	private Connection connection;
	private int ID=-1;
	private DataHandler dataHandler;
	
	public Client (Connection c, DataHandler h){
		this.connection = c;
		this.dataHandler = h;
	}
	public int getID() {
		return this.ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public Connection getConnection() {
		return connection;
	}
	public DataHandler getDataHandler() {
		return dataHandler;
	}

}
