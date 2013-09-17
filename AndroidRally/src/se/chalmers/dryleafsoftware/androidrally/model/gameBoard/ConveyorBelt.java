package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class ConveyorBelt implements BoardElement{
	private int travelDistance;
	private int direction;
	
	public ConveyorBelt(int travelDistance, int direction){
		this.travelDistance = travelDistance;
		this.direction = direction;
	}
	
	@Override
	public void action(Robot robot) {
		robot.move(travelDistance, direction);
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
