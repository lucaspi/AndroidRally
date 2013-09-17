package se.chalmers.dryleafsoftware.androidrally.model.cards;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Turn extends Card {
	private TurnType turnType;
	
	public Turn(int priority, TurnType turnType) {
		super(priority);
		this.turnType = turnType;
	}
	
	@Override
	public void action(Robot robot) {
		robot.turn(turnType);
	}
}
