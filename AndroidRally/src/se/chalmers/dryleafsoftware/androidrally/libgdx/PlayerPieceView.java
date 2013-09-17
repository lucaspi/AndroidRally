package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerPieceView extends Sprite  {

	private final int playerID;
	
	public PlayerPieceView(int playerID, TextureRegion texture) {
		super(texture);
		this.setSize(40, 40);
		this.playerID = playerID;
	}
	
	public int getPlayerID() {
		return playerID;
	}
}
