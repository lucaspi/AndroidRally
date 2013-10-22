package se.chalmers.dryleafsoftware.androidrally.client;

import java.net.Socket;

import se.chalmers.dryleafsoftware.androidrally.shared.ACConnection;
import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

/**
 * Handles the network connections for the server.
 * @author Vidar Eriksson
 *
 */
public class Connection extends ACConnection{

	public Connection(ACOperator operator){
		super(operator);
	}
	
	@Override
	protected Socket generateSocket() {
		Socket s = null;
		try {
			s = new Socket("176.10.217.200", 10000);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return s;
	}


}
