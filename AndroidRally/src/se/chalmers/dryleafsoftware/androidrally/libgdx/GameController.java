package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * This is the client-side controller which handles the logic of the view classes.
 * 
 * @author
 *
 */
public class GameController implements PropertyChangeListener {

	private final GdxGame game;
	private final Client client;
	private DeckView deckView;
	
	private List<GameAction> actions;
	private RoundResult result;
	
	private final Texture boardTexture, cardTexture;
	private long runTimerStamp = TimeUtils.millis() +  20*1000;
	
	private final Timer timer;
	
	/*
	 * Time to choose cards.
	 */
	private static final int CARDTIME = 10;
	
	/*
	 *  Stages for specifying which stage the game is currently in:
	 *  WAITING: Nothing needs to update.
	 *  ACTIONS: Showing the actions of the last round.
	 *  	- STEP_ACTION: Waits for user input when done showing one card's actions.
	 *  	- PLAY_ACTION: Do not wait for user input, plays all card's action in sequence.
	 *  	- SKIP_ACTION: Skips to the outcome.
	 */	
	private static enum Stage { WAITING, STEP_ACTIONS, PLAY_ACTIONS, SKIP_ACTIONS };
	private Stage currentStage = Stage.WAITING; 

	/**
	 * Creates a new instance which will control the specified game.
	 * @param game The game to control.
	 */
	public GameController(GdxGame game) {
		this.game = game;
		this.client = new Client(1);
		this.deckView = game.getDeckView();
		game.addListener(this);
		deckView.addListener(this);
		
		// Only load the textures once.
		boardTexture = new Texture(Gdx.files.internal("textures/testTile.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
		cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		game.getBoardView().createBoard(boardTexture, client.getMap());
		
		for(RobotView player : client.getRobots(boardTexture, game.getBoardView().getDocksPositions())) {
			game.getBoardView().addRobot(player);
		}		
		
		deckView.displayDrawCard();
		
		timer = new Timer();
		timer.start();
		// TODO: remove this task, as this is only for testing.
		timer.scheduleTask(new Timer.Task() {			
			@Override
			public void run() {
				System.out.println("Getting actions");
				deckView.displayPlayOptions();
				result = client.getRoundResult();					
			}
		}, (int)(runTimerStamp - TimeUtils.millis()) / 1000);	
		deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000);
	}

	/**
	 * The main update loop.
	 */
	private void update() {
		switch(currentStage) {
		case STEP_ACTIONS:
		case PLAY_ACTIONS:
			updateActions();
			break;
		default:
			// Do nothing...
		}
	}
	
	/*
	 * Updates the actions.
	 */
	private void updateActions() {
		// Remove and continue if last action complete.
		if(!actions.isEmpty() && (actions.get(0).isDone() || !actions.get(0).isRunning())) {
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
		if(event.getPropertyName().equals(GdxGame.EVENT_UPDATE)) {
			// Called from the LibGDX game instance.
			update();
		}else if(event.getPropertyName().equals(DeckView.EVENT_PLAY)) {
			// TODO: should play thought all actions.
			currentStage = Stage.PLAY_ACTIONS;
			actions = result.getNextResult();
		}else if(event.getPropertyName().equals(DeckView.EVENT_STEP)) {
			// Displays the next set of actions.
			currentStage = Stage.STEP_ACTIONS;
			actions = result.getNextResult();
		}else if(event.getPropertyName().equals(DeckView.EVENT_SKIP)) {
			// TODO: should skip through all actions.
		}else if(event.getPropertyName().equals(DeckView.EVENT_DRAW_CARDS)) {
			// Displays the cards and waits for the timer task.
			this.deckView.setDeckCards(client.getCards(cardTexture));
			this.deckView.setTimerValue(0, 0, CARDTIME);
			timer.scheduleTask(new Timer.Task() {			
				@Override
				public void run() {
					System.out.println("Sending cards");
					client.sendCard(deckView.getChosenCards());
					deckView.displayWaiting();
					currentStage = Stage.WAITING;
					deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000);
				}
			}, CARDTIME);
		}
	}
}
