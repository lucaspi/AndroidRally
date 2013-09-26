package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * A tile on a gameBoard. A tile can hold walls in any direction and
 * any number of board elements.
 * @author
 *
 */
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
	public static int GEARS = 3;
	public static int WRENCH = 4;

	private List<BoardElement> boardElements = null;
	
	/**
	 * Executes the action for every board element on the robot.
	 * @param robot the robot which every action will affect.
	 */
	public void action(Robot robot){
		GameModel.hejhej = 2;
		if(boardElements != null){
			GameModel.hejhej = 3;
			for(BoardElement boardElement : boardElements){
				boardElement.action(robot);
			}
		}
	}
	
	/**
	 * Executes the instant action for every board element on the robot.
	 * Instant actions are actions which should not wait until after a round,
	 * i.e. falling down when walking down a hole.
	 * @param robot the robot which every action will affect.
	 */
	public void instantAction(Robot robot){
		if(boardElements != null){
			for(BoardElement boardElement : boardElements){
				boardElement.instantAction(robot);
			}
		}
	}
	
	/**
	 * Return true if the tile has a north wall.
	 * @return true if the tile has a north wall.
	 */
	public boolean getNorthWall(){
		return wallNorth;
	}
	
	/**
	 * Return true if the tile has a east wall.
	 * @return true if the tile has a east wall.
	 */
	public boolean getEastWall(){
		return wallEast;
	}
	
	/**
	 * Return true if the tile has a south wall.
	 * @return true if the tile has a south wall.
	 */
	public boolean getSouthWall(){
		return wallSouth;
	}
	
	/**
	 * Return true if the tile has a west wall.
	 * @return true if the tile has a west wall.
	 */
	public boolean getWestWall(){
		return wallWest;
	}
	
	/**
	 * Add a board element to the tile.
	 * @param boardElement the board element to be added.
	 */
	public void addBoardElement(BoardElement boardElement){
		if(boardElements == null){
			boardElements = new ArrayList<BoardElement>();
		}
		if (boardElement instanceof ConveyorBelt) {
			boardElements.add(0, boardElement);
		} else {
			boardElements.add(boardElement);
		}
	}
	
	/**
	 * Set which walls should exist on the tile.
	 * @param walls use constants provided in class.
	 */
	public void setWalls(int walls){
		wallNorth = false;
		wallEast = false;
		wallSouth = false;
		wallWest = false;
		
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
