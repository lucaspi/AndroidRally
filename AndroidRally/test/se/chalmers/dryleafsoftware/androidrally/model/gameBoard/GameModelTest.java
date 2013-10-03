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
	public void testOneRobotWalksManySteps() {
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
//		GameModel gm = new GameModel(4,testMap);
		GameModel gm = new GameModel(null, 4,"");

		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();
		List<Card> cardList3 = new ArrayList<Card>();
		List<Card> cardList4 = new ArrayList<Card>();

		cardList1.add(new Turn(100,TurnType.UTURN));
		cardList1.add(new Move(110,3));
		cardList1.add(new Move(120,1));
		cardList1.add(new Move(130,2));
		cardList1.add(new Move(140,-1));

		cardList2.add(new Move(200,2));
		cardList2.add(new Move(210,3));
		cardList2.add(new Move(220,1));
		cardList2.add(new Move(230,-1));
		cardList2.add(new Move(240,-1));

		cardList3.add(new Turn(300,TurnType.LEFT));
		cardList3.add(new Move(310,1));
		cardList3.add(new Move(320,2));
		cardList3.add(new Move(330,3));
		cardList3.add(new Move(340,-1));

		cardList4.add(new Turn(400,TurnType.RIGHT));
		cardList4.add(new Move(410,1));
		cardList4.add(new Move(420,2));
		cardList4.add(new Move(430,3));
		cardList4.add(new Move(440,-1));


		gm.getRobots().set(0, new Robot(0,0));
		gm.getRobots().set(1, new Robot(9,9));
		gm.getRobots().set(2, new Robot(7,3));
		gm.getRobots().set(3, new Robot(2,7));

		gm.getRobots().get(0).addCards(cardList1);
		gm.getRobots().get(1).addCards(cardList2);
		gm.getRobots().get(2).addCards(cardList3);
		gm.getRobots().get(3).addCards(cardList4);

		for (int i = 0; i < 4; i++) {
			gm.getRobots().get(i).setChosenCards(gm.getRobots().get(i).getCards());
		}

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 0);
		assertTrue(gm.getRobots().get(1).getX() == 9);
		assertTrue(gm.getRobots().get(1).getY() == 9);
		assertTrue(gm.getRobots().get(2).getX() == 7);
		assertTrue(gm.getRobots().get(2).getY() == 3);
		assertTrue(gm.getRobots().get(3).getX() == 2);
		assertTrue(gm.getRobots().get(3).getY() == 7);

		gm.moveRobots();

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 5);
		assertTrue(gm.getRobots().get(0).getDirection() == GameBoard.SOUTH);
		assertTrue(gm.getRobots().get(1).getX() == 9);
		assertTrue(gm.getRobots().get(1).getY() == 5);
		assertTrue(gm.getRobots().get(1).getDirection() == GameBoard.NORTH);
		assertTrue(gm.getRobots().get(2).getX() == 2);
		assertTrue(gm.getRobots().get(2).getY() == 3);
		assertTrue(gm.getRobots().get(2).getDirection() == GameBoard.WEST);
		assertTrue(gm.getRobots().get(3).getX() == 7);
		assertTrue(gm.getRobots().get(3).getY() == 7);
		assertTrue(gm.getRobots().get(3).getDirection() == GameBoard.EAST);
	}

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
//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");

		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 4; i++) {
			cardList2.add(new Move(i+10,1));
		}
		cardList2.add(new Move(20,1));


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
	public void testOneRobotPushesAnotherRobot2Steps() {
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
//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");

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
		assertTrue(gm.getRobots().get(0).getY() == 2);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 3);
	}

	@Test
	public void testOneRobotPushesAnotherRobot3Steps() {
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
//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");

		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 4; i++) {
			cardList2.add(new Move(i+10,1));
		}
		cardList2.add(new Move(20,3));


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
		assertTrue(gm.getRobots().get(0).getY() == 1);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 2);
	}

	@Test
	public void testOneRobotPushesAnotherRobotBackwards() {
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
//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");

		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 5; i++) {
			cardList2.add(new Move(i+10,-1));
		}


		gm.getRobots().set(0, new Robot(0,8));
		gm.getRobots().set(1, new Robot(0,9));

		gm.getRobots().get(1).turn(TurnType.UTURN);

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
//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");

		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		for(int i = 0; i < 5; i++) {
			cardList1.add(new Turn(i+1,TurnType.LEFT));
		}
		for(int i = 0; i < 4; i++) {
			cardList2.add(new Move(i+10,1));
		}
		cardList2.add(new Move(20,1));


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

	@Test
	public void testRobotPushesAnotherRobotOutsideMap(){
		String[][] testMap = {{"","",""},
				{"","",""}};

//		GameModel gm = new GameModel(2,testMap);
		GameModel gm = new GameModel(null, 2,"");
		gm.getRobots().set(0, new Robot(0,2));
		gm.getRobots().set(1, new Robot(1,1));
		gm.getRobots().get(1).move(1, GameBoard.WEST);


		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		cardList1.add(new Move(500,1));
		cardList1.add(new Move(510,1));

		for(int i = 0; i < 3; i++) {
			cardList1.add(new Turn(i+10,TurnType.LEFT));
		}
		for(int i = 0; i < 5; i++) {
			cardList2.add(new Turn(i+1,TurnType.LEFT));
		}

		gm.getRobots().get(0).addCards(cardList1);
		gm.getRobots().get(1).addCards(cardList2);

		gm.getRobots().get(0).setChosenCards(cardList1);
		gm.getRobots().get(1).setChosenCards(cardList2);

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 2);
		assertTrue(gm.getRobots().get(1).getX() == 0);
		assertTrue(gm.getRobots().get(1).getY() == 1);
		assertTrue(gm.getRobots().get(0).getLife() == 3);
		assertTrue(gm.getRobots().get(1).getLife() == 3);

		gm.moveRobots();


		System.out.println("Knuffare x" + gm.getRobots().get(0).getX()); //TODO remove syso
		System.out.println("Knuffare y" + gm.getRobots().get(0).getY());
		System.out.println("Knuffad x" + gm.getRobots().get(1).getX());
		System.out.println("Knuffad y" + gm.getRobots().get(1).getY());
		System.out.println("Stod kvar life: " + gm.getRobots().get(0).getLife());
		System.out.println("Föll ner life: " + gm.getRobots().get(1).getLife());

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 0);
		assertTrue(gm.getRobots().get(1).getX() == 1);
		assertTrue(gm.getRobots().get(1).getY() == 1);
		assertTrue(gm.getRobots().get(0).getLife() == 3);
		assertTrue(gm.getRobots().get(1).getLife() == 2);

	}

	@Test
	public void testRobotWalksOutsideMap() {
		String[][] testMap = {{"","",""},
							  {"","",""}};

//		GameModel gm = new GameModel(2,testMap);
//		GameModel gm = new GameModel(2, "");
		GameModel gm = new GameModel(null, 2,"");
		gm.getRobots().set(0, new Robot(0,1));
		gm.getRobots().set(1, new Robot(1,1));
		gm.getRobots().get(0).move(1, GameBoard.NORTH);


		List<Card> cardList1 = new ArrayList<Card>();
		List<Card> cardList2 = new ArrayList<Card>();

		cardList1.add(new Move(500,1));
		for(int i = 0; i < 4; i++) {
			cardList1.add(new Turn(i+10,TurnType.LEFT));
		}
		for(int i = 0; i < 5; i++) {
			cardList2.add(new Turn(i+1,TurnType.LEFT));
		}

		gm.getRobots().get(0).addCards(cardList1);
		gm.getRobots().get(1).addCards(cardList2);

		gm.getRobots().get(0).setChosenCards(cardList1);
		gm.getRobots().get(1).setChosenCards(cardList2);

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 0);
		assertTrue(gm.getRobots().get(1).getX() == 1);
		assertTrue(gm.getRobots().get(1).getY() == 1);
		assertTrue(gm.getRobots().get(0).getLife() == 3);
		assertTrue(gm.getRobots().get(1).getLife() == 3);

		gm.moveRobots();

		System.out.println(); //TODO remove syso
		System.out.println("Han som hoppat ut x" + gm.getRobots().get(0).getX()); //TODO remove syso
		System.out.println("Han som hoppat ut y" + gm.getRobots().get(0).getY());
		System.out.println("Stod kvar x" + gm.getRobots().get(1).getX());
		System.out.println("Stod kvar y" + gm.getRobots().get(1).getY());
		System.out.println("Han som hoppat ut life: " + gm.getRobots().get(0).getLife());
		System.out.println("Stod kvar life: " + gm.getRobots().get(1).getLife());

		assertTrue(gm.getRobots().get(0).getX() == 0);
		assertTrue(gm.getRobots().get(0).getY() == 1);
		assertTrue(gm.getRobots().get(1).getX() == 1);
		assertTrue(gm.getRobots().get(1).getY() == 1);
		assertTrue(gm.getRobots().get(0).getLife() == 2);
		assertTrue(gm.getRobots().get(1).getLife() == 3);
	}
}
