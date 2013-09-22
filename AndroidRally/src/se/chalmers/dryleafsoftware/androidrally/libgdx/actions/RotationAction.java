package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * A RotationAction rotates a robot a to the specified amount.
 * 
 * @author
 *
 */
public class RotationAction extends GameAction {

	private final int direction;
	
	/**
	 * Creates a new instance which will rotate the robot with the specified ID.
	 * @param robotID The ID of the robot to rotate.
	 * @param direction The direction to rotate to. Note: 0=Facing north, 1=east, 2=south, 3=west.
	 */
	public RotationAction(int robotID, int direction) {
		super(robotID);
		this.direction = direction;
	}

	@Override
	public void action(List<RobotView> robots) {
		robots.get(getRobotID()).addAction(Actions.rotateTo(-direction * 90, 1));		
	}
}
