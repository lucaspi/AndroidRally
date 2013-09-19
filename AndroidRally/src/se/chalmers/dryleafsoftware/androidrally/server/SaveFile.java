package se.chalmers.dryleafsoftware.androidrally.server;

import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;

/**
 * This is the class that saves the files that are sent 
 * from the server to the client
 * @author Jonatan Magnusson
 *
 */
public class SaveFile {
	GameModel gameModel;
	Robot[] robots;
	String cardsString;
	
	public SaveFile(GameModel gameModel){
		this.gameModel = gameModel;
		this.robots = gameModel.getRobots();
	}
	/**
	 * converts each robots card to a String
	 * @return a String representing each robot's cards
	 */
	public String cardsToString(){
		for(int i = 0; i < robot.getCards().length; i++){
			cardsString += "" + robot.getCards().get(i).get
		}
	}

}
