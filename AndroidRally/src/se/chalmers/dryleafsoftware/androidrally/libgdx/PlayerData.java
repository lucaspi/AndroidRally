package se.chalmers.dryleafsoftware.androidrally.libgdx;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

public class PlayerData {

	private final RobotView robot;
	private int damage;
	private int lives = 3;
	
	public PlayerData(RobotView robot) {
		this.robot = robot;
	}
	
	public RobotView getRobot() {
		return this.robot;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
