package se.chalmers.dryleafsoftware.androidrally.model.cards;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Move extends Card {
	private int distance;
	
	public Move(int priority, int nbrOfSteps) {
		super(priority);
		this.distance = nbrOfSteps;
}
	@Override
	public void action(Robot robot) {
		robot.move(distance, robot.getDirection());
	}
}
