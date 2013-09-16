package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class CheckPoint implements BoardElement{
	private int checkPoint;
	
	public CheckPoint(int checkPoint){
		this.checkPoint = checkPoint;
	}
	
	@Override
	public void action(Robot robot) {
		robot.reachCheckPoint(checkPoint);
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
