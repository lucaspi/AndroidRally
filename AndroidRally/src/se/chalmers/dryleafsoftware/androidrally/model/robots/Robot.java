package se.chalmers.dryleafsoftware.androidrally.model.robots;

import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;

public abstract class Robot {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private int positionX;
	private int positionY;
	private int direction = NORTH;
	
	public Robot(int startX, int startY) {
		positionX = startX;
		positionY = startY;
	}
	
	/**
	 * 
	 * @param distance
	 * @param direction use int constants in Robot class
	 */
	public void move(int distance, int direction){
		if(direction == NORTH){
			this.positionY -= distance;
		}else if(direction == EAST){
			this.positionX += distance;
		}else if(direction == SOUTH){
			this.positionY += distance;
		}else if(direction == WEST){
			this.positionX -= distance;
		}
	}
	
	
	public void turn(TurnType turn){
		if (turn == TurnType.LEFT){
			direction += 3;
		} else if(turn == TurnType.RIGHT){
			direction += 1;
		} else if(turn == TurnType.UTURN){
			direction += 2;
		}
		direction %= 4;
	}
	
	public void damage(int damage){
		
	}
	
	public void die(){
		
	}
	
	public void reachCheckPoint(int checkPoint){
		
	}

	public int getDirection() {
		return direction;
	}
}
