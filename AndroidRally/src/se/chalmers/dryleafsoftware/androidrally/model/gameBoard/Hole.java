package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Hole implements BoardElement{

	@Override
	public void action(Robot robot) {
		robot.die();
	}

	@Override
	public void instantAction(Robot robot) {
		robot.die();
	}

}
