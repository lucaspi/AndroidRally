package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Representing a Conveyor belt on a gameBoard. Contains data of how a robot
 * standing on it should move.
 * @author
 *
 */
public class ConveyorBelt implements BoardElement{
	private int travelDistance;
	private int direction;
	private int turn = 0;
	
	/**
	 * The conveyor belt will not affect the robot's direction.
	 */
	public static final int NO_TURN = 0;
	
	/**
	 * The conveyor belt will make a left turn for the robot.
	 */
	public static final int TURN_LEFT = 1;
	
	/**
	 * The conveyor belt will make a right turn for the robot.
	 */
	public static final int TURN_RIGHT = 2;
	
	/**
	 * Creates a new conveyor belt with the specified travelDistance and direction.
	 * The conveyor belt will not turn the robot.
	 * @param travelDistance the distance the robot should be moved.
	 * @param direction the direction the robot will be moved in
	 */
	public ConveyorBelt(int travelDistance, int direction){
		this.travelDistance = travelDistance;
		this.direction = direction;
	}
	
	/**
	 * Creates a new conveyor belt with the specified travelDistance, direction and turn.
	 * @param travelDistance the distance the robot should be moved.
	 * @param direction the direction the robot will be moved in
	 * @param turn in which direction (or none) the robot should turn.
	 */
	public ConveyorBelt(int travelDistance, int direction, int turn){
		this(travelDistance, direction);
		this.turn = turn;
	}
	
	@Override
	public void action(Robot robot) {
		robot.move(travelDistance, direction);
		if(turn == TURN_LEFT){
			robot.turn(TurnType.LEFT);
		}else if(turn == TURN_RIGHT){
			robot.turn(TurnType.RIGHT);
		}
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

	/**
	 * Returns the travel distance for this conveyor belt.
	 * @return the travel distance for this conveyor belt.
	 */
	public int getTravelDistance() {
		return travelDistance;
	}

}
