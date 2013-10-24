package se.chalmers.dryleafsoftware.androidrally.server;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

public class Operator extends ACOperator {
	private List<Game> games = null;
	
	public Operator(Client c, List<Game> games) {
		super(c);
		this.games = games;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void verifyClientID(String data, String route) {
		String clientID = data;
		if (clientID.equalsIgnoreCase("-1")){
			clientID = generateNewClientID()+"";
			getClient().getConnection().send(clientID + "$" + route);
		} else {
			
		}
		// TODO Auto-generated method stub
		
	}

	private String generateNewClientID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateNewGame(String data, String route) {
		// TODO Auto-generated method stub
		
	}
}
