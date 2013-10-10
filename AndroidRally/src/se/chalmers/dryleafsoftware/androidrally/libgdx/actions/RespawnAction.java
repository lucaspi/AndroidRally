package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * Action for when a robot should respawn.
 * 
 * @author 
 *
 */
public class RespawnAction extends GameAction {

	/**
	 * Creates a new action which will handle the robot with the specified ID.
	 * @param robotID The ID of the robot to handle.
	 */
	public RespawnAction(int robotID) {
		super(robotID, 200);
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		robots.get(getRobotID()).setDead(false);
		robots.get(getRobotID()).addAction(Actions.parallel(
				Actions.fadeIn(getDuration() / 1000f), 
				Actions.scaleTo(1, 1, getDuration() / 1000f)));
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		RobotView robot = robots.get(getRobotID());
		robot.setDead(false);
		robot.addAction(Actions.parallel(
				Actions.fadeIn(0), 
				Actions.scaleTo(1, 1, 0)));
	}

}
