package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.AnimatedImage;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.MapBuilder;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * This class adds an animated image to the robot it works on.
 * 
 * @author
 * 
 */
public class AnimationAction extends GameAction {

	private final AnimatedImage animation;

	/**
	 * Creates a new action which will work on the robot the the specified ID.
	 * 
	 * @param robotID
	 *            The ID of the robot to work on.
	 * @param duration
	 *            The duration of the action.
	 * @param animation
	 *            The animation to display on the robot.
	 */
	public AnimationAction(int robotID, int duration, AnimatedImage animation) {
		super(robotID, duration);
		this.animation = animation;
	}

	@Override
	public void action(List<RobotView> robots, MapBuilder map) {
		start();
		RobotView r = robots.get(getRobotID());
		animation.setSize(r.getWidth(), r.getHeight());
		animation.setPosition(r.getX(), r.getY());
		animation.setOrigin(r.getOriginX(), r.getOriginY());
		r.getParent().addActor(animation);
	}

	@Override
	public void cleanUp(List<RobotView> robots, MapBuilder map) {
		robots.get(getRobotID()).getParent().removeActor(animation);
	}
}
