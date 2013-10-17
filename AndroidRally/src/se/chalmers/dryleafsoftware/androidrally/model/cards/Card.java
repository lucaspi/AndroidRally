package se.chalmers.dryleafsoftware.androidrally.model.cards;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * Abstract class that hold the priority of a card.
 */
public abstract class Card {
	private int priority;

	/**
	 * The higher the priority value is the earlier
	 * the card will be laid in a game round.
	 * 
	 * @param priority
	 *            an int value
	 */
	public Card(int priority) {
		this.priority = priority;
	}

	/**
	 * What the card does when it's "executing".
	 * 
	 * @param robot
	 *            the robot that should do the action
	 */
	abstract public void action(Robot robot);

	public int getPriority() {
		return priority;
	}
}