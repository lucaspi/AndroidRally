package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Representing a Conveyor belt on a gameBoard. Contains data of how a robot
 * standing on it should move.
 * 
 */
public class ConveyorBelt implements BoardElement {
	private int travelDistance;
	private int direction;

	/**
	 * Creates a new conveyor belt with the specified travelDistance and
	 * direction. The conveyor belt will not turn the robot.
	 * 
	 * @param travelDistance
	 *            the distance the robot should be moved.
	 * @param direction
	 *            the direction the robot will be moved in
	 */
	public ConveyorBelt(int travelDistance, int direction) {
		this.travelDistance = travelDistance;
		this.direction = direction;
	}

	/**
	 * The robot will only move one step every time this method is called. If
	 * the traveldistance is > 1 this method should be called several times.
	 */
	@Override
	public void action(Robot robot) {
		robot.move(1, direction);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

	/**
	 * Returns the travel distance for this conveyor belt.
	 * 
	 * @return the travel distance for this conveyor belt.
	 */
	public int getTravelDistance() {
		return travelDistance;
	}
}