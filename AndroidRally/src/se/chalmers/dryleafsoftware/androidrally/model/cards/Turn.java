package se.chalmers.dryleafsoftware.androidrally.model.cards;

public class Turn extends Card {
	private TurnType turnType;
	
	public Turn(int priority, TurnType turnType) {
		super(priority);
		this.turnType = turnType;
	}
	
	@Override
	public void action() {
		
	}

}
