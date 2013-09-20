package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.PlayerView;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Move;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Turn;
import se.chalmers.dryleafsoftware.androidrally.model.cards.TurnType;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * 
 * @author
 *
 */
public class Client {

	private final GameModel model;
	private final int clientID;
	
	/**
	 * Creates a new client instance.
	 */
	public Client(int clientID) {
		this.model = new GameModel(8);
		this.clientID = clientID;
	}
	
	/**
	 * Returns the map of the board as a matrix of strings.
	 * @return A map of the board as a matrix of strings.
	 */
	public String[][] getMap() {
		return model.getMap();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<CardView> getCards(Texture texture) {
		model.dealCards();
		List<CardView> cards = new ArrayList<CardView>();
		
		// TODO: change to clientID and input to string
		for(Card card : model.getRobots().get(0).getCards()) {
			int prio = card.getPriority();
			int regX = 0;
			if(prio <= 60) {
				regX = 0;	// UTURN
			}else if(prio <= 410 && prio % 20 != 0) {
				regX = 64;	// LEFT
			}else if(prio <= 420 && prio % 20 == 0) {
				regX = 128;	// LEFT
			}else if(prio <= 480) {
				regX = 192;	// Back 1
			}else if(prio <= 660) {
				regX = 256;	// Move 1
			}else if(prio <= 780) {
				regX = 320;	// Move 2
			}else if(prio <= 840) {
				regX = 384;	// Move 3
			}				
			CardView cv = new CardView(new TextureRegion(texture, regX, 0, 64, 90), 
				card.getPriority());
			cards.add(cv);
		}
		return cards;
	}
	
	/**
	 * Gives all the players as a list.
	 * @param texture The textures to use.
	 * @param startPoints The points to start at.
	 * @return A list of all the players.
	 */
	public List<PlayerView> getPlayers(Texture texture, Vector2[] startPoints) {
		List<PlayerView> players = new ArrayList<PlayerView>();		
		for(int i = 0; i < model.getRobots().size(); i++) {
			PlayerView player1 = new PlayerView(i, new TextureRegion(texture, i * 64, 64, 64, 64));
			player1.setPosition(startPoints[i].x, startPoints[i].y);
			player1.setOrigin(20, 20);
			players.add(player1);
		}		
		return players;
	}
}
