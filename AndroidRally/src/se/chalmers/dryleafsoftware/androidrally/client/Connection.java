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
	private Socket socket;
	private PrintWriter outputWriter;
	private BufferedReader inputReader;
	
	private Connection(){
		super();
	}
	/**
	 * Should always call this start() method.
	 * @param s
	 * @param h
	 */
	public Connection(Socket s){
		this();
		this.socket=s;
		try {
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends data over the connection.
	 * @param data the data to be sent over the connection.
	 */
	public String send(String data){
		
//		try {
//			System.out.println(inputReader.readLine());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		//TODO clear input reader?
		
		
		
		System.out.println("Sent:" + data);
		outputWriter.println(data);

		String inputLine = "";
		try {
			System.out.println("Waiting for data");
			Thread.sleep(2000);
			System.out.println("Done Waiting for data");
			try {
				inputLine = inputReader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
		} catch (Exception e) {
			System.out.println("Error!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Recived:" + inputLine);
		return inputLine;
	}
	
}
