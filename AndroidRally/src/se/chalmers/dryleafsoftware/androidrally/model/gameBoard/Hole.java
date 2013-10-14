package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Representing a hole on the game board. Robot standing on this board element
 * will die instantly.
 */
public class Hole implements BoardElement{

	/**
	 * Does nothing.
	 */
	@Override
	public void action(Robot robot) {
		//Do nothing
	}

	/**
	 * The given robot dies instantly. (Falls down into the hole).
	 */
	@Override
	public void instantAction(Robot robot) {
		robot.die();
		// The robot will be put out of the game until something puts him
		// on the correct position.
	}
}