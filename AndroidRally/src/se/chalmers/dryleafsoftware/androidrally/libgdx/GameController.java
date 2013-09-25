package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

/**
 * This is the client-side controller which handles the game's logic.
 * 
 * @author
 *
 */
public class GameController implements GestureListener, PropertyChangeListener {

	private final GdxGame game;
	private final Client client;
	private DeckView deckView;
	
	private List<GameAction> actions;
	private RoundResult result;
	
	private final Texture boardTexture, cardTexture;
	
//	private 

	/**
	 * Creates a new instance which will control the specified game.
	 * @param game The game to control.
	 */
	public GameController(GdxGame game) {
		this.game = game;
		this.client = new Client(1);
		this.deckView = game.getDeckView();
		game.addListener(this);
		
		// Only load the textures once.
		boardTexture = new Texture(Gdx.files.internal("textures/testTile.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
		cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		game.getBoardView().createBoard(boardTexture, client.getMap());
		
		for(RobotView player : client.getRobots(boardTexture, game.getBoardView().getDocksPositions())) {
			game.getBoardView().addRobot(player);
		}		
	
		this.deckView.setDeckCards(client.getCards(cardTexture));
				
		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {			
			@Override
			public void run() {
				System.out.println("Sending cards");
				client.sendCard(deckView.getChosenCards());
			}
		}, 3);
		timer.scheduleTask(new Timer.Task() {			
			@Override
			public void run() {
				System.out.println("Getting actions");
				result = client.getRoundResult();
				actions = result.getNextResult();
			}
		}, 4);
		timer.start();
	}
	
	public RoundResult getRoundResults() {
		return result;
	}
	
	private void update() {
		if(actions != null && !actions.isEmpty() && (actions.get(0).isDone() || !actions.get(0).isRunning())) {
			if(actions.get(0).isDone()) {
				actions.remove(0);
			}
			if(!actions.isEmpty()) {
				actions.get(0).action(game.getBoardView().getRobots());
				if(actions.get(0).getPhase() == GameAction.PHASE_BOARD_ELEMENT) {
					game.getBoardView().setAnimationElement(true);
				}else{
					game.getBoardView().setAnimationElement(false);
				}
			}else{
				game.getBoardView().setAnimationElement(false);
			}
		}else if(actions != null && actions.isEmpty()){
			if(result.hasNext()) {
				System.out.println("Next round!");
				actions = result.getNextResult();
			}
		}
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkCameraBounds() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(GdxGame.EVENT_UPDATE)) {
			update();
		}
	}
}
