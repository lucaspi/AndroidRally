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
		
	public static enum Special {RESPAWN, HOLE};
	
	private final Action action;
	private final Action instantAction;
	
	/**
	 * Creates a new instance which will run the specified action.
	 * @param robotID The ID of the robot to handle.
	 * @param action The action to display.
	 * @param instantAction The outcome of the action.
	 */
	public SpecialAction(int robotID, Special special) {
		super(robotID, 1000);
		switch(special) {
		case RESPAWN:
			action = Actions.parallel(
					Actions.fadeIn(1), Actions.scaleTo(1, 1, 1));
			instantAction = Actions.fadeIn(0);
			setDuration(0);
			break;
		case HOLE:
			action = Actions.parallel(
					Actions.fadeOut(1), Actions.scaleTo(0.3f, 0.3f, 1));
			instantAction = Actions.fadeOut(0);
			break;
			default:
				action = null;
				instantAction = null;
		}
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
