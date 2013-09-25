package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Representing a gear on the gameBoard. Will turn the robot 90 degrees when standing on it.
 * @author Hagvall 0768-500991
 *
 */
public class Gears implements BoardElement{
	private boolean isTurnRight = false;
	
	/**
	 * Creates a new gear element which will turn a robot left or right.
	 * @param isTurnRight true if the robot should be turn right, false for left.
	 */
	public Gears(boolean isTurnRight){
		this.isTurnRight = isTurnRight;
	}
	
	@Override
	public void action(Robot robot) {
		if (isTurnRight) {
			robot.turn(TurnType.RIGHT);
		} else {
			robot.turn(TurnType.LEFT);
		}
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
