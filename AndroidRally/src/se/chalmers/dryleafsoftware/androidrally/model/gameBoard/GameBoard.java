package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
	private Tile[][] tiles = null;
	private List<Laser> lasers;
	private String[][] mapAsString;
	/*
	 * Max 8 players.
	 */
	private int[][] startingPosition = new int[8][2];
	
	public static final int EAST = 1;
	public static final int WEST = 3;
	public static final int SOUTH = 2;
	public static final int NORTH = 0;

	private static final int
		TILE_HOLE = 1,
		TILE_CHECKPOINT = 2,
		TILE_CONVEYORBELT = 3,
		TILE_GEARS = 4,
		TILE_REPAIR = 5,
		TILE_WALL = 6,
		TILE_LASER = 7,
		TILE_START = 8;
	
	public static final int WIDTH = 12;
	public static final int HEIGHT = 16;
	
	/*
	 * Hole = 1
	 * CheckPoint = (nr)2
	 * ConveyorBelt = (GameBoard.staticfinal)3
	 * Gear = 4 -> moturs, 14 -> medurs
	 * Repair = 5
	 * Wall = (GameBoard.staticFinal)6
	 * Laser = (GameBoard.staticFinal)7
	 * 
	 *  : mellan olika element per tile
	 *  
	 */
	
	public GameBoard(String[][] map) {
		createBoard(map);
		mapAsString = map;
		lasers = new ArrayList<Laser>();
		
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public int[][] getStartingPositions(){
		return startingPosition;
	}
	
	public List<Laser> getLasers(){
		return lasers;
	}
	
	public String[][] getMapAsString(){
		return mapAsString;
	}
		
	
	/**
	* Builds the board using the specified map data.
	* @param map An array of integers mapping the map's layout.
	*/
	private void createBoard(String[][] map) {
		tiles = new Tile[map.length][map[0].length];
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {	
				tiles[x][y] = new Tile();
				int wall = 0;
				// Add all the elements to the tile
				if(!map[x][y].equals("")) {
					for(String elementData : map[x][y].split(":")) {
						int tileData = Integer.parseInt(elementData);
						// Create the boardelement
						int tile = tileData % 10;
						if(tile == TILE_HOLE) {
							tiles[x][y].addBoardElement(new Hole());
						}else if(tile == TILE_GEARS) {
							tiles[x][y].addBoardElement(new Gears(!(tileData / 10 == 0)));
						}else if(tile == TILE_CONVEYORBELT) {
							tiles[x][y].addBoardElement(new ConveyorBelt(tileData / 100, ((tileData / 10)%10)));
						}else if(tile == TILE_CHECKPOINT) {
							tiles[x][y].addBoardElement(new CheckPoint(tileData / 10));
						}else if(tile == TILE_REPAIR) {
							tiles[x][y].addBoardElement(new Wrench(1));
						}else if(tile == TILE_WALL || tile == TILE_LASER) {
							wall += tileData / 10;
						}else if(tile == TILE_LASER){
							lasers.add(new Laser(x, y, tileData / 10));
						}else if(tile == TILE_START){
							startingPosition[tileData / 10 - 1][0] = x;
							startingPosition[tileData / 10 - 1][1] = y;
						}
					} // loop - elements
					tiles[x][y].setWalls(wall);
				} // if
			} // loop - Y
		} // loop - X
	}
//	/**
//	* Builds the board using the specified texture and map data.
//	* @param texture The texture to use.
//	* @param map An array of integers mapping the map's layout.
//	* NOTE: The bottom 4 rows will be the dock area.
//	*/
//	public void createBoard(String[][] map) {
//		Texture conveyerTexture = new Texture(Gdx.files.internal("textures/special/conveyor.png"));
//		conveyerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		conveyerTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.Repeat);
//		
//		for(int x = 0; x < map.length; x++) {
//			for(int y = 0; y < map[0].length; y++) {	
//				// Create the floor
//				Sprite floor = null;
//				if(y < map[0].length - 4) {
//					floor = new Sprite(new TextureRegion(texture, 0, 0, 64, 64));
//				}else{
//					floor = new Sprite(new TextureRegion(texture, 64, 0, 64, 64));
//				}
//				// Add the floor
//				floor.setSize(40, 40);
//				floor.setPosition(40 * x, 800 - 40 * (y+1));
//				tiles.add(floor);
//				
//				// Add all the elements to the tile
//				if(!map[x][y].equals("")) {
//					for(String elementData : map[x][y].split(":")) {
//					int tileData = Integer.parseInt(elementData);
//					
//					// Create the boardelement
//					int tile = tileData % 10;
//					Sprite s = null;
//					if(tile == TILE_HOLE) {	
//						s = new Sprite(new TextureRegion(texture, 128, 0, 64, 64));
//					}else if(tile == TILE_GEARS) {
//						s = new GearsView(new TextureRegion(texture, 320, 0, 64, 64),
//						tileData / 10 == 0 ? false : true);
//					}else if(tile == TILE_CONVEYORBELT) {
//						s = new ConveyorBeltView(new TextureRegion(conveyerTexture, 0, 0, 1f, 1f),
//						(tileData / 10) * 90);
//					}else if(tile == TILE_CHECKPOINT) {
//						s = new CheckPointView(new TextureRegion(texture, 192, 0, 64, 64),
//						(tileData / 10));
//					}else if(tile == TILE_REPAIR) {
//						s = new Sprite(new TextureRegion(texture, 256, 0, 64, 64));
//					}else if(tile == TILE_WALL || tile == TILE_LASER) {
//						int rotation = tileData / 10;
//						Sprite wallBit;
//					if(tile == TILE_WALL) {
//						wallBit = new Sprite(new TextureRegion(texture, 384, 0, 64, 64));
//					}else{
//						 new Sprite(new TextureRegion(texture, 448, 0, 64, 64));
//					}
//						wallBit.setSize(40, 40);
//						wallBit.setPosition(40 * x - 20, 800 - 40 * (y+1));
//						wallBit.setOrigin(wallBit.getWidth()/2 + 20, wallBit.getHeight()/2);
//						wallBit.rotate(-(1 + rotation) * 90);
//						wallList.add(wallBit);	
//					
//					}
//					// Add the element if created
//					if(s != null) {
//						s.setSize(40, 40);
//						s.setPosition(40 * x, 800 - 40 * (y+1));
//						tiles.add(s);
//					}
//					} // loop - elements
//				} // if
//			} // loop - Y
//		} // loop - X
//	}

}
