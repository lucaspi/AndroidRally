package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Move;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Turn;
import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GameModelTest {

	@Test
	public void testOneRobotPushesAnotherRobot() {
		String[][] testMap = new String[][]{
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
		};
		GameModel gm = new GameModel(2,testMap);
				
		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();
		
		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 4; i++) {
			cardList2.add(new Move(i+10,1));
		}
		cardList2.add(new Move(20,2));

		
		gm.getRobots().set(0, new Robot(0,8));
		gm.getRobots().set(1, new Robot(0,9));
		
		gm.getRobots().get(0).addCards(cardList1);
		gm.getRobots().get(1).addCards(cardList2);
		
		gm.getRobots().get(0).setChosenCards(gm.getRobots().get(0).getCards());
		gm.getRobots().get(1).setChosenCards(gm.getRobots().get(1).getCards());
		
		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 8);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 9);
		
		gm.moveRobots();
		
		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 3);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 4);
	}
	
	@Test
	public void testOneRobotPushesAnotherRobotIntoAWallFromSouth() {
		String[][] testMap = new String[][]{
				{"","","","","","","","",String.valueOf(GameBoard.NORTH)+"6",""},
				{"","","","","","","",String.valueOf(GameBoard.SOUTH)+"6","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
				{"","","","","","","","","",""},
		};
		GameModel gm = new GameModel(2,testMap);
				
		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();
		
		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 4; i++) {
			cardList2.add(new Move(i+10,1));
		}
		cardList2.add(new Move(20,2));

		
		gm.getRobots().set(0, new Robot(0,8));
		gm.getRobots().set(1, new Robot(0,9));
		
		gm.getRobots().get(0).addCards(cardList1);
		gm.getRobots().get(1).addCards(cardList2);
		
		gm.getRobots().get(0).setChosenCards(gm.getRobots().get(0).getCards());
		gm.getRobots().get(1).setChosenCards(gm.getRobots().get(1).getCards());
		
		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 8);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 9);
		
		gm.moveRobots();
		
		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 8);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 9);
	}

}
