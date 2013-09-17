package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BoardView {

	private List<Sprite> staticSprites = new ArrayList<Sprite>();
	private List<PlayerPieceView> players = new ArrayList<PlayerPieceView>();
	
	public BoardView() {
		
	}
	
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
	
	public void addPlayer(PlayerPieceView player) {
		players.add(player);
	}
	
	public PlayerPieceView getPlayer(int playerID) {
		for(PlayerPieceView p : players) {
			if(p.getPlayerID() == playerID) {
				return p;
			}
		}
		return null;
	}
	
	public void render(SpriteBatch spriteBatch) {
		for(Sprite s : staticSprites) {
			s.draw(spriteBatch);
		}
		for(PlayerPieceView p : players) {
			p.draw(spriteBatch);
		}
	}
}
