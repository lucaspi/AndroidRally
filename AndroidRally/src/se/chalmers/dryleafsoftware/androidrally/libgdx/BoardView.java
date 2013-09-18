package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.CheckPointView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.ConveyorBeltView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.GearsView;
import se.chalmers.dryleafsoftware.androidrally.model.gameBoard.ConveyorBelt;

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

	private List<Sprite> staticSprites = new ArrayList<Sprite>();
	private List<PlayerPieceView> players = new ArrayList<PlayerPieceView>();
	
	private static final int TILE_EMPTY = 0,
			TILE_HOLE = 1,
			TILE_CHECKPOINT = 2,
			TILE_CONVEYORBELT = 3,
			TILE_GEARS = 4,
			TILE_REPAIR = 5;
	
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
	public void createBoard(Texture texture, int[][] map) {
		Texture conveyerTexture = new Texture(Gdx.files.internal("textures/special/conveyor.png"));
		conveyerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		conveyerTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.Repeat);
				
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				int tile = map[x][y] % 10;
				Sprite floor = null;
				if(y < map[0].length - 4) {
					floor = new Sprite(new TextureRegion(texture, 0, 0, 64, 64));
				}else{
					floor = new Sprite(new TextureRegion(texture, 64, 0, 64, 64));
				}
				floor.setSize(40, 40);
				floor.setPosition(40 * x, 800 - 40 * (y+1));
				staticSprites.add(floor);
					
				Sprite s = null;
				if(tile == TILE_HOLE) {					
					s = new Sprite(new TextureRegion(texture, 128, 0, 64, 64));
				}else if(tile == TILE_GEARS) {
					s = new GearsView(new TextureRegion(texture, 320, 0, 64, 64), 
							map[x][y] / 100 == 0 ? false : true);
				}else if(tile == TILE_CONVEYORBELT) {
					s = new ConveyorBeltView(new TextureRegion(conveyerTexture, 0, 0, 1f, 1f), 
							(map[x][y] / 100) * 90);
				}else if(tile == TILE_CHECKPOINT) {
					s = new CheckPointView(new TextureRegion(texture, 192, 0, 64, 64), 
							(map[x][y] / 100));
				}else if(tile == TILE_REPAIR) {
					s = new Sprite(new TextureRegion(texture, 256, 0, 64, 64));
				}
				if(s != null) {
					s.setSize(40, 40);
					s.setPosition(40 * x, 800 - 40 * (y+1));
					staticSprites.add(s);
				}
				int walls = map[x][y] / 10 % 10;
				if(walls >= 2) {
					Sprite wall = new Sprite(new TextureRegion(texture, 384, 0, 64, 64));
					wall.setSize(40, 40);
					wall.setPosition(40 * x - 20, 800 - 40 * (y+1));
					wall.setOrigin(wall.getWidth()/2, wall.getHeight()/2);
					staticSprites.add(wall);
				}
				walls -= 2;
				if(walls == 1) {
					Sprite wall = new Sprite(new TextureRegion(texture, 384, 0, 64, 64));
					wall.rotate(90);
					wall.setSize(40, 40);
					wall.setPosition(40 * x, 800 - 40 * (y+1) - 20);
					wall.setOrigin(wall.getWidth()/2, wall.getHeight()/2);					
					staticSprites.add(wall);
				}
			}
		}
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
		for(Sprite s : staticSprites) {
			s.draw(spriteBatch);
		}
		for(PlayerPieceView p : players) {
			p.draw(spriteBatch);
		}
	}
}
