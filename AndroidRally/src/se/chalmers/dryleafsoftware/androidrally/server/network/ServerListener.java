package se.chalmers.dryleafsoftware.androidrally.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;
import se.chalmers.dryleafsoftware.androidrally.network.Connection;

public class ServerListener extends Thread{
	List<GameController> gameControllers;
	List<Client> clients = new ArrayList<Client>();
    private ServerSocket serverSocket = null;
    private DataHandler dataHandler;
    
    
    /**
     * Creates the server listener, the start() method has to be called.
     */
    private ServerListener() {
    	super();
    	try {
			serverSocket = new ServerSocket(10000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
    
    /**
     * Creates the server listener, the start() method has to be called.
     */
    public ServerListener(DataHandler h) {
    	this();
		this.dataHandler = h;
	}

	public ServerListener(List<GameController> gameControllers) {
		this();
		this.gameControllers=gameControllers;
		// TODO Auto-generated constructor stub
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
        			clients.remove(a); //FIXME Could generate bug if ArrayList doesn't close gap.
        		}
        	}
    	}
	}
    
    public List<Client> getClients(){
    	return clients;
    }

}