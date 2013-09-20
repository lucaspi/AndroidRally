package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * This is the class that saves the files that are sent 
 * from the server to the client
 * @author Jonatan Magnusson
 *
 */
public class SaveFile {
	private List<Robot> robots;
	private String cardsString;
	
	public SaveFile(GameModel gameModel){
		this.robots = gameModel.getRobots();
	}
	/**
	 * converts each robots card to a String
	 * @return a String representing each robot's cards
	 */
	public String cardsToString(){
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
	 * Saves the card info to a .txt file
	 */
	public void cardsToFile(){
		//TODO
	}
}
