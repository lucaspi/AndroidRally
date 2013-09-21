package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * An interface representing any element which can be placed on a tile
 * in order to effect robots moving over or standing on it.
 * @author
 *
 */
public interface BoardElement {

	/**
	 * This method will execute an action which should take place after a robot stopped on a tile.
	 * @param robot the robot the action will affect.
	 */
	public void action(Robot robot);
	
	/**
	 * This method will execute an action which should take place directlyafter a robot walks on a tile.
	 * @param robot the robot the action will affect.
	 */
	public void instantAction(Robot robot);
}
