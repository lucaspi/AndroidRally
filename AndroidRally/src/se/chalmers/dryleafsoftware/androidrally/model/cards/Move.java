package se.chalmers.dryleafsoftware.model.cards;

public class Move extends Card {
	private int nbrOfSteps;
	
	public Move(int priority, int nbrOfSteps) {
		super(priority);
		this.nbrOfSteps = nbrOfSteps;
}
	@Override
	public void action() {
		
	}

}
