package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * 
 * 
 * @author
 *
 */
public class GdxGame implements ApplicationListener {

	private Texture deckTexture;
	private GameController gameController;
	private BoardView gameBoard;
	private DeckView cardDeck;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public static final String EVENT_UPDATE = "e_update";
	
	@Override
	public void create() {
		gameBoard = new BoardView();		
		cardDeck = new DeckView();

		gameController = new GameController(this);
		InputMultiplexer im = new InputMultiplexer(gameBoard, cardDeck, new GestureDetector(20, 0.6f, 1.1f, 0.15f, gameController));
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void dispose() {
		gameBoard.dispose();
		cardDeck.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gameBoard.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		gameBoard.draw();
		cardDeck.draw();
		pcs.firePropertyChange(EVENT_UPDATE, 0, 1);
		
		Table.drawDebug(cardDeck);
		Table.drawDebug(gameBoard);
	}
	
	/**
	 * Gives the board.
	 * @return The board.
	 */
	public BoardView getBoardView() {
		return this.gameBoard;
	}
	
	/**
	 * Gives the player's card deck.
	 * @return The player's card deck.
	 */
	public DeckView getDeckView() {
		return this.cardDeck;
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
	
	public void addListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
