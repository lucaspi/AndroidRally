package se.chalmers.dryleafsoftware.androidrally.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.BoardElement;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Move;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Turn;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.Hole;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class AIRobotController {
	private GameBoard gb;
	private List<Card> chosenCards;

	public AIRobotController(GameBoard gb) {
		this.gb = gb;
	}

	public void makeMove(Robot robot) {
		List<Card> cards = new ArrayList<Card>();
		chosenCards = new ArrayList<Card>();
		cards.addAll(robot.getCards());
		placeCards(robot, cards);



	}

	@SuppressWarnings("unchecked")
	private void placeCards(Robot robot, List<Card> cards) {
		if (robot.haveSentCards()) {
			return;
		}
		List<Move> moveForwardCards = new ArrayList<Move>();
		List<Move> moveBackwardCards = new ArrayList<Move>();
		List<Turn> turnCards = new ArrayList<Turn>();

		boolean isRightDirection = false;
		for (Integer direction : getDirections(robot)) { //kolla efter om roboten står i rätt riktning
			if (robot.getDirection() == direction) {
				isRightDirection = true;
			}
		}
		if (isRightDirection) {//står roboten i rätt riktning händer detta
			for (Card card : cards) {
				if (card instanceof Move) {
					if (((Move)card).getDistance() > 0) {
						moveForwardCards.add((Move)card);
					}
				}
			}
			if (moveForwardCards.size() != 0) { //ta det största "gå framåt"-kortet
				Collections.sort(moveForwardCards);
				chosenCards.add(moveForwardCards.get(0));
				cards.remove(moveForwardCards.get(0));
			} else { //har man inga "gå framåt"-kort slumpas ett kort
				randomizeCard(robot, cards);
			}
			placeCards(robot, cards);
		}
		else { //annars, försök vrida i rätt riktning
			for (Card card : cards) {
				if (card instanceof Turn) {
					turnCards.add((Turn)card);
				}
			}
			if (turnCards.size() != 0) { //om man har snurrkort
				//TODO implement
			}
			else { //har man inga snurrkort slumpas ett kort
				randomizeCard(robot, cards);
			}
			placeCards(robot, cards);
		}
	}

	private int[] nextCheckPoint(Robot robot) {
		int[] xy = new int[]{};
		for(int i = 0; i < 1; i++) {
			xy[i] = gb.getCheckPoints().get(robot.getLastCheckPoint()+1)[i]; //TODO will crash game if game is over and continues
		}
		return xy;
	}

	private int getDistanceToNextCheckPoint(Robot robot){
		int distance = 0;
		int[] nextCheckPoint = nextCheckPoint(robot);
		distance = Math.abs(robot.getX() - nextCheckPoint[0]);
		distance += Math.abs(robot.getY() - nextCheckPoint[1]);
		return distance;
	}

	private Card nextCard(Robot robot){

		return null;
	}

	private List<Integer> getDirections(Robot robot){
		List<Card> cards = robot.getCards();
		List<Integer> directions = new ArrayList<Integer>();
		int dx = getDX(robot);
		int dy = getDY(robot);
		if(dx < 0){
			directions.add(new Integer(GameBoard.EAST));
		}else if(dx > 0){
			directions.add(new Integer(GameBoard.WEST));
		}
		if(dy < 0){
			directions.add(new Integer(GameBoard.SOUTH));
		}else if(dy > 0){
			directions.add(new Integer(GameBoard.NORTH));
		}
		removeBadDirections(robot, directions);


		if(directions.size() == 2){
			if(Math.abs(dx) < Math.abs(dy)){
				directions.add(directions.remove(0));
			}
		}
		return directions;
	}

	private void removeDirection(List<Integer> directions, Integer indexToRemove){
		if(directions.size() == 1){
			directions.add(new Integer((directions.get(indexToRemove) + 1) % 4));
			directions.add(new Integer((directions.get(indexToRemove) + 3) % 4));
		}
		directions.remove(indexToRemove);
	}

	private List<Integer> removeBadDirections(Robot robot, List<Integer> directions){
		int x = robot.getX();
		int y = robot.getY();
		for(int i = 0; i < directions.size(); i++){
			if(directions.get(i).intValue() == GameBoard.NORTH){
				for(int j = 0; j < 3; j++){
					if((y-j) >= 0){
						for(BoardElement boardElement : gb.getTile(x, (y-j)).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i);
								break;
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
						for(BoardElement boardElement : gb.getTile((x+j), y).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i);
								break;
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
						for(BoardElement boardElement : gb.getTile(x, (y+j)).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i);
								break;
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
						for(BoardElement boardElement : gb.getTile((x-j), y).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i);
								break;
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

	private int getDX(Robot robot){
		return (robot.getX() - nextCheckPoint(robot)[0]);
	}

	private int getDY(Robot robot){
		return (robot.getY() - nextCheckPoint(robot)[1]);
	}


	private void randomizeCard(Robot robot, List<Card> cards) {
		Random rand = new Random();
		int index = rand.nextInt(cards.size());
		Card randChosenCard = cards.get(index);
		chosenCards.add(randChosenCard);
		cards.remove(index);
		if (chosenCards.size() == 5) {
			robot.setSentCards(true);
		}
	}



}
