package se.chalmers.dryleafsoftware.androidrally.model.cards;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class MoveTest {

	@Test
	public void testAction() {
		Robot robot = new Robot(5, 5);
		
		Move move1 = new Move(0, 1);
		Move move2 = new Move(0, 2);
		Move move3 = new Move(0, 3);
		Move moveMinus1 = new Move(0, -1);
		
		Turn turn = new Turn(0, TurnType.LEFT);
		
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		
		move1.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 4);
		turn.action(robot);
		
		move1.action(robot);
		assertTrue(robot.getX() == 4);
		assertTrue(robot.getY() == 4);
		turn.action(robot);
		
		move1.action(robot);
		assertTrue(robot.getX() == 4);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		move1.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		turn.action(robot);


		move2.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 3);
		turn.action(robot);

		move2.action(robot);
		assertTrue(robot.getX() == 3);
		assertTrue(robot.getY() == 3);
		turn.action(robot);

		move2.action(robot);
		assertTrue(robot.getX() == 3);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		move2.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		
		move3.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 2);
		turn.action(robot);

		move3.action(robot);
		assertTrue(robot.getX() == 2);
		assertTrue(robot.getY() == 2);
		turn.action(robot);

		move3.action(robot);
		assertTrue(robot.getX() == 2);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		move3.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		
		moveMinus1.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 6);
		turn.action(robot);
		
		moveMinus1.action(robot);
		assertTrue(robot.getX() == 6);
		assertTrue(robot.getY() == 6);
		turn.action(robot);
		
		moveMinus1.action(robot);
		assertTrue(robot.getX() == 6);
		assertTrue(robot.getY() == 5);
		turn.action(robot);
		
		moveMinus1.action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		turn.action(robot);		
	}

}
