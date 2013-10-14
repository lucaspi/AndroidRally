package se.chalmers.dryleafsoftware.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import se.chalmers.dryleafsoftware.androidrally.controller.AIRobotController;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class AIRobotControllerTest {

	@Test
	public void testMakeMove() {
		//walls on this course so no one can win
		String testMap = "yx36:06x36x36x36x36x36x36x36x36:06x36x36x36x36:06x36x36x36:26yx06xxx16xxxxx06xxxx06x18xx26yx06xx26x12x06xxxx06xxxx06xxx26yx06xxx36xxxxx06xxxx06xxx26yx06xxxxxx32xx06xxxx06xxx26yx06xxxxxxxx06x28xxx06xxx26yx06xxx42xxxxx06xxxx06x38xx26yx06xxxxxxxx06xxxx06x48xx26yx06xxxx22xxxx06xxxx06x58x88x26yx06xxxxxxxx06xxxx06x68x78x26yx06xxxxxxxx06xxxx06xxx26yx16:06x16x16x16x16x16x16x16x16:06x16x16x16x16:06x16x16x16:26";
		GameModel gm = new GameModel(2, testMap);
		AIRobotController ai = new AIRobotController(gm.getGameBoard());
		
		for(int j = 0; j < 100000; j++) {
			gm.dealCards();
			for(Robot robot: gm.getRobots()) {
				ai.makeMove(robot);
				for (int i = 0; i < 5; i++) {
					assertTrue(robot.getChosenCards()[i] != null);
				}
			}
			gm.moveRobots();
		}
	}
}
