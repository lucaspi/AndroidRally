package se.chalmers.dryleafsoftware.androidrally.server.network;


public class Client {
	private Connection connection;
	private int ID=-1;
	
	public Client (Connection c){
		this.connection = c;
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
}
