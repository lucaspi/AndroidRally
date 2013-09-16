package se.chalmers.dryleafsoftware.androidrally.model.robots;

public abstract class Robot {
	private int positionX;
	private int positionY;
	private int direction;
	private Direction myDirection = Direction.EAST;
	
	public static enum Direction{NORTH, EAST, SOUTH, WEST;
		
	
		public static Direction getNextClockwise(Direction direction, int numberOfTurns){
			Direction tempDirection = direction;
			for(int i = 0; i<numberOfTurns; i++){
				if(tempDirection == Direction.NORTH){
					tempDirection = Direction.EAST;
				}else if(tempDirection == Direction.EAST){
					tempDirection = Direction.SOUTH;
				}else if(tempDirection == Direction.SOUTH){
					tempDirection = Direction.WEST;
				}else if(tempDirection == Direction.WEST){
					tempDirection = Direction.NORTH;
				}
			}
			return tempDirection;
		}
	};
	
	
	
	public void move(int distance){
		if(myDirection == Direction.NORTH){
			this.positionY = this.positionY - distance;
		}else if(myDirection == Direction.EAST){
			this.positionX = this.positionX + distance;
		}else if(myDirection == Direction.SOUTH){
			this.positionY = this.positionY + distance;
		}else if(myDirection == Direction.WEST){
			this.positionX = this.positionX - distance;
		}
		// TODO check if position is valid
	}
	
	/**
	 * The robot will turn clockwise turn*90 degrees.
	 * @param turn
	 */
	public void turn(int turn){
		if(turn%4 == 1){
			myDirection = Direction.getNextClockwise(myDirection, 1);
		}else if(turn%4 == 2){
			myDirection = Direction.getNextClockwise(myDirection, 2);
		}else if(turn%4 == 3){
			myDirection = Direction.getNextClockwise(myDirection, 3);
		}
	}
	
	public void damage(int damage){
		
	}
	
	public void die(){
		
	}
	
	public void reachCheckPoint(int checkPoint){
		
	}
}
