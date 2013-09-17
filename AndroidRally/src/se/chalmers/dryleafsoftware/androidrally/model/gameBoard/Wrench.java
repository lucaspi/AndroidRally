package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Wrench implements BoardElement{
	private int repairAmount;
	
	public Wrench(int repairAmount){
		this.repairAmount = repairAmount;
	}
	
	@Override
	public void action(Robot robot) {
		robot.damage(-repairAmount);
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
