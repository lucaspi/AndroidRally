package se.chalmers.dryleafsoftware.androidrally.model.cards;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public abstract class Card {
	private int priority;
	
	public Card(int priority) {
		this.priority = priority; 
	}
	abstract public void action(Robot robot);
	
	public int getPriority() {
		return priority;
	}
}