package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class ConveyorBelt implements BoardElement{
	private int travelDistance;
	private int direction;
	private int turn = 0;
	
	public static final int NO_TURN = 0;
	public static final int TURN_LEFT = 1;
	public static final int TURN_RIGHT = 2;
	
	public ConveyorBelt(int travelDistance, int direction){
		this.travelDistance = travelDistance;
		this.direction = direction;
	}
	
	public ConveyorBelt(int travelDistance, int direction, int turn){
		this(travelDistance, direction);
		this.turn = turn;
	}
	
	@Override
	public void action(Robot robot) {
		robot.move(travelDistance, direction);
		if(turn == TURN_LEFT){
			robot.turn(TurnType.LEFT);
		}else if(turn == TURN_RIGHT){
			robot.turn(TurnType.RIGHT);
		}
	}

	@Override
	public void instantAction(Robot robot) {
		// Do nothing
	}

	public int getTravelDistance() {
		return travelDistance;
	}

}
