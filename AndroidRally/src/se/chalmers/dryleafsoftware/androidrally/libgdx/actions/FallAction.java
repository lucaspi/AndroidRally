package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.MapBuilder;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * Action for when the robot falls out of the map.
 * 
 * @author 
 *
 */
public class FallAction extends GameAction {

	/**
	 * Creates a new instance which will handle the robot with the specified ID.
	 * @param robotID The ID of the robot to handle.
	 * @param duration The duration of the action in millis.
	 */
	public FallAction(int robotID, int duration) {
		super(robotID, duration);
	}

	@Override
	public void action(List<RobotView> robots, MapBuilder map) {
		start();
		robots.get(getRobotID()).addAction(
				Actions.parallel(Actions.fadeOut(getDuration() / 1000f),
				Actions.scaleTo(0.3f, 0.3f, getDuration() / 1000f)));
	}

	@Override
	public void cleanUp(List<RobotView> robots, MapBuilder map) {
		robots.get(getRobotID()).clearActions();
		robots.get(getRobotID()).addAction(
				Actions.parallel(Actions.fadeOut(0),
				Actions.scaleTo(0.3f, 0.3f, 0)));
		robots.get(getRobotID()).setDead(true);
	}

}
