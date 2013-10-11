package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class that loads the game from a .txt file on the server
 * @author Jonatan
 *
 */
public class GameLoader {
	private String gameString;
	private String mapString;
	private String robotsString;
	private String[] strings;
	
	public GameLoader(){
		
	}
	
	/**
	 * loads the saved game to a String
	 * @param gameID the ID of the game to be loaded
	 * @throws IOException
	 */ 
	private String loadGameString(String gameID) throws IOException{
		String fileName = gameID + ".txt";
		
		byte[] encoded = Files.readAllBytes(Paths.get(fileName));//TODO change to proper path
		return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	public void loadFile(String gameID) throws IOException{
		this.gameString = loadGameString(gameID);
		this.strings = gameString.split("#");
		
	}
}
