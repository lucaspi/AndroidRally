package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class ConveyorBelt implements BoardElement{
	private int travelDistance;
	
	public ConveyorBelt(int travelDistance){
		this.travelDistance = travelDistance;
	}
	
	@Override
	public void action(Robot robot) {
		robot.move(travelDistance);
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
