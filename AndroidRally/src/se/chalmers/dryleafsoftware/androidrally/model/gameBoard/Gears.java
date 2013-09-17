package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Gears implements BoardElement{
	private boolean isTurnRight = false;
	
	public Gears(boolean isTurnRight){
		this.isTurnRight = isTurnRight;
	}
	
	@Override
	public void action(Robot robot) {
		if (isTurnRight) {
			robot.turn(TurnType.RIGHT);
		} else {
			robot.turn(TurnType.LEFT);
		}
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

}
