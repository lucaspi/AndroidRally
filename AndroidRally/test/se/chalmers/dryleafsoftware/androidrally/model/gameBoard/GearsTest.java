package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GearsTest {

	@Test
	public void test() {
		Gears gearsRight = new Gears(true);
		Gears gearsLeft = new Gears(false);
		Robot robot = new Robot(5,5);
		
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		gearsRight.action(robot);
		assertTrue(robot.getDirection() == GameBoard.EAST);
		
		gearsRight.action(robot);
		assertTrue(robot.getDirection() == GameBoard.SOUTH);
		
		gearsRight.action(robot);
		assertTrue(robot.getDirection() == GameBoard.WEST);
		
		gearsRight.action(robot);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
		
		
		gearsLeft.action(robot);
		assertTrue(robot.getDirection() == GameBoard.WEST);
		
		gearsLeft.action(robot);
		assertTrue(robot.getDirection() == GameBoard.SOUTH);

		gearsLeft.action(robot);
		assertTrue(robot.getDirection() == GameBoard.EAST);

		gearsLeft.action(robot);
		assertTrue(robot.getDirection() == GameBoard.NORTH);
	}

}
