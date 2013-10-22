package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.MapBuilder;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * This Action simple stops any other action to perform for the specified
 * duration.
 * 
 * @author
 * 
 */
public class HolderAction extends GameAction {

	/**
	 * Creates a new instance with the specified duration.
	 * 
	 * @param duration
	 *            The duration of the action, in millis.
	 */
	public HolderAction(int duration) {
		super(-1, duration);
	}

	public HolderAction(int duration, int phase) {
		this(duration);
		this.setPhase(phase);
	}

	@Override
	public void action(List<RobotView> robots, MapBuilder map) {
		start();
	}

	@Override
	public void cleanUp(List<RobotView> robots, MapBuilder map) {
		// Do nothing
	}
}
