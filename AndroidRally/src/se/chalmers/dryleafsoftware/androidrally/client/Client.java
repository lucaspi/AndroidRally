package se.chalmers.dryleafsoftware.androidrally.client;

import java.net.Socket;

import se.chalmers.dryleafsoftware.androidrally.network.Connection;


public class Client {
	private Connection connection;
	private int ID = -1;
	
	public Client(){
		Socket s = null;
		try {
			s = new Socket("176.10.217.200", 10000);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		this.connection = new Connection(s, new DataHandler(this));
		setID(loadID());
		
	}

	private int loadID() {
		// TODO Auto-generated method stub
		//TODO should load id from file on client.
		return -1;
	}

	public int getClientID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setClientID(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setGameID(int i) {
		// TODO Auto-generated method stub
		
	}

	public Connection getConnection() {
		return connection;
	}

	public int getID() {
		return this.ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}

}
