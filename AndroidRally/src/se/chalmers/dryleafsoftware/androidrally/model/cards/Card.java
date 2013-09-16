package se.chalmers.dryleafsoftware.androidrally.model.cards;

public abstract class Card {
	private int priority;
	
	public Card(int priority) {
		this.priority = priority; 
	}
	abstract public void action();
}