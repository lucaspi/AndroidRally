package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * A MoveAction moves the robot to a specified position when called on.
 * 
 * @author
 *
 */
public class MoveAction extends GameAction {

	private final int x, y;
	
	/**
	 * Creates a new instance which will work on the robot with the specified ID.
	 * @param robotID The ID of the robot to work on.
	 * @param x The X position to move the robot to. NOTE: Array position, i.g. increments of ones.
	 * @param y The Y position to move the robot to. NOTE: Array position, i.g. increments of ones.
	 */
	public MoveAction(int robotID, int x, int y) {
		super(robotID);
		this.x = x;
		this.y = y;
	}

	@Override
	public void action(List<RobotView> robots) {
		robots.get(getRobotID()).addAction(Actions.moveTo(x * 40,  800 - (y+1) * 40, 1));
	}

}
