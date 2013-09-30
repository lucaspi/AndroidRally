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
	
	// Time to choose cards.
	private static final int CARDTIME = 40;
	// Time between rounds.
	private static final int ROUNDTIME = 120;
	
	private final Texture boardTexture, cardTexture;
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
		deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);
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
				a.cleanUp(game.getBoardView().getRobots());
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
				actions.get(0).cleanUp(game.getBoardView().getRobots());
				actions.remove(0);
			}
			if(!actions.isEmpty()) {
				actions.get(0).action(game.getBoardView().getRobots());
				if(actions.get(0).getPhase() == GameAction.PHASE_BOARD_ELEMENT_CONVEYER) {
					game.getBoardView().stopAnimations();
					game.getBoardView().setAnimate((GameAction.PHASE_BOARD_ELEMENT_CONVEYER * 10 
							+ actions.get(0).getSubPhase()));
				}else if(actions.get(0).getPhase() == GameAction.PHASE_BOARD_ELEMENT_GEARS) {
					game.getBoardView().stopAnimations();
					game.getBoardView().setAnimate(4);
				}else if(actions.get(0).getPhase() == GameAction.PHASE_LASER) {
					game.getBoardView().stopAnimations();
					game.getBoardView().setAnimate(5);
				}
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
		
		if(event.getPropertyName().equals(GdxGame.EVENT_UPDATE)) {
			// Called from the LibGDX game instance.
			update();
		}else if(event.getPropertyName().equals(DeckView.EVENT_PLAY)) {
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
			System.out.println("Sending cards");
			client.sendCard(deckView.getChosenCards());
			deckView.displayWaiting();
			currentStage = Stage.WAITING;
			deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);
		}else if(event.getPropertyName().equals(DeckView.TIMER_ROUND)
				&& currentStage.equals(Stage.WAITING)) {
			runTimerStamp = TimeUtils.millis() +  ROUNDTIME*1000;
			System.out.println("Getting actions");
			deckView.displayPlayOptions();
			result = client.getRoundResult();		
			deckView.setTimer((int)(runTimerStamp - TimeUtils.millis()) / 1000, DeckView.TIMER_ROUND);
		}
	}
}
