package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GdxGame implements ApplicationListener {
	
	private OrthographicCamera boardCamera, cardCamera;
	private SpriteBatch spriteBatch;
	private Texture texture;
	private GameController gameController;
	private Stage boardStage;
	
	/**
	 * Bara "" ger en tom ruta.
	 * "12:33" kommer skapa två elements på den rutan.
	 * entalen står för ID för elementet på den rutan.
	 * tiotalen står för speciella egenskaper för det elementet, 
	 * 		t.ex 33 ger ett rullband (3) + roterat 3 gånger (30) = 33
	 * 		t.ex 12 ger checkpoint (2) + numerordning 1 (10) = 12
	 * 		osv.
	 */
	private String[][] testmap = new String[][] {
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "16", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "14", "", "", "", "5", "", "", "", ""},
			{"", "37", "", "1", "", "", "", "", "", "", "1", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "4", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "33", "", "", "", "", "", "", "", ""},
			{"", "5", "", "", "", "", "", "33", "", "", "", "1", "", "", "", ""},
			{"", "", "", "", "3", "3", "3", "33:3", "", "", "", "", "", "", "", ""},
			{"", "", "36", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "4", "", "", "", "", "", "", "22", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
	};
	
	@Override
	public void create() {	
		// Turn off rendering loop to save battery
//		Gdx.graphics.setContinuousRendering(false);
		
		boardCamera = new OrthographicCamera(480, 800);
		boardCamera.zoom = 1.0f;
		boardCamera.position.set(240, 400, 0f);
		boardCamera.update();
		spriteBatch = new SpriteBatch();	
		
		texture = new Texture(Gdx.files.internal("textures/testTile.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		BoardView gameBoard = new BoardView();		
		gameBoard.createBoard(texture, testmap);
		
		// TODO: move this
		TextureRegion playerTexture1 = new TextureRegion(texture, 0, 64, 
				64, 64);
		PlayerPieceView player1 = new PlayerPieceView(1, playerTexture1);
		player1.setPosition(80, 800 - 160);
		gameBoard.addPlayer(player1);
		// TODO: move this
		TextureRegion playerTexture2 = new TextureRegion(texture, 64, 64, 
				64, 64);
		PlayerPieceView player2 = new PlayerPieceView(2, playerTexture2);
		player2.setPosition(160, 400);
		gameBoard.addPlayer(player2);
		
		boardStage = new Stage();
		Gdx.input.setInputProcessor(boardStage);
		gameBoard.setVisible(true);
		gameBoard.setPosition(0, 0);
		gameBoard.setSize(200, 200);
		boardStage.addActor(gameBoard);
		
		boardStage.setCamera(boardCamera);
		
		gameController = new GameController(this);
		Gdx.input.setInputProcessor(new GestureDetector(gameController));
		
//		Gdx.graphics.requestRendering();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		boardStage.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
		boardStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		boardStage.draw();
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
