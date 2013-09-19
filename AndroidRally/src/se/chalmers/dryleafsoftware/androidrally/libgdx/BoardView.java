package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.CheckPointView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.ConveyorBeltView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.GearsView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A gameboard contains all the tiles and dynamic objects, like all the players' robots.
 * 
 * @author
 *
 */
public class BoardView {

	private List<Sprite> tiles = new ArrayList<Sprite>();
	private List<Sprite> wallList = new ArrayList<Sprite>();
	private List<PlayerPieceView> players = new ArrayList<PlayerPieceView>();
	
	private static final int TILE_EMPTY = 0,
			TILE_HOLE = 1,
			TILE_CHECKPOINT = 2,
			TILE_CONVEYORBELT = 3,
			TILE_GEARS = 4,
			TILE_REPAIR = 5,
			TILE_WALL = 6;
	
	/**
	 * Creates a new instance of BoardView.
	 */
	public BoardView() {
		
	}
	
	/**
	 * Builds the board using the specified texture and map data.
	 * @param texture The texture to use.
	 * @param map An array of integers mapping the map's layout. 
	 * NOTE: The bottom 4 rows will be the dock area.
	 */
	public void createBoard(Texture texture, String[][] map) {
		Texture conveyerTexture = new Texture(Gdx.files.internal("textures/special/conveyor.png"));
		conveyerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		conveyerTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.Repeat);
				
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {				
				// Create the floor
				Sprite floor = null;
				if(y < map[0].length - 4) {
					floor = new Sprite(new TextureRegion(texture, 0, 0, 64, 64));
				}else{
					floor = new Sprite(new TextureRegion(texture, 64, 0, 64, 64));
				}
				// Add the floor
				floor.setSize(40, 40);
				floor.setPosition(40 * x, 800 - 40 * (y+1));
				tiles.add(floor);

				// Add all the elements to the tile
				if(!map[x][y].equals("")) {
					for(String elementData : map[x][y].split(":")) {
						int tileData = Integer.parseInt(elementData);

						// Create the boardelement
						int tile = tileData % 10;
						Sprite s = null;
						if(tile == TILE_HOLE) {					
							s = new Sprite(new TextureRegion(texture, 128, 0, 64, 64));
						}else if(tile == TILE_GEARS) {
							s = new GearsView(new TextureRegion(texture, 320, 0, 64, 64), 
									tileData / 10 == 0 ? false : true);
						}else if(tile == TILE_CONVEYORBELT) {
							s = new ConveyorBeltView(new TextureRegion(conveyerTexture, 0, 0, 1f, 1f), 
									(tileData / 10) * 90);
						}else if(tile == TILE_CHECKPOINT) {
							s = new CheckPointView(new TextureRegion(texture, 192, 0, 64, 64), 
									(tileData / 10));
						}else if(tile == TILE_REPAIR) {
							s = new Sprite(new TextureRegion(texture, 256, 0, 64, 64));
						}else if(tile == TILE_WALL) {
							int wallPos = tileData / 10;
							if(wallPos >= 2) {
								Sprite wall = new Sprite(new TextureRegion(texture, 384, 0, 64, 64));
								wall.setSize(40, 40);
								wall.setPosition(40 * x - 20, 800 - 40 * (y+1));
								wall.setOrigin(wall.getWidth()/2, wall.getHeight()/2);
								wallList.add(wall);
								wallPos -= 2;
							}							
							if(wallPos == 1) {
								Sprite wall = new Sprite(new TextureRegion(texture, 384, 0, 64, 64));
								wall.rotate(90);
								wall.setSize(40, 40);
								wall.setPosition(40 * x, 800 - 40 * (y+1) - 20);
								wall.setOrigin(wall.getWidth()/2, wall.getHeight()/2);					
								wallList.add(wall);
							}

						}
						// Add the element if created
						if(s != null) {
							s.setSize(40, 40);
							s.setPosition(40 * x, 800 - 40 * (y+1));
							tiles.add(s);
						}
					} // loop - elements
				} // if
			} // loop - Y
		} // loop - X
	}
	
	/**
	 * Adds the specified player to the board.
	 * @param player The player to add.
	 */
	public void addPlayer(PlayerPieceView player) {
		players.add(player);
	}
	
	/**
	 * Gives the player with the specified ID-number.
	 * @param playerID The ID-number to look for.
	 * @return The player with the specified ID.
	 */
	public PlayerPieceView getPlayer(int playerID) {
		for(PlayerPieceView p : players) {
			if(p.getPlayerID() == playerID) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Renders the scene.
	 * @param spriteBatch The instance of spritebatch to use when rendering.
	 */
	public void render(SpriteBatch spriteBatch) {
		for(Sprite s : tiles) {
			s.draw(spriteBatch);
		}
		for(PlayerPieceView p : players) {
			p.draw(spriteBatch);
		}
		for(Sprite s : wallList) {
			s.draw(spriteBatch);
		}
	}
}
