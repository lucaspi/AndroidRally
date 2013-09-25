package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * This is the class that saves the files that are sent 
 * from the server to the client
 * @author Jonatan Magnusson
 *
 */
public class SaveFile {
	private String cardsString;
	private String mapString;
	
	public SaveFile(){
	}
	/**
	 * converts each robots card to a String
	 * @param robots the robots that are in play
	 * @return a String representing each robot's cards
	 */
	public String cardsToString(List<Robot> robots){
		cardsString = "";
		for(Robot robot : robots){
			for(int i = 0; i < robot.getCards().size(); i++){
				cardsString += "" + robot.getCards().get(i).getPriority() + ",";
			}
			cardsString += "\n";
		}
		return cardsString;
	}
	
	/**
	 * converts the map to a single string
	 * @param map the map the convert
	 * @return a string representing the map
	 */
	public String mapToString(String[][] map){
		mapString = "";
		for(int i = 0; i < map.length; i++){
			for(int j = 0; i < map[i].length; j++){
				mapString += map[i][j];
			}
		}
		
		return mapString;
		
	}
	
	/**
	 * Saves the card info to a .txt file
	 */
	public void cardsToFile(List<Robot> robots){
		//TODO real file directory
				File f = new File("cards.txt");
				PrintWriter pw;
				
				if(f.exists()){
					pw = new PrintWriter("cards.txt");
					pw.print(this.cardsToString(robots));
					pw.close();
				}else{
					f.mkdirs();
					f.createNewFile();
					pw = new PrintWriter("cards.txt");
					pw.print(this.cardsToString(robots));
					pw.close();
				}
	}
}
