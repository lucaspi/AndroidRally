package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Hole implements BoardElement{

	@Override
	public void action(Robot robot) {
		//Do nothing
	}

	@Override
	public void instantAction(Robot robot) {
		robot.die();
		// The robot will be put out of the game until something puts him
		// on the correct position.
	}
}