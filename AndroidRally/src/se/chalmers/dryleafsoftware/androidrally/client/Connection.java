package se.chalmers.dryleafsoftware.androidrally.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles the network connections for the server. The IP and sockets are hard coded
 *  and should only be changed if a new server is established.
 * @author Vidar Eriksson
 *
 */
public class Connection {
	
	private Socket socket = null;
	private PrintWriter outputWriter = null;
	private BufferedReader inputReader = null;
	
	public Connection(){
		super();
		try {
			socket = new Socket("176.10.217.200", 10000);
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends data over the connection.
	 * @param data the data to be sent over the connection.
	 * @return the data recieved
	 */
	public synchronized String send(String data){
		System.out.println("Sent:" + data);
		outputWriter.println(data);
		String inputLine = null;
		try {
			inputLine = inputReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(inputLine);
		return inputLine;
	}
	
}
