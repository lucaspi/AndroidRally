package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * A GameAction describes an action on a robot.
 * 
 * @author 
 *
 */
public abstract class GameAction {

	private final int robotID;
	
	/**
	 * Creates a new instance which will work against the robot with the specified ID.
	 * @param robotID The ID of the robot to do an action on.
	 */
	public GameAction(int robotID) {
		this.robotID = robotID;
	}
	
	/**
	 * Gives the ID of the robot to work on.
	 * @return The ID of the robot to work on.
	 */
	protected int getRobotID() {
		return this.robotID;
	}
	
	/**
	 * Does the action on the robot already specified.
	 * @param robots A list of all the robots.
	 */
	public abstract void action(List<RobotView> robots);
}
