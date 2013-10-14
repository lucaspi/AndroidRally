package se.chalmers.dryleafsoftware.androidrally.client;

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
public class Connection{
	private Socket socket = null;
	private PrintWriter outputWriter = null;
	private BufferedReader inputReader = null;
	
	public Connection(){
		super(); 
		try {
			socket = new Socket("176.10.217.200", 10000);
			
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * Sends data over the connection.
	 * @param data the data to be sent over the connection.
	 */
	public String send(String data){
		String inputLine = "";
		
		//TODO clear input reader?
		if (socket.isConnected()){
			System.out.println("Sent:" + data);
			outputWriter.println(data);
	
			
			try {
				do {
					inputLine = inputReader.readLine();
					if (inputLine==null){
						break;
					}
					System.out.println("Recived:" + inputLine);
				} while (inputLine.charAt(0) == 'T');
				
			} catch (Exception e) {
				System.out.println("Error! in send");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		return inputLine;
	}
	
}
