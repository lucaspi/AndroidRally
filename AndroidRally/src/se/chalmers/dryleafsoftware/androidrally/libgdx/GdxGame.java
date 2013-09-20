package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector;


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

		Gdx.input.setInputProcessor(gameBoard);
		Gdx.input.setInputProcessor(cardDeck);
		
		gameBoard.setCamera(boardCamera);
		cardDeck.setCamera(cardCamera);

		gameController = new GameController(this);
		Gdx.input.setInputProcessor(new GestureDetector(gameController));
				
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
	
	public BoardView getBoardView() {
		return this.gameBoard;
	}
	
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

	public OrthographicCamera getBoardCamera() {
		return this.boardCamera;
	}

	public OrthographicCamera getCardCamera() {
		return this.cardCamera;
	}
}
