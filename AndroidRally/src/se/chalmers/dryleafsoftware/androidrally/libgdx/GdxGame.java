package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;


/**
 * 
 * 
 * @author
 *
 */
public class GdxGame implements ApplicationListener, PropertyChangeListener {

	private List<RobotView> players;
	private Client client;
	
	private List<GameAction> actions;
	private RoundResult result;
		
	private BoardView gameBoard;
	private DeckView deckView;
	
	// Time to choose cards.
	private static final int CARDTIME = 40;
	// Time between rounds.
	private static final int ROUNDTIME = 120;
	
	private Texture boardTexture, cardTexture;
	private long runTimerStamp = TimeUtils.millis() +  ROUNDTIME*1000;
			
	/*
	 *  Stages for specifying which stage the game is currently in:
	 *  WAITING: Nothing needs to update.
	 *  ACTIONS: Showing the actions of the last round.
	 *  	- STEP_ACTION: Waits for user input when done showing one card's actions.
	 *  	- PLAY_ACTION: Do not wait for user input, plays all card's action in sequence.
	 *  	- SKIP_ACTION: Skips to the outcome.
	 *  CHOOSING_CARDS : Waiting for timer to reach zero or player to send cards.
	 */	
	private static enum Stage { WAITING, STEP_ACTIONS, PLAY_ACTIONS, SKIP_ACTIONS, CHOOSING_CARDS };
	private Stage currentStage = Stage.WAITING; 
	
	public static final String EVENT_UPDATE = "e_update";
	
	@Override
	public void create() {
		this.client = new Client(1);
		this.gameBoard = new BoardView();		

		// Only load the textures once.
		boardTexture = new Texture(Gdx.files.internal("textures/testTile.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
		cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		gameBoard.createBoard(boardTexture, client.getMap());
		
		players = client.getRobots(boardTexture, gameBoard.getDocksPositions());
		for(RobotView player : players) {
			gameBoard.addRobot(player);
		}		
		
		this.deckView = new DeckView(players, client.getRobotID());
		deckView.addListener(this);
		deckView.displayDrawCard();
		deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);

		//Creates an input multiplexer to be able to use multiple listeners
		InputMultiplexer im = new InputMultiplexer(gameBoard, deckView);
		Gdx.input.setInputProcessor(im);
	}
	
	/**
	 * The main update loop.
	 */
	public void update() {
		switch(currentStage) {
		case STEP_ACTIONS:
		case PLAY_ACTIONS:		
			updateActions();
			break;
		case SKIP_ACTIONS:
			skipActions();
			break;
		default:
			// Do nothing...
		}
	}
	
	/*
	 * Skips all actions
	 */
	private void skipActions() {
		while(true) {
			for(GameAction a : actions) {
				a.cleanUp(gameBoard.getRobots());
			}
			if(result.hasNext()) {
				actions = result.getNextResult();
			}else{
				break;
			}
		}
		currentStage = Stage.WAITING;
		deckView.displayDrawCard();
	}
	
	/*
	 * Updates the actions.
	 */
	private void updateActions() {
		// Remove and continue if last action complete.
		// TODO: make the code look better!
		if(!actions.isEmpty() && (actions.get(0).isDone() || !actions.get(0).isRunning())) {
			if(actions.get(0).isDone()) {
				actions.get(0).cleanUp(gameBoard.getRobots());
				actions.remove(0);
			}
			gameBoard.stopAnimations();
			if(!actions.isEmpty()) {
				actions.get(0).action(gameBoard.getRobots());
				gameBoard.setAnimate(actions.get(0).getPhase());
			}
		}
		// Get next list of actions
		else if(actions.isEmpty()) {
			// If no more: wait
			if(!result.hasNext()) {
				currentStage = Stage.WAITING;
				deckView.displayDrawCard();
			}
			// Otherwise, if playing, get the next list.
			else if(currentStage.equals(Stage.PLAY_ACTIONS)) {
				actions = result.getNextResult();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO: clean this method.
		if(event.getPropertyName().equals(DeckView.EVENT_PLAY)) {
			currentStage = Stage.PLAY_ACTIONS;
			actions = result.getNextResult();
		}else if(event.getPropertyName().equals(DeckView.EVENT_STEP)) {
			currentStage = Stage.STEP_ACTIONS;
			actions = result.getNextResult();
		}else if(event.getPropertyName().equals(DeckView.EVENT_SKIP)) {
			currentStage = Stage.SKIP_ACTIONS;
			actions = result.getNextResult();
		}else if(event.getPropertyName().equals(DeckView.EVENT_DRAW_CARDS)) {
			// Displays the cards and waits for the timer task.
//			deckView.setDeckCards(client.getCards(cardTexture));
			client.loadCards(cardTexture, deckView);
			deckView.setTimer(Math.min(CARDTIME + 1, deckView.getTimerSeconds()), DeckView.TIMER_CARDS);
			currentStage = Stage.CHOOSING_CARDS;
		}else if(event.getPropertyName().equals(DeckView.TIMER_CARDS)
				&& currentStage.equals(Stage.CHOOSING_CARDS)) {
			client.sendCard(deckView.getChosenCards());
			deckView.displayWaiting();
			currentStage = Stage.WAITING;
			deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);
		}else if(event.getPropertyName().equals(DeckView.TIMER_ROUND)
				&& currentStage.equals(Stage.WAITING)) {
			runTimerStamp = TimeUtils.millis() +  ROUNDTIME*1000;
			deckView.displayPlayOptions();
			result = client.getRoundResult();		
			deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);
		}
	}

	@Override
	public void dispose() {
		gameBoard.dispose();
		deckView.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gameBoard.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		gameBoard.draw();
		deckView.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		deckView.draw();
		update();
		
		Table.drawDebug(deckView);
		Table.drawDebug(gameBoard);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
