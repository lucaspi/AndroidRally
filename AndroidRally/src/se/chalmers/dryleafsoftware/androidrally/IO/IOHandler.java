package se.chalmers.dryleafsoftware.androidrally.IO;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class IOHandler {
		
	/*
	 * Note: 
	 * Saved ids: [id1]:[id2]:[id3]:[id4]:
	 */
	
	public static final String SERVER_DATA = "save";
	public static final String GAMEID_PATH = "gameids";
	public static final String CLIENT_DATA = "client";
	
	/**
	 * Saves the game with the specified ID. 
	 * @param saveData The data to save.
	 * @param gameID The game with the specified ID to save.
	 */
	public static void save(String saveData, int gameID, String location) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		prefs.putString(location + gameID, saveData);
		System.out.println("Saving: " + gameID + ", " + saveData);
		
		String data = prefs.getString(GAMEID_PATH);
		String[] idString = data.split(":");
		String gameIDString = "" + gameID;
		boolean alreadySaved = false;
		for(int i = 0; i < idString.length; i++) {
			if(idString[i].equals(gameIDString)) {
				alreadySaved = true;
			}
		}
		if(!alreadySaved) {
			prefs.putString(GAMEID_PATH, data + ":" + gameID);
		}
		prefs.flush();
	}
	
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
	 * Gives all the games the client is playing. 
	 * @return An array of all the IDs of all the games a client is playing in.
	 */
	public static int[] getGameIDs() {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		String data = prefs.getString(GAMEID_PATH).substring(1);
		System.out.println("GameIDs: " + data);
		String[] idString = data.split(":");
		int[] id = new int[idString.length];
		for(int i = 0; i < idString.length; i++) {
			id[i] = Integer.parseInt(idString[i]);
		}
		return id;
	}
	
	/**
	 * Removes the data for the specified game. 
	 * @param gameID
	 */
	public static void remove(int gameID, String location) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		prefs.remove(location + gameID);
	
		String gameIDString = "" + gameID;
		String data = prefs.getString(GAMEID_PATH);
		String[] idString = data.split(":");
		List<String> idList = Arrays.asList(idString);
		for(String s : idList) {
			if(s.equals(gameIDString)) {
				idList.remove(s);
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(String s : idList) {
			sb.append(s + ":");
		}
		prefs.putString(GAMEID_PATH, sb.toString());
		
		prefs.flush();
	}
	
	/**
	 * Loads the data to the specified game. 
	 * @param gameID
	 * @return
	 */
	public static String load(int gameID, String location) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		String data = prefs.getString(location + gameID);
		System.out.println("Loaded: " + data);
		return data;
	}
}
