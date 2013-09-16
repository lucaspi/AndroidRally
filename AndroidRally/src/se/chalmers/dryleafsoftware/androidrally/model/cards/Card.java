package se.chalmers.dryleafsoftware.androidrally.model.cards;

public abstract class Card {
	private int priority;
	private boolean isLocked;
	
	public Card(int priority) {
		this.priority = priority; 
		this.isLocked = false;
	}
	abstract public void action();
	
	public int getPriority() {
		return priority;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}