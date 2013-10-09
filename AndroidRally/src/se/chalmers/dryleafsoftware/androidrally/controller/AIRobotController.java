package se.chalmers.dryleafsoftware.androidrally.controller;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.BoardElement;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.Hole;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class AIRobotController {
	private GameBoard gb;
			
	public AIRobotController(GameBoard gb) {
		this.gb = gb;
	}
	
	public void makeMove(Robot robot) {
		List<Card> cards = robot.getCards();
		
		for (int i = 0; i < cards.size(); i++) {
			
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
//		
		return directions;
	}
	
	private void removeDirection(List<Integer> directions, int direction){
		if(directions.size() == 1){
			directions.add(new Integer((direction + 1) % 4));
			directions.add(new Integer((direction + 3) % 4));
		}
		directions.remove(0);
	}
	
	private List<Integer> removeBadDirections(Robot robot, List<Integer> directions){
		int x = robot.getX();
		int y = robot.getY();
		for(Integer i : directions){
			if(i.intValue() == GameBoard.NORTH){
				for(int j = 0; j < 3; j++){
					if((y-j) >= 0){
						for(BoardElement boardElement : gb.getTile(x, (y-j)).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i.intValue());
								break;
							}
						}
					}else{
						removeDirection(directions, i.intValue());
						break;
					}
				}
			}else if(i.intValue() == GameBoard.EAST){
				for(int j = 0; j < 3; j++){
					if((x+j) <= gb.getWidth()){
						for(BoardElement boardElement : gb.getTile((x+j), y).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i.intValue());
								break;
							}
						}
					}else{
						removeDirection(directions, i.intValue());
						break;
					}
				}
			}else if(i.intValue() == GameBoard.SOUTH){
				for(int j = 0; j < 3; j++){
					if((y+j) <= gb.getHeight()){
						for(BoardElement boardElement : gb.getTile(x, (y+j)).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i.intValue());
								break;
							}
						}
					}else{
						removeDirection(directions, i.intValue());
						break;
					}
				}
			}else if(i.intValue() == GameBoard.WEST){
				for(int j = 0; j < 3; j++){
					if((x-j) >= 0){
						for(BoardElement boardElement : gb.getTile((x-j), y).getBoardElements()){
							if(boardElement instanceof Hole){
								removeDirection(directions, i.intValue());
								break;
							}
						}
					}else{
						removeDirection(directions, i.intValue());
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
	
	
	
	
	
	
}
