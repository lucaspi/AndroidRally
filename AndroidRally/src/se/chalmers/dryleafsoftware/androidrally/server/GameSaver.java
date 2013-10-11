package se.chalmers.dryleafsoftware.androidrally.server;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * A class that saves the different games as .txt files on the server
 * @author Jonatan
 *
 */
public class GameSaver {
	private String map;
	private List<Robot> robots;
	private List<Card> cards;
	
	public GameSaver(GameController controller){
		this.map = controller.getMap();
		this.robots = controller.getRobots();
	}
	
	/**
	 * Saves the game as a .txt file
	 * @param gameID the ID of the game
	 * @throws FileNotFoundException 
	 */
	public void saveGameToFile(String gameID) throws FileNotFoundException{
		String fileName = gameID + ".txt";
		PrintWriter out = new PrintWriter(fileName); //TODO change to proper file path
		out.print(gameToString());
		out.close();
	}
	
	/**
	 * Converts the game to a String
	 */
	private String gameToString(){
		StringBuilder sb = new StringBuilder();
		
		//Saves the robots to the gameString
		for(Robot r : robots){
			sb.append(robotToString(r) + "#");
		}
		//Saves the map to the gameString
		sb.append(map);
		
		return sb.toString();
	}
	
	/**
	 * Represents a robot as a String
	 * @return the robot as a String
	 */
	private String robotToString(Robot robot){
		this.cards = robot.getCards();		
		StringBuilder sb = new StringBuilder();
		
		//TODO robotID
		//TODO # of checkpoints
		sb.append("" + robot.getSpawnPointX() + ":" + robot.getSpawnPointY() + ":");
		sb.append("" + robot.getDirection() + ":");
		sb.append("" + robot.getX() + ":" + robot.getY() + ":");
		for(Card card : cards){
			sb.append("" + card.getPriority() + ":");
		}
		sb.append(robot.getLife());
		sb.append(robot.getHealth());
		
		return sb.toString();
	}
}