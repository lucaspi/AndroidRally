package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

public class Operator extends ACOperator{

	public Operator(Client client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void verifyClientID(String data, String route) {
		getClient().setID(data);
	}

	@Override
	protected void generateNewGame(String data, String route) {
		((Client) getClient()).setGameID(data);
	}

}
