package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import java.util.Random;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class Tile {
	public static int WALL_NONE = 0;
	public static int WALL_NORTH = 1;
	public static int WALL_EAST = 2;
	public static int WALL_SOUTH = 4;
	public static int WALL_WEST = 8;
	
	private boolean wallNorth = false;
	private boolean wallEast = false;
	private boolean wallSouth = false;
	private boolean wallWest = false;
	
	public static int BOARD_ELEMENT_NONE = 0;
	public static int CONVEYOR_BELT = 1;
	public static int HOLE = 2;
	public static int WRENCH = 4;
	
	private BoardElement[] boardElements = null;
	
	public Tile(int walls, BoardElement[] boardElements){
		this.boardElements = boardElements;
		setWalls(walls);
	}
	
	
	public void action(Robot robot){
		if(boardElements != null){
			for(BoardElement e : boardElements){
				e.action(robot);
			}
		}
	}
	
	public void instantAction(Robot robot){
		if(boardElements != null){
			for(BoardElement e : boardElements){
				e.instantAction(robot);
			}
		}
	}
	
	public boolean getNorthWall(){
		return wallNorth;
	}
	
	public boolean getEastWall(){
		return wallEast;
	}
	
	public boolean getSouthWall(){
		return wallSouth;
	}
	
	public boolean getWestWall(){
		return wallWest;
	}
	
	private void setWalls(int walls){
		
		switch(walls){
		case 1:
			wallNorth = true;
			break;
		case 2:
			wallEast = true;
			break;
		case 3:
			wallNorth = true;
			wallEast = true;
			break;
		case 4:
			wallSouth = true;
			break;
		case 5:
			wallSouth = true;
			wallNorth = true;
			break;
		case 6:
			wallSouth = true;
			wallEast = true;
			break;
		case 7:
			wallSouth = true;
			wallEast = true;
			wallNorth = true;
			break;
		case 8:
			wallWest = true;
			break;
		case 9:
			wallWest = true;
			wallNorth = true;
			break;
		case 10:
			wallWest = true;
			wallEast = true;
			break;
		case 11:
			wallWest = true;
			wallEast = true;
			wallNorth = true;
			break;
		case 12:
			wallWest = true;
			wallSouth = true;
			break;
		case 13:
			wallWest = true;
			wallSouth = true;
			wallNorth = true;
			break;
		case 14:
			wallWest = true;
			wallSouth = true;
			wallEast = true;
			break;
		case 15:
			wallWest = true;
			wallSouth = true;
			wallEast = true;
			wallNorth = true;
			break;
		}
	}
}
