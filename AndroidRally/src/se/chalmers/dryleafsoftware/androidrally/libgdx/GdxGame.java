package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.CheckPointHandler;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.view.DeckView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.view.MessageStage;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


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
	private int cardTime;
	// Time between rounds.
	private int roundTime;
	
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
		PLAY,
		PAUSE
	};
	private int playSpeed = 1;
	private Stage currentStage = Stage.WAITING; 

	@Override
	public void create() {
		this.client = new Client(1);
		this.gameBoard = new BoardView();	
		this.messageStage = new MessageStage();
		
		String[] gameData = client.getGameData().split(";");
		this.cardTime = Integer.parseInt(gameData[0]);
		this.roundTime = Integer.parseInt(gameData[1]) * 3600;
		
		messageStage.addListener(this);

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
		players.get(client.getRobotID()).addListener(
				new CheckPointHandler(gameBoard.getCheckPoints(), boardTexture));
		
		this.deckView = new DeckView(players, client.getRobotID());
		deckView.addListener(this);
		deckView.displayDrawCard();
		deckView.setRoundTick(roundTime);
		
		//Creates an input multiplexer to be able to use multiple listeners
		InputMultiplexer im = new InputMultiplexer(inputProcess, messageStage, gameBoard, deckView);
		Gdx.input.setInputProcessor(im);
		Gdx.input.setCatchBackKey(true);
	}
	
	/*
	 * Handle the game when the client has died.
	 */
	private void handleGameOver() {
		messageStage.dispGameOver(gameBoard.getRobots());
		stopGame();
	}
	
	/*
	 * Will handle everything needed to stop the game from continuing.
	 */
	private void stopGame() {
		gameBoard.stopAnimations();
		result.clear();
		actions.clear();
		deckView.setCardTick(-1);
		deckView.setRoundTick(-1);
		deckView.displayWaiting();
		currentStage = Stage.WAITING;
	}
	
	/*
	 * Handle the game when someone won.
	 */
	private void handleGameWon() {
		messageStage.dispGameWon(gameBoard.getRobots());
		stopGame();
	}
	
	/**
	 * The main update loop.
	 */
	public void update() {
		if(currentStage.equals(Stage.PLAY)) {
			updateActions();
		}else if(currentStage.equals(Stage.PAUSE) && actions != null && !actions.isEmpty()) {
			if(actions.get(0).isDone()) {
				gameBoard.stopAnimations();
				cleanAndRemove(actions.get(0));
				currentStage = Stage.WAITING;
			}
		}
	}

	/*
	 * Skips all actions on the current card and then waits.
	 */
	private void skipCardActions() {
		if(actions == null || actions.isEmpty()) {
			nextCard();
		}
		// Remove all actions
		for(RobotView r : gameBoard.getRobots()) {
			r.clearActions();
		}
		// Do all actions
		int i = actions.size();
		while(i-- > 0) {
			if(!cleanAndRemove(actions.get(0))) {
				return;
			}
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
	
	/*
	 * Will return true as long as the game should continue.
	 */
	private boolean cleanAndRemove(GameAction action) {
		int phase = action.getPhase();
		action.cleanUp(gameBoard.getRobots());
		actions.remove(action);
		if(phase == GameAction.SPECIAL_PHASE_GAMEOVER) {
			handleGameOver();
			return false;
		}else if(phase == GameAction.SPECIAL_PHASE_WON) {
			handleGameWon();
			return false;
		}else if(actions.isEmpty()) {
			nextCard();
		}
		return true;
	}

	private void nextCard() {
		if(result.hasNext()) {
			actions = result.getNextResult();
			deckView.getRegisters().nextHighLight();
		}else{
			currentStage = Stage.WAITING;
			deckView.getRegisters().clearHighLight();
			deckView.displayDrawCard();
			client.incrementRound();
		}
	}

	/*
	 * Updates the actions.
	 */
	private void updateActions() {
		// Remove and continue if last action complete.
		if((actions == null || actions.isEmpty())) {
			nextCard();
			return;
		}else if(actions.get(0).isDone() || !actions.get(0).isRunning()) {
			if(actions.get(0).isDone()) {
				cleanAndRemove(actions.get(0));
			}
			gameBoard.stopAnimations();
			int syncSpeed = playSpeed;
			if(!actions.isEmpty()) {
				actions.get(0).setDuration(actions.get(0).getDuration() / syncSpeed);
				actions.get(0).action(gameBoard.getRobots());
				gameBoard.setAnimate(actions.get(0).getPhase(), syncSpeed);
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
			currentStage = Stage.PAUSE;
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
			deckView.setCardTick(cardTime);
		}else if(event.getPropertyName().equals(DeckView.TIMER_CARDS)) {
			client.sendCard(deckView.getChosenCards());
			deckView.displayWaiting();
			currentStage = Stage.WAITING;
			deckView.setCardTick(-1);
		}else if(event.getPropertyName().equals(DeckView.TIMER_ROUND)
				&& currentStage.equals(Stage.WAITING)) {
			deckView.displayPlayOptions();
			result = client.getRoundResult();
			deckView.setRoundTick(roundTime);
		}else if(event.getPropertyName().equals(MessageStage.EVENT_OK)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		gameBoard.dispose();
		deckView.dispose();
		messageStage.dispose();
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
//		Table.drawDebug(messageStage);
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
	
	private InputProcessor inputProcess = new InputProcessor() {
		@Override
		public boolean keyDown(int arg0) {
			if(arg0 == Keys.BACK || arg0 == Keys.BACKSPACE){
				if(deckView.isCardTimerOn()) {
					messageStage.dispCloseMessage();
				}else{
					Gdx.app.exit();
				}
			}
			return false;
		}
		@Override
		public boolean keyTyped(char arg0) {
			return false;
		}
		@Override
		public boolean keyUp(int arg0) {
			return false;
		}
		@Override
		public boolean mouseMoved(int arg0, int arg1) {
			return false;
		}
		@Override
		public boolean scrolled(int arg0) {
			return false;
		}
		@Override
		public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
			return false;
		}
		@Override
		public boolean touchDragged(int arg0, int arg1, int arg2) {
			return false;
		}
		@Override
		public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
			return false;
		}
	};
}
