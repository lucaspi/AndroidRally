package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public interface BoardElement {

	public void action(Robot robot);
	public void instantAction(Robot robot);
}
