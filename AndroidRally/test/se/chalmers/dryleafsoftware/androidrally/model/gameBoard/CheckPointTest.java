package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class CheckPointTest {

	@Test
	public void testAction() {
		Robot robot = new Robot(5,5);
		
		CheckPoint checkPoint1 = new CheckPoint(1);
		CheckPoint checkPoint2 = new CheckPoint(2);
		CheckPoint checkPoint3 = new CheckPoint(3);
		CheckPoint checkPoint4 = new CheckPoint(4);
		
		assertTrue(robot.getLastCheckPoint() == 0);
		
		robot.reachCheckPoint(checkPoint2.getNbrOfCheckPoint());
		assertTrue(robot.getLastCheckPoint() == 0);
		
		checkPoint1.action(robot);

	}

}
