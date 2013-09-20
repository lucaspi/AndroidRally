package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.MoveAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.RotationAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.PlayerView;
import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;

import com.badlogic.gdx.Game;
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
	
	// 1 framför = en position
	private String indata = "0:10101;0:1;1:10203";
	
	/**
	 * Creates a new client instance.
	 * @param clientID The ID number of the player.
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
	
	public List<GameAction> getRoundResult() {
		List<GameAction> actions = new ArrayList<GameAction>();
		
		String[] allActions = indata.split(";");
		for(String s : allActions) {
			String[] singleAction = s.split(":");
			int player = Integer.parseInt(singleAction[0]);
			int data = Integer.parseInt(singleAction[1]);
			if(data / 10000 == 1) {	// Pos
				actions.add(new MoveAction(player, (data % 10000) / 100, data % 100));
			}else {	// Dir
				actions.add(new RotationAction(player, data));
			}
		}
		
		return actions;
	}
	
	/**
	 * Gives the client's cards.
	 * @return A list of the client's cards.
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
		Collections.sort(cards);
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
