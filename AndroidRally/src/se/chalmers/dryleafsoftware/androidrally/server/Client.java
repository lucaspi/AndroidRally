package se.chalmers.dryleafsoftware.androidrally.server;

import se.chalmers.dryleafsoftware.androidrally.shared.ACClient;
import se.chalmers.dryleafsoftware.androidrally.shared.ACConnection;
import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

public class Client extends ACClient{

	public Client(ACOperator o) {
		super(o);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ACConnection generateConnection(ACOperator o) {
		Connection c = new Connection(o);
		c.start();
		return c;
	}

}
