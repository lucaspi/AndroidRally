package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.view.DeckView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.view.MessageStage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


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
	private MessageStage messageStage;
	
	// Time to choose cards.
	private static final int CARDTIME = 40;
	// Time between rounds.
	private static final int ROUNDTIME = 120;
	
	private Texture boardTexture, cardTexture;
			
	/*
	 *  Stages for specifying which stage the game is currently in:
	 *  WAITING: Nothing needs to update.
	 *  ACTIONS: Showing the actions of the last round.
	 *  	- STEP_ACTION: Waits for user input when done showing one card's actions.
	 *  	- PLAY_ACTION: Do not wait for user input, plays all card's action in sequence.
	 *  	- SKIP_ACTION: Skips to the outcome.
	 *  CHOOSING_CARDS : Waiting for timer to reach zero or player to send cards.
	 */	
	private static enum Stage { 
		WAITING,
		PLAY
	};
	private int playSpeed = 1;
	private Stage currentStage = Stage.WAITING; 

	@Override
	public void create() {
		this.client = new Client(1);
		this.gameBoard = new BoardView();	
		this.messageStage = new MessageStage();

		// Only load the textures once.
		boardTexture = new Texture(Gdx.files.internal("textures/boardElements.png"));
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
		deckView.setRoundTick(ROUNDTIME);
//		deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);

		
		//Creates an input multiplexer to be able to use multiple listeners
		InputMultiplexer im = new InputMultiplexer(gameBoard, deckView);
		Gdx.input.setInputProcessor(im);
	}
	
	/**
	 * The main update loop.
	 */
	public void update() {
		if(currentStage.equals(Stage.PLAY)) {
			updateActions();
		}
	}

	/*
	 * Skips all actions on the current card
	 */
	private void skipCardActions() {
		if((actions == null || actions.isEmpty()) && result.hasNext()) {
			actions = result.getNextResult();
		}
		// Remove all actions
		for(RobotView r : gameBoard.getRobots()) {
			r.clearActions();
		}
		// Do all actions
		int i = actions.size();
		while(i > 0) {
			cleanAndRemove(actions.get(0));
			i--;
		}
		// Check result
		if(result.hasNext()) {
			actions = result.getNextResult();
		}else{
			deckView.displayDrawCard();
		}
		gameBoard.stopAnimations();
		currentStage = Stage.WAITING;
	}

	/*
	 * Skips all actions
	 */
	private void skipAllActions() {
		while(actions == null || !actions.isEmpty() || (actions.isEmpty() && result.hasNext())) {
			skipCardActions();
		}
	}
	
	private void cleanAndRemove(GameAction action) {
		int phase = action.getPhase();
		if(phase == GameAction.SPECIAL_PHASE_GAMEOVER) {
			System.out.println("GAME OVER");
			messageStage.dispGameOver(gameBoard.getRobots());
			currentStage = Stage.WAITING;
			return;
		}else if(phase == GameAction.SPECIAL_PHASE_CLIENT_WON) {
			System.out.println("GAME WON");
			messageStage.dispGameWon(gameBoard.getRobots());
			currentStage = Stage.WAITING;
			return;
		}	
		action.cleanUp(gameBoard.getRobots());
		actions.remove(action);
	}
	
	/*
	 * Updates the actions.
	 */
	private void updateActions() {
		// Remove and continue if last action complete.
		if(actions == null) {
			actions = result.getNextResult();
			return;
		}
		if(!actions.isEmpty() && (actions.get(0).isDone() || !actions.get(0).isRunning())) {
			if(actions.get(0).isDone()) {
				cleanAndRemove(actions.get(0));
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
			else if(currentStage.equals(Stage.PLAY)) {
				actions = result.getNextResult();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// Play events:
		if(event.getPropertyName().equals(DeckView.EVENT_PLAY)) {
			currentStage = Stage.PLAY;
			playSpeed = 1;
		}else if(event.getPropertyName().equals(DeckView.EVENT_PAUSE)) {
			currentStage = Stage.WAITING;
		}else if(event.getPropertyName().equals(DeckView.EVENT_FASTFORWARD)) {
			currentStage = Stage.PLAY;
			playSpeed = 2;
		}else if(event.getPropertyName().equals(DeckView.EVENT_STEP_ALL)) {
			skipAllActions();
		}else if(event.getPropertyName().equals(DeckView.EVENT_STEP_CARD)) {
			skipCardActions();
		}
		
		// Other events:
		else if(event.getPropertyName().equals(DeckView.EVENT_DRAW_CARDS)) {
			// Displays the cards and waits for the timer task.
			deckView.setDeckCards(client.loadCards(), cardTexture);
			deckView.setCardTick(CARDTIME);
		}else if(event.getPropertyName().equals(DeckView.TIMER_CARDS)) {
			client.sendCard(deckView.getChosenCards());
			deckView.displayWaiting();
			currentStage = Stage.WAITING;
			deckView.setCardTick(-1);
		}else if(event.getPropertyName().equals(DeckView.TIMER_ROUND)
				&& currentStage.equals(Stage.WAITING)) {
			deckView.displayPlayOptions();
			result = client.getRoundResult();
			deckView.setRoundTick(ROUNDTIME);
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
		messageStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		messageStage.draw();
		update();
		
//		Table.drawDebug(deckView);
//		Table.drawDebug(gameBoard);
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
