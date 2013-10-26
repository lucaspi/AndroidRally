package se.chalmers.dryleafsoftware.androidrally.client;

public class Controller {
	private Client client = null;
	private Connection connection = null;
	
	public Controller(){
		client = new Client();		
		connection = new Connection();
	}

	public Client getClient() {
		return client;
	}
		
	public Connection getConnection() {
		return connection;
	}

}
