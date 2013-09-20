package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class ConveyorBeltTest {

	/**
	 * Testing 1 and 2 steps forward.
	 */
	@Test
	public void testAction() {
		ConveyorBelt conveyorBelt[] = new ConveyorBelt[]{ 
				new ConveyorBelt(1, GameBoard.NORTH),
				new ConveyorBelt(1, GameBoard.WEST),
				new ConveyorBelt(1, GameBoard.SOUTH),
				new ConveyorBelt(1, GameBoard.EAST),
				new ConveyorBelt(2, GameBoard.NORTH),
				new ConveyorBelt(2, GameBoard.WEST),
				new ConveyorBelt(2, GameBoard.SOUTH),
				new ConveyorBelt(2, GameBoard.EAST),};
		
		Robot robot = new Robot(5, 5);
		
		conveyorBelt[0].action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 4);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[1].action(robot);
		assertTrue(robot.getX() == 4);
		assertTrue(robot.getY() == 4);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[2].action(robot);
		assertTrue(robot.getX() == 4);
		assertTrue(robot.getY() == 5);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[3].action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[4].action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 3);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[5].action(robot);
		assertTrue(robot.getX() == 3);
		assertTrue(robot.getY() == 3);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[6].action(robot);
		assertTrue(robot.getX() == 3);
		assertTrue(robot.getY() == 5);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		conveyorBelt[7].action(robot);
		assertTrue(robot.getX() == 5);
		assertTrue(robot.getY() == 5);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
	}

}
