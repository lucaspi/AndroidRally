package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread{

	private Socket socket = null;
	private PrintWriter outputWriter = null;
	private BufferedReader inputReader = null;
	private Operator operator = null;
	
	/**
	 * Should always call this start() method.
	 */
	public Connection(Operator o){
		super();
		this.operator = o;
		try {
			socket = new ServerSocket(10000).accept();
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
	        	operator.interpret(inputLine);				
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
	 * @return 
	 */
	public synchronized void send(String data){
		System.out.println("Sent:" + data);
		outputWriter.println(data);
	}

}
