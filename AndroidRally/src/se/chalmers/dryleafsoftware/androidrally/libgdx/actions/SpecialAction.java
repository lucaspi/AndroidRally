package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * 
 * 
 * @author
 * 
 */
public class SpecialAction extends GameAction{

	/**
	 * Simulates falling in a dark hole.
	 */
	public static final Action HOLE_ACTION = Actions.parallel(
			Actions.fadeOut(1), Actions.scaleTo(0.3f, 0.3f, 1));
	/**
	 * Respawns the robot.
	 */
	public static final Action RESPAWN_ACTION = Actions.parallel(
			Actions.fadeIn(1), Actions.scaleTo(1, 1, 1));
	/**
	 * Hides the robot.
	 */
	public static final Action INVISIBLE_ACTION = Actions.fadeOut(0);
	/**
	 * Makes the robot visible again.
	 */
	public static final Action VISIBLE_ACTION = Actions.fadeIn(0);
	
	private final Action action;
	private final Action instantAction;
	
	/**
	 * Creates a new instance which will run the specified action.
	 * @param robotID The ID of the robot to handle.
	 * @param action The action to display.
	 * @param instantAction The outcome of the action.
	 */
	public SpecialAction(int robotID, Action action, Action instantAction) {
		super(robotID, 1000);
		this.action = action;
		this.instantAction = instantAction;
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		robots.get(getRobotID()).addAction(action);
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		robots.get(getRobotID()).addAction(instantAction);
	}
}
