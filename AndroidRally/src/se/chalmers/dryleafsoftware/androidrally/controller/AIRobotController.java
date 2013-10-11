package se.chalmers.dryleafsoftware.androidrally.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.BoardElement;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Move;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Turn;
import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.Hole;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class AIRobotController {
	private GameBoard gb;
	private List<Card> chosenCards;
	private int x;
	private int y;
	private int direction;
	private int[] nextCheckpoint;
	private List<Card> cards;
	List<Move> moveForwardCards;
	List<Turn> leftTurnCards;
	List<Turn> rightTurnCards;
	List<Turn> uTurnCards;

	public AIRobotController(GameBoard gb) {
		this.gb = gb;
	}

	public void makeMove(Robot robot) {
		cards = new ArrayList<Card>();
		moveForwardCards = new ArrayList<Move>();
		leftTurnCards = new ArrayList<Turn>();
		rightTurnCards = new ArrayList<Turn>();
		uTurnCards = new ArrayList<Turn>();
		chosenCards = new ArrayList<Card>();
		
		cards.addAll(robot.getCards());
		
		for (Card card : cards) {
			if (card instanceof Turn) {
				if(((Turn)card).getTurn() == TurnType.LEFT){
					leftTurnCards.add((Turn)card);
				}else if(((Turn)card).getTurn() == TurnType.RIGHT){
					rightTurnCards.add((Turn)card);
				}else {
					uTurnCards.add((Turn)card);
				}
			}
		}
		for (Card card : cards) {
			if (card instanceof Move) {
				if (((Move)card).getDistance() > 0) {
					moveForwardCards.add((Move)card);
				}
			}
		}
		Collections.sort(moveForwardCards);
		
		x = robot.getX();
		y = robot.getY();
		direction = robot.getDirection();
		nextCheckpoint = nextCheckPoint(robot);
		placeCards();
		robot.setChosenCards(chosenCards);
		for(int i = 0; i < cards.size(); i++){
			System.out.println("Card nr: " + i + " " + cards.get(i).getPriority());
		}
		for(int i = 0; i < chosenCards.size(); i++){
			System.out.println("ChosenCard nr: " + i + " " + chosenCards.get(i).getPriority());
			System.out.println("ChosenCard nr: " + i + " " + robot.getChosenCards()[i].getPriority());
		}
	}

	@SuppressWarnings("unchecked")
	private void placeCards() {
		System.out.println("placeCArd");
		System.out.println("placeCard " + chosenCards.size());
		if (chosenCards.size() == 5) {
			return;
		}
		if(chosenCards.size() != 0){
			changeCalculatedPosition(chosenCards.get(chosenCards.size()-1));
		}
		
		boolean isRightDirection = false;
		for (Integer direction : getDirections()) { // Check if the robot stand in a correct direction
			if (this.direction == direction) {
				isRightDirection = true;
			}
		}
		if (isRightDirection) {
			
			if (moveForwardCards.size() != 0) { // Move forward as long as possible
				if(direction == GameBoard.NORTH || direction == GameBoard.SOUTH){
					addMoveCard(getDY());
				}else{
					addMoveCard(getDX());
				}
			} else { // if there are no move forwards cards -> random card 
				randomizeCard();
			}
		} else { // If the robot is turned towards a wrong direction.
			if (rightTurnCards.size() != 0 || leftTurnCards.size() != 0 || uTurnCards.size() != 0) { // Try turn towards a correct direction
				boolean cardAdded = false;
				for(Integer i : getDirections()){
					int turnDifference = Math.abs(i.intValue() - 
							direction);
					if(turnDifference == 1){
						if(leftTurnCards.size() != 0){
							chosenCards.add(leftTurnCards.get(0));
							removeCardFromLists(leftTurnCards.get(0));
							cardAdded = true;
							break;
						}
					}else if(turnDifference == 2){
						if(uTurnCards.size() != 0){
							chosenCards.add(uTurnCards.get(0));
							removeCardFromLists(uTurnCards.get(0));
							cardAdded = true;
							break;
						}
					}else if(turnDifference == 3){
						if(rightTurnCards.size() != 0){
							chosenCards.add(rightTurnCards.get(0));
							removeCardFromLists(rightTurnCards.get(0));
							cardAdded = true;
							break;
						}
					}
				}
				if(!cardAdded){
					for(int j = 0; j<cards.size(); j++){
						if(!(cards.get(j) instanceof Move)){
							chosenCards.add(cards.get(j));
							removeCardFromLists(cards.get(j));
							cardAdded = true;
							break;
						}
					}
					if(!cardAdded){
						randomizeCard();
					}
				}
			} else { // No turn cards -> random card
				randomizeCard();
			}
		}
		placeCards();
	}
	
	private void changeCalculatedPosition(Card card){
		if(card instanceof Move){
			if(direction == GameBoard.NORTH){
				y = y - ((Move)card).getDistance();
			}else if(direction == GameBoard.EAST){
				x = x + ((Move)card).getDistance();
			}else if(direction == GameBoard.SOUTH){
				y = y + ((Move)card).getDistance();
			}else if(direction == GameBoard.WEST){
				x = x - ((Move)card).getDistance();
			}
		}else if(card instanceof Turn){
			if(((Turn)card).getTurn() == TurnType.LEFT){
				direction = (direction + 3) % 4;
			}else if(((Turn)card).getTurn() == TurnType.RIGHT){
				direction = (direction + 1) % 4;
			}else{ // UTurn
				direction = (direction + 2) % 4;
			}
		}
	}

	private int[] nextCheckPoint(Robot robot) {
		int[] xy = new int[2];
		for(int i = 0; i <= 1; i++) {
			xy[i] = gb.getCheckPoints().get(robot.getLastCheckPoint()+1)[i]; //TODO will crash game if game is over and continues
		}
		return xy;
	}

	private List<Integer> getDirections(){
		List<Integer> directions = new ArrayList<Integer>();
		int dx = getDX();
		int dy = getDY();
		if(dx < 0){
			directions.add(Integer.valueOf(GameBoard.EAST));
		}else if(dx > 0){
			directions.add(Integer.valueOf(GameBoard.WEST));
		}
		if(dy < 0){
			directions.add(Integer.valueOf(GameBoard.SOUTH));
		}else if(dy > 0){
			directions.add(Integer.valueOf(GameBoard.NORTH));
		}
		removeBadDirections(directions);


		if(directions.size() == 2){
			if(Math.abs(dx) < Math.abs(dy)){
				directions.add(directions.remove(0));
			}
		}
		return directions;
	}

	private void removeDirection(List<Integer> directions, Integer indexToRemove){
		if(directions.size() == 1){
			directions.add(Integer.valueOf((directions.get(indexToRemove) + 1) % 4));
			directions.add(Integer.valueOf((directions.get(indexToRemove) + 3) % 4));
		}
		directions.remove(indexToRemove);
	}

	private List<Integer> removeBadDirections(List<Integer> directions){
		if(x<0 || x>(gb.getWidth()-1) || y<0 || y>(gb.getHeight()-1)){
			return directions;
		}
		for(int i = 0; i < directions.size(); i++){
			if(directions.get(i).intValue() == GameBoard.NORTH){
				for(int j = 0; j < 3; j++){
					if((y-j) >= 0){
						if (gb.getTile(x, y-j).getBoardElements() != null) {
							for(BoardElement boardElement : gb.getTile(x, (y-j)).getBoardElements()){
								if(boardElement instanceof Hole){
									removeDirection(directions, i);
									break;
								}
							}
						}
					}else{
						removeDirection(directions, i);
						break;
					}
				}
			}else if(directions.get(i).intValue() == GameBoard.EAST){
				for(int j = 0; j < 3; j++){
					if((x+j) <= gb.getWidth()){
						if (gb.getTile(x+j, y).getBoardElements() != null) {
							for(BoardElement boardElement : gb.getTile((x+j), y).getBoardElements()){
								if(boardElement instanceof Hole){
									removeDirection(directions, i);
									break;
								}
							}
						}
					}else{
						removeDirection(directions, i);
						break;
					}
				}
			}else if(directions.get(i).intValue() == GameBoard.SOUTH){
				for(int j = 0; j < 3; j++){
					if((y+j) <= gb.getHeight()){
						if (gb.getTile(x, y+j).getBoardElements() != null) {
							for(BoardElement boardElement : gb.getTile(x, (y+j)).getBoardElements()){
								if(boardElement instanceof Hole){
									removeDirection(directions, i);
									break;
								}
							}
						}
					}else{
						removeDirection(directions, i);
						break;
					}
				}
			}else if(directions.get(i).intValue() == GameBoard.WEST){
				for(int j = 0; j < 3; j++){
					if((x-j) >= 0){
						if (gb.getTile(x-j, y).getBoardElements() != null) {
							for(BoardElement boardElement : gb.getTile((x-j), y).getBoardElements()){
								if(boardElement instanceof Hole){
									removeDirection(directions, i);
									break;
								}
							}
						}
					}else{
						removeDirection(directions, i);
						break;
					}
				}
			}
		}
		return directions;
	}

	private void addMoveCard(int distance){
		for(Move card : moveForwardCards){// the list is sorted with longest distance first.
			if(card.getDistance() <= distance){
				chosenCards.add(card);{
					removeCardFromLists(card);
					return;
				}
			}
		}
		randomizeCard();
	}
	
	private int getDX(){
		return (x - nextCheckpoint[0]);
	}

	private int getDY(){
		return (y - nextCheckpoint[1]);
	}

	//Removes a card from cards. It removes it from moveForwardCards and so on if they are found there
	private void removeCardFromLists(Card card) {
		cards.remove(card);
		if (card instanceof Move) {
			if (((Move)card).getDistance() > 0) {
				moveForwardCards.remove(card);
			}
		} else if (card instanceof Turn) {
			if (((Turn) card).getTurn() == TurnType.LEFT) {
				leftTurnCards.remove(card);
			} else if (((Turn) card).getTurn() == TurnType.UTURN) {
				uTurnCards.remove(card);
			} else if (((Turn) card).getTurn() == TurnType.RIGHT) {
				rightTurnCards.remove(card);
			}
		}
	}

	private void randomizeCard() {
		// TODO first turncards then low movement cards
		Random rand = new Random();
		System.out.println(cards.size() + " random " + cards.size());
		int index = rand.nextInt(cards.size());
		System.out.println(" index: " + index);
		Card randChosenCard = cards.get(index);
		chosenCards.add(randChosenCard);
		removeCardFromLists(randChosenCard);

	}

}
