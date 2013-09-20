package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class CheckPoint implements BoardElement{
	private int nbrOfCheckPoint;
	
	public CheckPoint(int checkPoint){
		this.nbrOfCheckPoint = checkPoint;
	}
	
	@Override
	public void action(Robot robot) {
		robot.reachCheckPoint(nbrOfCheckPoint);
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

	public int getNbrOfCheckPoint() {
		return nbrOfCheckPoint;
	}

}
