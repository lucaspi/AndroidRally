package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.chalmers.dryleafsoftware.androidrally.shared.ACConnection;
import se.chalmers.dryleafsoftware.androidrally.shared.ACOperator;

public class Connection extends ACConnection {

	
	public Connection(ACOperator o) {
		super(o);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Socket generateSocket() {
		Socket socket = null;
    	try {
			socket = new ServerSocket(10000).accept();
		} catch (IOException e) {
			e.printStackTrace();
			//TODO: handle exceptions
		}
		return socket;
	}


}
