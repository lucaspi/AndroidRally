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
	
	public static final String SAVENAME = "save";
	public static final String GAMEID_PATH = "gameids";
	
	public static void save(String saveData, int gameID) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		prefs.putString(SAVENAME + gameID, saveData);
		
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
	}
	
	public static int[] getGameIDs() {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		String data = prefs.getString(GAMEID_PATH);
		String[] idString = data.split(":");
		int[] id = new int[idString.length];
		for(int i = 0; i < idString.length; i++) {
			id[i] = Integer.parseInt(idString[i]);
		}
		return id;
	}
	
	public static void remove(int gameID) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		prefs.remove(SAVENAME + gameID);
	
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
	}
	
	public static String load(String loadData, int gameID) {
		Preferences prefs = Gdx.app.getPreferences("androidRally");
		return prefs.getString(SAVENAME + gameID);
	}
}
