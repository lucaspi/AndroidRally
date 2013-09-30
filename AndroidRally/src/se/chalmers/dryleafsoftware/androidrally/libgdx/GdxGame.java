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
		//Creates an input multiplexer to be able to use multiple listeners
		InputMultiplexer im = new InputMultiplexer(gameBoard, cardDeck);
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
	
	/**
	 * Adds the specified listener.
	 * 
	 * @param listener The listener to add
	 */
	public void addListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	/**
	 * Removes the specified listener
	 * 
	 * @param listener The listener to remove
	 */
	public void removeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
