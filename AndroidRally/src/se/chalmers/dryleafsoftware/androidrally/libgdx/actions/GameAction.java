package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.utils.TimeUtils;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.MapBuilder;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * A GameAction describes an action on a robot.
 * 
 * @author 
 *
 */
public abstract class GameAction {

	private final int robotID;
	private int duration;
	private long started;
	private int phaseRound = PHASE_MOVE;
	
	/**
	 * Static value specifying that a value should stay unchanged.
	 */
	public static final int UNCHANGED = -1;
			
	/**
	 * When the robot is moving. (Default)
	 */
	public static final int PHASE_MOVE = 0;
	/**
	 * When a conveyer belt is moving.
	 */
	public static final int PHASE_BOARD_ELEMENT_CONVEYER = 1;
	/**
	 * When another robot is forcing the robot to move.
	 */
	public static final int PHASE_PUSHED = 2;
	/**
	 * When the gears are moving.
	 */
	public static final int PHASE_BOARD_ELEMENT_GEARS = 4;
	/**
	 * When all the lasers on the boards is firing.
	 */
	public static final int PHASE_LASER = 5;
	/**
	 * When robots respawn.
	 */
	public static final int PHASE_RESPAWN = 7;
	/**
	 * When robot touch checkpoint
	 */
	public static final int PHASE_CHECKPOINT = 6;
	
	public static final int SPECIAL_PHASE_GAMEOVER = 101;
	public static final int SPECIAL_PHASE_WON = 102;
		
	/**
	 * Creates a new instance which will work against the robot with the specified ID.
	 * @param robotID The ID of the robot to do an action on.
	 * @param duration The duration of the action in milliseconds.
	 */
	public GameAction(int robotID, int duration) {
		this.robotID = robotID;
		this.duration = duration;
	}
	
	/**
	 * Sets the integer specifying which phase this action belongs to.
	 * @param moveRound An integer specifying what phase this action belongs to.
	 */
	public void setMoveRound(int moveRound) {
		this.phaseRound = moveRound;
	}
	
	/**
	 * Gives the integer specifying which phase this action belongs to.
	 * @return An integer specifying what phase this action belongs to.
	 */
	public int getPhase() {
		return this.phaseRound;
	}
	
	/**
	 * Gives <code>true</code> if the action is currently running.
	 * @return <code>true</code> if the action is currently running.
	 */
	public boolean isRunning() {
		return started != 0;
	}
	
	/**
	 * Gives <code>true</code> if the action is done.
	 * @return <code>true</code> if the action is done.
	 */
	public boolean isDone() {
		return started == 0 ? false : TimeUtils.millis() - started >= duration;
	}
	
	/**
	 * Gives the duration of the action in milliseconds.
	 * @return The duration of the action in milliseconds.
	 */
	public int getDuration() {
		return this.duration;
	}
	
	/**
	 * Sets the duration of the action is milliseconds.
	 * @param duration The duration of the action in milliseconds.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Gives the ID of the robot to work on.
	 * @return The ID of the robot to work on.
	 */
	protected int getRobotID() {
		return this.robotID;
	}
	
	/**
	 * Must be called internally when the action starts, otherwise
	 * the <code>isDone()</code> method will not work.
	 */
	protected void start() {
		started = TimeUtils.millis();
	}
	
	/**
	 * Does the action on the robot already specified.
	 * @param robots A list of all the robots.
	 */
	public abstract void action(List<RobotView> robots, MapBuilder map);
	
	/**
	 * Skips the animation and simply places the robot.
	 * @param robots A list of all the robots.
	 */
	public abstract void cleanUp(List<RobotView> robots, MapBuilder map);
}
