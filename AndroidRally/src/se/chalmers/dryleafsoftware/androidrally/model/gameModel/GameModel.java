package se.chalmers.dryleafsoftware.androidrally.model.gameModel;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Deck;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GameModel {
	
	private GameBoard gameBoard;
	private List<Robot> robots;
	private Deck deck;
	
	public GameModel(int nbrOfPlayers) {
		gameBoard = new GameBoard(12, 16);
		for (int i = 0; i < nbrOfPlayers; i++) {
			robots.add(new Robot(i, 14));
		}
		deck = new Deck();
	}
	
	public void dealCards() {
		for(Robot robot : robots) {
			int health = robot.getHealth();
			List<Card> drawnCards = new ArrayList<Card>();
			for (int i = 0; i < health; i++) {
				drawnCards.add(deck.drawCard());
			}
			robot.addCards(drawnCards);
		}
	}
	
}
