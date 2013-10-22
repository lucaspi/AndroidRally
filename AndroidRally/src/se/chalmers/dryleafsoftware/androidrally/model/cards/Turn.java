package se.chalmers.dryleafsoftware.androidrally.model.cards;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Turn extends the class Card.
 * {@inheritDoc}
 */
public class Turn extends Card {
	private TurnType turnType;
	
	/**
	 * Creates an instance of Turn.
	 * @param priority the priority of the card
	 * @param turnType a value from enum TurnType
 	 * @see TurnType
	 */
	public Turn(int priority, TurnType turnType) {
		super(priority);
		this.turnType = turnType;
	}
	
	/**
	 * Turns the robot the way the TurnType is
	 * set in the constructor.
	 */
	@Override
	public void action(Robot robot) {
		robot.turn(turnType);
	}
	
	public TurnType getTurn(){
		return turnType;
	}
}