package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

/**
 * This class holds a simple matrix of the map which only specifies if there is
 * walls or other objects blocking paths.
 * 
 * @author
 * 
 */
public class CollisionMatrix {

	private final boolean[][][] walls;
	private boolean[][] dynamic;

	/**
	 * Creates a new matrix with the specified size.
	 * 
	 * @param width
	 *            The width of the matrix.
	 * @param height
	 *            The height of the matrix.
	 */
	public CollisionMatrix(int width, int height) {
		this.walls = new boolean[width][height][2];
		// 2 wall positions [0] = up, [1] = left
		clearDynamic();
	}

	/**
	 * Checks if it is possible to travel from the specified tile to the next in
	 * the specified direction.
	 * 
	 * @param x
	 *            The starting X-position.
	 * @param y
	 *            The starting Y-position.
	 * @param dir
	 *            The direction to try to travel.
	 * @return <code>true</code> if it is not possible to travel the specified
	 *         direction.
	 */
	public boolean cannotTravel(int x, int y, int dir) {
		if (isWall(x, y, dir)) {
			return true;
		}
		if (dir == MapBuilder.DIR_NORTH) {
			return isBlocked(x, y - 1);
		} else if (dir == MapBuilder.DIR_SOUTH) {
			return isBlocked(x, y + 1);
		} else if (dir == MapBuilder.DIR_WEST) {
			return isBlocked(x - 1, y);
		} else if (dir == MapBuilder.DIR_EAST) {
			return isBlocked(x + 1, y);
		} else {
			return true;
		}
	}

	/**
	 * Gives <code>true</code> if there is a wall on the specified tile.
	 * 
	 * @param x
	 *            The X-coordinate of the tile.
	 * @param y
	 *            The Y-coordinate of the tile.
	 * @param dir
	 *            The direction of the edge to check.
	 * @return <code>true</code> if there is a wall on the specified tile.
	 */
	public boolean isWall(int x, int y, int dir) {
		switch (dir) {
		case MapBuilder.DIR_NORTH:
			if (validWallPos(x, y, 0)) {
				return this.walls[x][y][0];
			}
			break;
		case MapBuilder.DIR_WEST:
			if (validWallPos(x, y, 1)) {
				return this.walls[x][y][1];
			}
			break;
		case MapBuilder.DIR_SOUTH:
			if (validWallPos(x, y + 1, 0)) {
				return this.walls[x][y + 1][0];
			}
			break;
		case MapBuilder.DIR_EAST:
			if (validWallPos(x + 1, y, 1)) {
				return this.walls[x + 1][y][1];
			}
			break;
		}
		return false;
	}

	/*
	 * Checks if the specified values are valid, i.e. checking if a value is out
	 * of bounds.
	 */
	private boolean validWallPos(int x, int y, int dir) {
		return (validPos(x, y) && dir < walls[0][0].length && dir >= 0);
	}

	/**
	 * Checks if the specified position is on the map.
	 * 
	 * @param x
	 *            The X-coordinate to check.
	 * @param y
	 *            The Y-coordinate to check.
	 * @return <code>true</code> if the position is on the map.
	 */
	public boolean validPos(int x, int y) {
		return (x < walls.length && y < walls[0].length && x >= 0 && y >= 0);
	}

	/*
	 * Checks if the specified values are valid, i.e. checking if a value is out
	 * of bounds.
	 */
	private boolean validBlockedPos(int x, int y) {
		return validPos(x, y);
	}

	/**
	 * Checks if the specified tile is blocked by a dynamic object.
	 * 
	 * @param x
	 *            The X-coordinate of the tile to check.
	 * @param y
	 *            The Y-coordinate of the tile to check.
	 * @return <code>true</code> if the specified tile is blocked by a dynamic
	 *         object.
	 */
	public boolean isBlocked(int x, int y) {
		if (validBlockedPos(x, y)) {
			return dynamic[x][y];
		} else {
			return false;
		}
	}

	/**
	 * Gives the width of the map.
	 * 
	 * @return The width of the map.
	 */
	public int getWidth() {
		return this.walls.length;
	}

	/**
	 * Gives the height of the map.
	 * 
	 * @return The height of the map.
	 */
	public int getHeight() {
		return this.walls[0].length;
	}

	/**
	 * Specifies that a dynamic object is blocking the specified tile.
	 * 
	 * @param x
	 *            The X-coordinate of the tile to block.
	 * @param y
	 *            The Y-coordinate of the tile to block.
	 */
	public void setDynamic(int x, int y) {
		if (validBlockedPos(x, y)) {
			this.dynamic[x][y] = true;
		}
	}

	/**
	 * Removes all values from the dynamic matrix. I.e. setting all values to
	 * false.
	 */
	public void clearDynamic() {
		this.dynamic = new boolean[walls.length][walls[0].length];
	}

	/**
	 * Sets a wall on the edge to the specified direction of the tile.
	 * 
	 * @param x
	 *            The X-coordinate of the tile.
	 * @param y
	 *            The Y-coordinate of the tile.
	 * @param dir
	 *            The edge to place the wall on. 0 = north, 1 = east, 2 = south,
	 *            3 = west.
	 */
	public void setWall(int x, int y, int dir) {
		switch (dir) {
		case MapBuilder.DIR_NORTH:
			if (validWallPos(x, y, 0)) {
				this.walls[x][y][0] = true;
			}
			break;
		case MapBuilder.DIR_WEST:
			if (validWallPos(x, y, 1)) {
				this.walls[x][y][1] = true;
			}
			break;
		case MapBuilder.DIR_SOUTH:
			if (validWallPos(x, y + 1, 0)) {
				this.walls[x][y + 1][0] = true;
			}
			break;
		case MapBuilder.DIR_EAST:
			if (validWallPos(x + 1, y, 1)) {
				this.walls[x + 1][y][1] = true;
			}
			break;
		}
	}
}
