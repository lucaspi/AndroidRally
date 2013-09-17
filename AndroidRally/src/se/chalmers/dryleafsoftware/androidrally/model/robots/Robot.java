package se.chalmers.dryleafsoftware.androidrally.model.robots;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;

public class Robot {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private static final int STARTING_HEALTH = 9;
	private static final int STARTING_LIFE = 3;
	
	private int positionX;
	private int positionY;
	private int direction = NORTH;
	private List<Card> cards;
	private int damage = 0;
	private int life = 3;
	
	
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
	
	public void addCards(List<Card> cards){
		this.cards = cards;
	}
	
	public int getHealth(){
		return STARTING_HEALTH - damage;
	}
	
	public void damage(int damage){
		this.damage += damage;
	}
	
	public void die(){
		life -= 1;
		//TODO change position
	}
	
	public void reachCheckPoint(int checkPoint){
		
	}

	public int getDirection() {
		return direction;
	}
}
