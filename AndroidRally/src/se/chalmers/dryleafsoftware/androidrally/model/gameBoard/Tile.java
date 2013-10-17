package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

/**
 * A tile on a gameBoard. A tile can hold walls in any direction and any number
 * of board elements.
 * 
 * @author
 * 
 */
public class Tile {
	public static int WALL_NORTH = GameBoard.NORTH;
	public static int WALL_EAST = GameBoard.EAST;
	public static int WALL_SOUTH = GameBoard.SOUTH;
	public static int WALL_WEST = GameBoard.WEST;

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
	 * 
	 * @param robot
	 *            the robot which every action will affect.
	 */
	public void action(Robot robot) {
		if (boardElements != null) {
			for (BoardElement boardElement : boardElements) {
				boardElement.action(robot);
			}
		}
	}

	/**
	 * Executes the instant action for every board element on the robot. Instant
	 * actions are actions which should not wait until after a round, i.e.
	 * falling down when walking down a hole.
	 * 
	 * @param robot
	 *            the robot which every action will affect.
	 */
	public void instantAction(Robot robot) {
		if (boardElements != null) {
			for (BoardElement boardElement : boardElements) {
				boardElement.instantAction(robot);
			}
		}
	}

	/**
	 * Add a board element to the tile. ConveyorBelts will be first in the
	 * collection.
	 * 
	 * @param boardElement
	 *            the board element to be added.
	 */
	public void addBoardElement(BoardElement boardElement) {
		if (boardElements == null) {
			boardElements = new ArrayList<BoardElement>();
		}// ConveyorBelts should always be first
		if (boardElement instanceof ConveyorBelt) {
			boardElements.add(0, boardElement);
		} else {
			boardElements.add(boardElement);
		}
	}

	public List<BoardElement> getBoardElements() {
		return boardElements;
	}

	/**
	 * Return true if the tile has a north wall.
	 * 
	 * @return true if the tile has a north wall.
	 */
	public boolean getNorthWall() {
		return wallNorth;
	}

	/**
	 * Return true if the tile has a east wall.
	 * 
	 * @return true if the tile has a east wall.
	 */
	public boolean getEastWall() {
		return wallEast;
	}

	/**
	 * Return true if the tile has a south wall.
	 * 
	 * @return true if the tile has a south wall.
	 */
	public boolean getSouthWall() {
		return wallSouth;
	}

	/**
	 * Return true if the tile has a west wall.
	 * 
	 * @return true if the tile has a west wall.
	 */
	public boolean getWestWall() {
		return wallWest;
	}
	
	/**
	 * Set which walls should exist on the tile.
	 * 
	 * @param wall
	 *            use constants provided in GameBoard.
	 */
	public void setWall(int wall) {
		switch (wall) {
		case GameBoard.NORTH:
			wallNorth = true;
			break;
		case GameBoard.EAST:
			wallEast = true;
			break;
		case GameBoard.SOUTH:
			wallSouth = true;
			break;
		case GameBoard.WEST:
			wallWest = true;
			break;
		}
	}
}
