package se.chalmers.dryleafsoftware.androidrally.client;

import se.chalmers.dryleafsoftware.androidrally.shared.ACClient;
import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

public class Controller {
	private Client client = null;
	private Operator operator = null;
	
	public Controller(){
		client = new Client(operator);		
		operator = new Operator(client);
	}

	public ACClient getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ACOperator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
