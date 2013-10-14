package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Representing a checkpoint on a game board.
 */
public class CheckPoint implements BoardElement{
	private int nbrOfCheckPoint;
	
	/**
	 * Creates a new checkpoint with the specified number.
	 * @param checkPoint the number the checkpoint will represent.
	 */
	public CheckPoint(int checkPoint){
		this.nbrOfCheckPoint = checkPoint;
	}
	
	/**
	 * Calls robot's method reachCheckPoint with the
	 * check point number given in the constructor as parameter.
	 * @see Robot
	 */
	@Override
	public void action(Robot robot) {
		robot.reachCheckPoint(nbrOfCheckPoint);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

	public int getNbrOfCheckPoint() {
		return nbrOfCheckPoint;
	}

}
