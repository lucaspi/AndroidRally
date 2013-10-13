package se.chalmers.dryleafsoftware.androidrally.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Helps with saving and loading from the application's private storage.
 * 
 * @author 
 *
 */
public class IOHandler {
		
	/**
	 * Path to the directory where data needed for the server part is stored.
	 */
	public static final String SERVER_DATA = "server/";
	/**
	 * Path to the directory where data needed for the client part is stored.
	 */
	public static final String CLIENT_DATA = "saves/";
	
	/**
	 * Saves the game with the specified ID. 
	 * @param saveData The data to save.
	 * @param gameID The game with the specified ID to save.
	 * @param location The location to store the data. Use static values.
	 */
	public static void save(String saveData, int gameID, String location) {		
		FileHandle file = Gdx.files.local(location + gameID);
		file.writeString(saveData, false);
		System.out.println("(Saving) Location: \"" + location + gameID + "\", " + 
				"data: \"" + saveData + "\"");
	}
	
	/**
	 * Checks if the specified game is saved.
	 * @param gameID The ID of the game to check.
	 * @return <code>true</code> if the game with the specified game is saved.
	 */
	public static boolean isSaved(int gameID) {
		int[] gameIDs = getGameIDs();
		for(int i = 0; i < gameIDs.length; i++) {
			if(gameIDs[i] == gameID) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gives all the IDs of the games saved.
	 * @return An array of all the IDs of all the games a client has saved on the phone.
	 */
	public static int[] getGameIDs() {		
		FileHandle[] files = Gdx.files.local("saves/").list();
		int[] ids = new int[files.length];
		for(int i = 0; i < files.length; i++) {
			ids[i] = Integer.parseInt(files[i].name());
			System.out.println("Stored ID: \"" + ids[i] + "\"");
		}
		return ids;
	}
	
	/**
	 * Removes the save for the specified game.
	 * @param gameID The ID of the game to remove.
	 * @param location The location to remove from. Use static values.
	 */
	public static void remove(int gameID, String location) {
		Gdx.files.local(location + gameID).delete();
	}
	
	/**
	 * Loads the data for the specified game.
	 * @param gameID The ID of the game to load.
	 * @param location The location to load from. Use static values.
	 * @return The data stored at the location.
	 */
	public static String load(int gameID, String location) {
		FileHandle file = Gdx.files.local(location + gameID);
		String data = file.readString();
		System.out.println("(Loading) Location: \"" + location + gameID + "\", " + 
				"data: \"" + data + "\"");
		return data;
	}
}
