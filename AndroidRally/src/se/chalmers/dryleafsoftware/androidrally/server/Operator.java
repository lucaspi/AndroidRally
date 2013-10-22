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
		
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateNewGame(String data, String route) {
		// TODO Auto-generated method stub
		
	}
}
