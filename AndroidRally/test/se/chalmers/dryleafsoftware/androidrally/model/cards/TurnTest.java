package se.chalmers.dryleafsoftware.androidrally.model.cards;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class TurnTest {

	@Test
	public void testAction() {
		Robot robot = new Robot(5, 5);
		Turn leftTurn = new Turn(0, TurnType.LEFT);
		Turn rightTurn = new Turn(0, TurnType.RIGHT);
		Turn uTurn = new Turn(0, TurnType.UTURN);
		
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		leftTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.WEST);
		leftTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.SOUTH);
		leftTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.EAST);
		leftTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		uTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.SOUTH);
		uTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		rightTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.EAST);
		
		uTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.WEST);
		uTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.EAST);
		
		rightTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.SOUTH);
		rightTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.WEST);
		rightTurn.action(robot);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
	}

}
