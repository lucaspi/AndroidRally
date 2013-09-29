package se.chalmers.dryleafsoftware.androidrally.sharred;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.AnimatedImage;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.CheckPointView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.ConveyorBeltView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.DockView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.GearsView;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class MapBuilder {

	public static final int 
	TILE_HOLE = 1,
	TILE_CHECKPOINT = 2,
	TILE_CONVEYORBELT = 3,
	TILE_GEARS = 4,
	TILE_REPAIR = 5,
	TILE_WALL = 6,
	TILE_LASER = 7,
	TILE_START = 8;

	public MapBuilder(String indata) {
		String[] mapY = indata.substring(1).split("y");
		String[][] map = new String[mapY.length][];
		for(int i = 0; i < map.length; i++) {
			map[i] = mapY[i].substring(1).split("x", 64);
		}
		
		for(int y = 0; y < map[0].length; y++) {	
			for(int x = 0; x < map.length; x++) {
				// Create the floor
				if(y < map[0].length - 4) {
					buildFactoryFloor(x, y);
				}else{
					buildDockFloor(x, y);
				}

				// Add all the elements to the tile
				if(!map[x][y].equals("")) {
					for(String elementData : map[x][y].split(":")) {
						int tileData = Integer.parseInt(elementData);

						// Create the boardelement
						int tile = tileData % 10;
						// TODO : Switch
						switch(tile) {
						case TILE_HOLE:
							buildHole(x, y);
							break;
						case TILE_GEARS:
							buildGear(x, y, tileData / 10 == 0 ? false : true);	
							break;
						case TILE_CONVEYORBELT:
							buildConveyerBelt(x, y, tileData / 100, (tileData / 10) % 10);
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
							buildWall(x, y, tileData / 10 + 1);
							break;
						case TILE_LASER:
							buildLaser(x, y, tileData / 10 + 1);
							break;
						}
					} // loop - elements
				} // if
			} // loop - X
		} // loop - Y
	}
	
	/**
	 * 
	 * @param x
	 * @param y 
	 */
	public abstract void buildFactoryFloor(int x, int y);
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void buildDockFloor(int x, int y);
	public abstract void buildHole(int x, int y);
	public abstract void buildGear(int x, int y, boolean cw);
	public abstract void buildConveyerBelt(int x, int y, int speed, int dir);
	public abstract void buildCheckPoint(int x, int y, int nbr);
	public abstract void buildRepair(int x, int y);
	public abstract void buildStartDock(int x, int y, int nbr);
	public abstract void buildWall(int x, int y, int dir);
	public abstract void buildLaser(int x, int y, int dir);
}
