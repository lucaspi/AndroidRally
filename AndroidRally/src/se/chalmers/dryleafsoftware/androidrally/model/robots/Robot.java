package se.chalmers.dryleafsoftware.androidrally.model.robots;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;

public class Robot {
	private static final int STARTING_HEALTH = 9;
	private static final int STARTING_LIFE = 3;
	
	private int positionX;
	private int positionY;
	private int direction = GameBoard.NORTH;
	private List<Card> cards;
	private int damage = 0;
	private int life = 3;
	private int spawnPointX;
	private int spawnPointY;
	private int checkpoint = 0;
	
	public Robot(int startX, int startY) {
		positionX = startX;
		positionY = startY;
		newSpawnPoint();
	}
	
	/**
	 * 
	 * @param distance
	 * @param direction use int constants in Robot class
	 */
	public void move(int distance, int direction){
		if(direction == GameBoard.NORTH){
			this.positionY -= distance;
		}else if(direction == GameBoard.EAST){
			this.positionX += distance;
		}else if(direction == GameBoard.SOUTH){
			this.positionY += distance;
		}else if(direction == GameBoard.WEST){
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
		life--;
		positionX = spawnPointX;
		positionY = spawnPointY;
	}
	
	public void newSpawnPoint(){
		spawnPointX = positionX;
		spawnPointY = positionY;
	}
	
	public void reachCheckPoint(int checkpoint){
		newSpawnPoint();
		if(checkpoint == this.checkpoint + 1){
			this.checkpoint ++;
		}
	}
	
	public int getLastCheckPoint(){
		return checkpoint;
	}
	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}

	public int getDirection() {
		return direction;
	}
}
