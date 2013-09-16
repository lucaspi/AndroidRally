package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Gears implements BoardElement{
	private boolean clockWise = false;
	
	public Gears(boolean clockWise){
		this.clockWise = clockWise;
	}
	
	@Override
	public void action(Robot robot) {
		if(clockWise){
			robot.turn(1);
		}else{
			robot.turn(3);
		}
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
