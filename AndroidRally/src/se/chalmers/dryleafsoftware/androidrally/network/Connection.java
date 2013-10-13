package se.chalmers.dryleafsoftware.androidrally.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles the network connections for the server.
 * @author Vidar Eriksson
 *
 */
public class Connection extends Thread{
	private Socket socket;
	private PrintWriter outputWriter;
	private BufferedReader inputReader;
	private IDataHandler dataHandler;
	
	private Connection(){
		super();
	}
	/**
	 * Should always call this start() method.
	 * @param s
	 * @param h
	 */
	public Connection(Socket s, IDataHandler dataHandler){
		this();
		this.socket=s;
		this.dataHandler=dataHandler;
		try {
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
    public void run(){
    	try {
	    
    		String inputLine;        
	        while ((inputLine = inputReader.readLine()) != null) {
	        	System.out.println("Recived:" + inputLine);
        		dataHandler.handle(inputLine, this);				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
        	outputWriter.close();
			inputReader.close();
			socket.close();
		} catch (Exception e) {	
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
    }
	/**
	 * Sends data over the connection.
	 * @param data the data to be sent over the connection.
	 */
	public synchronized void send(String data){
		System.out.println("Sent:" + data);
		outputWriter.println(data);	
	}
	
}
