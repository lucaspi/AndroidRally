package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * This action changes its robot's life and damage values.
 * 
 * @author 
 *
 */
public class HealthAction extends GameAction {

	private final int damage, lives;
	
	/**
	 * Creates a new instance which will handle the robot with the specified ID.
	 * @param robotID The ID of the robot to handle.
	 * @param damage The damage to set to the robot.
	 * @param lives The number of lives to set to the robot.
	 */
	public HealthAction(int robotID, int damage, int lives) {
		super(robotID, 0);
		this.damage = damage;
		this.lives = lives;
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		// Do nothing
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		robots.get(getRobotID()).setDamage(damage);
		robots.get(getRobotID()).setLives(lives);
	}
}
