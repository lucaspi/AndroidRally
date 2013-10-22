package se.chalmers.dryleafsoftware.androidrally.server;

import java.util.ArrayList;
import java.util.List;


public class ServerListener extends Thread{
	private List<Client> clients = new ArrayList<Client>();
	private List<Game> games = null;
    
    
    /**
     * Creates the server listener, the start() method has to be called.
     */
    private ServerListener() {
    	super();

	}
    
    /**
     * Creates the server listener, the start() method has to be called.
     * @param h should be responsible for handling data.
     */
    public ServerListener(List<Game> g) {
    	this();
		this.games = g;
	}

	@Override
    public void run(){
        while (true) {
        	try {
        		Operator o = null;
        		Client c = new Client(o);
				clients.add(c);
				o = new Operator(c, games);
			} catch (Exception e) {
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