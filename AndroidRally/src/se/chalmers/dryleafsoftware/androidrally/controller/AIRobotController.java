package se.chalmers.dryleafsoftware.androidrally.controller;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.GameBoard;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class AIRobotController {
	private GameBoard gb;
			
	public AIRobotController(GameBoard gb) {
		this.gb = gb;
	}
	
	public void makeMove(Robot robot) {
		List<Card> cards = robot.getCards();
		
		
		
		
	}
	
	private int[] nextCheckPoint() {
		
		return new int[]{};
	}
	
	private int[][] findPath() {
		//Linus fixar TODO
		return new int[][]{};
	}
	
	private int getDistanceToNextCheckPoint(Robot robot){
		int distance = 0;
		int[] nextCheckPoint = nextCheckPoint();
		distance = Math.abs(robot.getX() - nextCheckPoint[0]);
		distance += Math.abs(robot.getY() - nextCheckPoint[1]);
		return distance;
	}
	
	
	
	
	
	
}
