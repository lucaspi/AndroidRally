package se.chalmers.dryleafsoftware.androidrally.model.robots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private Card[] chosenCards;
	private int damage = 0;
	private int life = STARTING_LIFE;
	private int spawnPointX;
	private int spawnPointY;
	private int checkpoint = 0;
	
	public Robot(int startX, int startY) {
		positionX = startX;
		positionY = startY;
		newSpawnPoint();
		cards = new ArrayList<Card>();
		chosenCards = new Card[5];
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
	
	public List<Card> returnCards(){
		List<Card> returnCards= new ArrayList<Card>();
		
		returnCards.addAll(cards);
		for(Card card : chosenCards){
			returnCards.remove(card);
		}
		for(int i = 0; i < damage - 4; i++){
			returnCards.remove(chosenCards[4-i]);
			chosenCards[i] = null;
		}
		cards.clear();
		return returnCards;
	}
	
	public int getHealth(){
		return STARTING_HEALTH - damage;
	}
	
	public void damage(int damage){
		this.damage += damage;
		if (damage > STARTING_HEALTH) {
			die();
		}
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

	public int getLife() {
		return life;
	}

	public Card[] getChosenCards() {
		return chosenCards;
	}
	
	public void setChosenCards(List<Card> chosenCards){
		if(this.cards.containsAll(chosenCards)){
			for(int i = 0; i<5; i++){
				if(this.chosenCards[i] == null){
					this.chosenCards[i] = chosenCards.get(i);
				}
			}
		}
		fillEmptyCardRegisters();
	}
	
	private void fillEmptyCardRegisters(){
		Random random = new Random();
		List<Card> tempCards = new ArrayList<Card>();
		tempCards.addAll(cards);
		for(int i = 0; i<5; i++){
			if(this.chosenCards[i] == null){
				this.chosenCards[i] = tempCards.remove(random.nextInt(tempCards.size()));
			}
		}
	}
	
	public List<Card> getCards(){
		return cards;
	}
}