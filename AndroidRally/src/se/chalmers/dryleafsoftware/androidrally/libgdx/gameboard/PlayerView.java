package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This is the player's robot on the gameboard.
 * 
 * @author 
 *
 */
public class PlayerView extends Image  {

	private final int playerID;
	
	/**
	 * Creates a new instance which will be linked to a player by the ID-number
	 * of that player.
	 * @param playerID The ID-number of the player whose piece this is.
	 * @param texture The texture to use.
	 */
	public PlayerView(int playerID, TextureRegion texture) {
		super(texture);
		this.setSize(40, 40);
		this.playerID = playerID;
	}
	
	/**
	 * Gives the ID-number of the player whose piece this is.
	 * @return The ID-number of the player whose piece this is.
	 */
	public int getPlayerID() {
		return playerID;
	}
}
