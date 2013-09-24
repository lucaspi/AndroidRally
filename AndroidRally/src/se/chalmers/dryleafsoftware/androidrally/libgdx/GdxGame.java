package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector;

/**
 * 
 * 
 * @author
 *
 */
public class GdxGame implements ApplicationListener {

	private OrthographicCamera boardCamera, cardCamera;
	private Texture deckTexture;
	private GameController gameController;
	private BoardView gameBoard;
	private DeckView cardDeck;
	
	@Override
	public void create() {
		// Turn off rendering loop to save battery
		// Gdx.graphics.setContinuousRendering(false);

		boardCamera = new OrthographicCamera(480, 800);
		boardCamera.zoom = 1.0f;
		boardCamera.position.set(240, 400, 0f);
		boardCamera.update();

		cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();

		deckTexture = new Texture(Gdx.files.internal("textures/woodenDeck.png"));
		deckTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		gameBoard = new BoardView();
		
		cardDeck = new DeckView();
		cardDeck.createDeck(deckTexture);

		gameBoard.setCamera(boardCamera);
		cardDeck.setCamera(cardCamera);

		gameController = new GameController(this);
		InputMultiplexer im = new InputMultiplexer(gameBoard, cardDeck, new GestureDetector(20, 0.6f, 1.1f, 0.15f, gameController));
		Gdx.input.setInputProcessor(im);
				
//		Gdx.graphics.requestRendering();
	}

	@Override
	public void dispose() {
		gameBoard.dispose();
		cardDeck.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gameBoard.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		gameBoard.draw();
		cardDeck.draw();
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
	 * Gives the camera used by the board.
	 * @return The camera used by the board.
	 */
	public OrthographicCamera getBoardCamera() {
		return this.boardCamera;
	}
}
