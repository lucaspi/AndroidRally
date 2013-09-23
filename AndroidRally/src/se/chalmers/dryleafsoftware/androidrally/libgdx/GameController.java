package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

/**
 * This is the client-side controller which handles the game's logic.
 * 
 * @author
 *
 */
public class GameController implements GestureListener, PropertyChangeListener {

	private final GdxGame game;
	private OrthographicCamera boardCamera;
	private boolean modifyBoard;
	private final Client client;
	private float maxZoom;
	private Vector3 defaultPosition;
	private DeckView deckView;
	private List<GameAction> actions;

	/**
	 * Creates a new instance which will control the specified game.
	 * @param game The game to control.
	 */
	public GameController(GdxGame game) {
		this.game = game;
		this.boardCamera = this.game.getBoardCamera();
		this.client = new Client(1);
		this.deckView = game.getDeckView();
		game.addListener(this);
		
		maxZoom = 0.4f;
		defaultPosition = new Vector3(240, 400, 0f);
		
		// Only load the textures once.
		Texture boardTexture = new Texture(Gdx.files.internal("textures/testTile.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Texture cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		game.getBoardView().createBoard(boardTexture, client.getMap());
		
		for(RobotView player : client.getRobots(boardTexture, game.getBoardView().getDocksPositions())) {
			game.getBoardView().addRobot(player);
		}		
		
//		PlayerView p = game.getBoardView().getPlayer(1);
//		p.addAction(Actions.sequence(Actions.moveBy(40, 0, 2),
//				Actions.parallel(Actions.fadeOut(2), Actions.scaleTo(0.3f, 0.3f, 2))));
	
		this.deckView.setDeckCards(client.getCards(cardTexture));
		
		actions = client.getRoundResult();
//		List<GameAction> actions = client.getRoundResult();
//		for(GameAction a : actions) {
//			a.action(game.getBoardView().getRobots());
//		}

		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {			
			@Override
			public void run() {
				client.sendCard(deckView.getChosenCards());
			}
		}, 3);
		timer.start();
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
		}
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int arg3) {
		if(modifyBoard && arg2 == 2) {
			if(boardCamera.zoom == maxZoom){
				boardCamera.zoom = 1.0f;
				boardCamera.position.set(defaultPosition);
			} else {
				boardCamera.zoom = maxZoom;
				boardCamera.position.set(arg0, arg1, 0f);
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		modifyBoard = arg1 < Gdx.graphics.getHeight() * 2 / 3;
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		if (modifyBoard) {
			if (boardCamera.zoom == 1.0f) {
				boardCamera.translate(0f, arg3);
			} else {
				boardCamera.translate(-arg2 * boardCamera.zoom, arg3
						* boardCamera.zoom);
			}
		}
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		if (modifyBoard) {
			if (arg1 - arg0 > 0 && boardCamera.zoom > 0.4f) {
				boardCamera.zoom -= 0.03f;
			} else if (arg0 - arg1 > 0 && boardCamera.zoom < 1.0f) {
				boardCamera.zoom += 0.03f;
			} else if (boardCamera.zoom == 1.0f) {
				boardCamera.position.set(240, 400, 0f);
			}
			// checkCameraBounds();
		}
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
