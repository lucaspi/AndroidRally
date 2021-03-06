package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.MapBuilder;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * This action changes which checkpoint a robot should target.
 * 
 * @author 
 *
 */
public class CheckPointAction extends GameAction {

	private final int reachedCheckPoint;
	private final boolean finished;

	/**
	 * Creates a new instance.
	 * 
	 * @param robotID
	 *            The robot to handle.
	 * @param reached
	 *            The checkpoint the robot just reached. Support UNCHANGED.
	 * @param finished
	 *            Set to <code>true</code> if the reached checkpoint was the
	 *            last.
	 */
	public CheckPointAction(int robotID, int reached, boolean finished) {
		super(robotID, 0);
		this.reachedCheckPoint = reached;
		this.finished = finished;
	}

	@Override
	public void action(List<RobotView> robots, MapBuilder map) {
		start();
		// All done in cleanup...
	}

	@Override
	public void cleanUp(List<RobotView> robots, MapBuilder map) {
		if (reachedCheckPoint != UNCHANGED) {
			robots.get(getRobotID()).setReachedCheckPoint(reachedCheckPoint);
		}
		if (finished) {
			robots.get(getRobotID()).setHasFinished(true);
		}
	}

}
