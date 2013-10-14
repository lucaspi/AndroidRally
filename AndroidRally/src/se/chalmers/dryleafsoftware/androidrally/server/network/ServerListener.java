package se.chalmers.dryleafsoftware.androidrally.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


public class ServerListener extends Thread{
	private List<Client> clients = new ArrayList<Client>();
    private ServerSocket serverSocket = null;
    private DataHandler dataHandler;
    
    
    /**
     * Creates the server listener, the start() method has to be called.
     */
    private ServerListener() {
    	super();
    	try {
			serverSocket = new ServerSocket(10000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Creates the server listener, the start() method has to be called.
     * @param h should be responsible for handling data.
     */
    public ServerListener(DataHandler handler) {
    	this();
		this.dataHandler = handler;
	}

	@Override
    public void run(){
        while (true) {
        	try {
        		Connection c = new Connection(serverSocket.accept(), dataHandler);
				clients.add(new Client(c));
				c.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	for (int a=0; a < clients.size(); ) {
        		if( clients.get(a).getConnection().isAlive()){
        			a++;
        		} else{
        			clients.remove(a);
        		}
        	}
    	}
	}

}