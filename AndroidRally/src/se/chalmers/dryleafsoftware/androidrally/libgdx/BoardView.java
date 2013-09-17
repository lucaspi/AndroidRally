package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
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
	
	/**
	 * Creates a new instance of BoardView.
	 */
	public BoardView() {
		
	}
	
	/**
	 * Builds the board using the specified texture and map data.
	 * @param texture The texture to use.
	 */
	public void createBoard(Texture texture/*TODO: need map*/) {
		TextureRegion factoryFloor = new TextureRegion(texture, 0, 0, 
				64, 64);
		TextureRegion dockFloor = new TextureRegion(texture, 64, 0, 
				64, 64);
		
		for(int x = 0; x < 12; x++) {
			for(int y = 0; y < 12; y++) {
				Sprite s = new Sprite(factoryFloor);
				s.setSize(40, 40);
				s.setPosition(40 * x, 760 - 40 * y);
				staticSprites.add(s);
			}
			for(int y = 0; y < 4; y++) {
				Sprite s = new Sprite(dockFloor);
				s.setSize(40, 40);
				s.setPosition(40 * x, 760 - 40 * y - 40 * 12);
				staticSprites.add(s);
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
