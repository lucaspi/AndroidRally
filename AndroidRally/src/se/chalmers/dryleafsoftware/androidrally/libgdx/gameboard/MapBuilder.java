package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.math.Vector2;

/**
 * This class helps with the loading of a map.
 * 
 * @author
 * 
 */
public abstract class MapBuilder {

	private int width, height;

	/**
	 * ID numbers for all the implemented board elements.
	 */
	public static final int TILE_HOLE = 1, TILE_CHECKPOINT = 2,
			TILE_CONVEYORBELT = 3, TILE_GEARS = 4, TILE_REPAIR = 5,
			TILE_WALL = 6, TILE_LASER = 7, TILE_START = 8;

	/**
	 * The different directions.
	 */
	// NOTE: Do not change their values as they are only there to help and are
	// often hard coded!
	public static final int DIR_NORTH = 0, DIR_EAST = 1, DIR_SOUTH = 2,
			DIR_WEST = 3;

	private final String[][] map;
	private final String mapData;

	/**
	 * Creates a new instance which will load the map provided.
	 * 
	 * @param indata
	 *            The map to load supplied as a String.
	 */
	public MapBuilder(String indata) {
		this.mapData = indata;
		String[] mapY = indata.substring(1).split("y");
		map = new String[mapY.length][];
		for (int i = 0; i < map.length; i++) {
			map[i] = mapY[i].substring(1).split("x", 64);
		}
		width = map.length;
		height = map[0].length;
	}

	/**
	 * Builds the map.
	 */
	public void build() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Create the floor
				if (y < map[0].length - 4) {
					buildFactoryFloor(x, y);
				} else {
					buildDockFloor(x, y);
				}

				// Add all the elements to the tile
				if (!map[x][y].equals("")) {
					for (String elementData : map[x][y].split(":")) {
						int tileData = Integer.parseInt(elementData);

						// Create the boardelement
						int tile = tileData % 10;
						switch (tile) {
						case TILE_HOLE:
							buildHole(x, y);
							break;
						case TILE_GEARS:
							buildGear(x, y, tileData / 10 == 0 ? false : true);
							break;
						case TILE_CONVEYORBELT:
							buildConveyerBelt(x, y, (tileData / 100) % 10,
									(tileData / 10) % 10);
							break;
						case TILE_CHECKPOINT:
							buildCheckPoint(x, y, tileData / 10);
							break;
						case TILE_REPAIR:
							buildRepair(x, y);
							break;
						case TILE_START:
							buildStartDock(x, y, tileData / 10);
							break;
						case TILE_WALL:
							buildWall(x, y, tileData / 10);
							break;
						case TILE_LASER:
							buildLaser(x, y, tileData / 10);
							break;
						}
					} // loop - elements
				} // if
			} // loop - X
		} // loop - Y
	}

	/**
	 * Converts the specified coordinates to coordinates on the map creates by
	 * the builder.
	 * 
	 * @param x
	 *            The X-coordinate to convert.
	 * @param y
	 *            THe Y-coordinate to convert.
	 * @return The converted position.
	 */
	public abstract Vector2 convertToMapY(int x, int y);

	/**
	 * Creates a new piece of factory floor.
	 * 
	 * @param x
	 *            The X-coordinate of the floor.
	 * @param y
	 *            The Y-coordinate of the floor.
	 */
	public abstract void buildFactoryFloor(int x, int y);

	/**
	 * Creates a new piece of dock floor.
	 * 
	 * @param x
	 *            The X-coordinate of the floor.
	 * @param y
	 *            The Y-coordinate of the floor.
	 */
	public abstract void buildDockFloor(int x, int y);

	/**
	 * Creates a new hole.
	 * 
	 * @param x
	 *            The X-coordinate of the hole.
	 * @param y
	 *            The Y-coordinate of the hole.
	 */
	public abstract void buildHole(int x, int y);

	/**
	 * Creates a new rotating gear.
	 * 
	 * @param x
	 *            The X-coordinate of the gear.
	 * @param y
	 *            The Y-coordinate of the gear.
	 * @param cw
	 *            Is <code>true</code> if the gear should rotate clockwise.
	 */
	public abstract void buildGear(int x, int y, boolean cw);

	/**
	 * Creates a new conveyer belt.
	 * 
	 * @param x
	 *            The X-coordinate of the conveyer belt.
	 * @param y
	 *            The Y-coordinate of the conveyer belt.
	 * @param speed
	 *            The speed of the conveyer belt.
	 * @param dir
	 *            The direction of the conveyer belt.
	 */
	public abstract void buildConveyerBelt(int x, int y, int speed, int dir);

	/**
	 * Creates a new checkpoint.
	 * 
	 * @param x
	 *            The X-coordinate of the checkpoint.
	 * @param y
	 *            The Y-coordinate of the checkpoint.
	 * @param nbr
	 *            The number of the checkpoint [1,8].
	 */
	public abstract void buildCheckPoint(int x, int y, int nbr);

	/**
	 * Creates a new repair.
	 * 
	 * @param x
	 *            The X-coordinate.
	 * @param y
	 *            The Y-coordinate.
	 */
	public abstract void buildRepair(int x, int y);

	/**
	 * Creates a new starting point.
	 * 
	 * @param x
	 *            The X-coordinate.
	 * @param y
	 *            The Y-coordinate.
	 * @param nbr
	 *            The number of the starting point [1,8].
	 */
	public abstract void buildStartDock(int x, int y, int nbr);

	/**
	 * Creates a new wall.
	 * 
	 * @param x
	 *            The X-coordinate of the wall.
	 * @param y
	 *            The Y-coordinate of the wall.
	 * @param dir
	 *            The direction of the wall. Note: As walls are placed between
	 *            tiles, the wall will be placed directly <code>dir</code> of
	 *            the tile with the specified coordinates.
	 */
	public abstract void buildWall(int x, int y, int dir);

	/**
	 * Creates a new laser.
	 * 
	 * @param x
	 *            The X-coordinate of the laser.
	 * @param y
	 *            The Y-coordinate of the laser.
	 * @param dir
	 *            The direction of the laser. Note: If the laser is to shot to
	 *            the north, then the specified direction should be south and
	 *            vice versa. Same for west - east. This is due to walls and
	 *            lasers both being placed between tiles.
	 */
	public abstract void buildLaser(int x, int y, int dir);

	/**
	 * Gives the map as a String.
	 * 
	 * @return The map as a String.
	 */
	public String getMap() {
		return this.mapData;
	}

	/**
	 * Gives the width of the map.
	 * 
	 * @return The width of the map.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gives the height of the map.
	 * 
	 * @return THe height of the map.
	 */
	public int getHeight() {
		return this.height;
	}
}
