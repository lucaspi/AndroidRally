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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class GdxGame implements ApplicationListener {
	
	private OrthographicCamera boardCamera, cardCamera;
	private SpriteBatch spriteBatch;
	private Texture texture, cardTexture;
	private GameController gameController;
	private Stage boardStage, cardStage;
	
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
			{"", "37", "", "1", "", "", "", "233", "", "", "1", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "233", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "4", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "133", "", "", "", "", "", "", "", ""},
			{"", "5", "", "", "", "", "", "133", "", "", "", "1", "", "", "", ""},
			{"", "", "", "", "103", "103", "103", "133:103", "", "", "", "", "", "", "", ""},
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
		
		cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		
		spriteBatch = new SpriteBatch();	
		
		texture = new Texture(Gdx.files.internal("textures/testTile.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		BoardView gameBoard = new BoardView();
		gameBoard.createBoard(texture, testmap);
		
		cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
		cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		DeckView deckBoard = new DeckView();
		deckBoard.CreateBoard(cardTexture);
		
		// TODO: move this
		TextureRegion playerTexture1 = new TextureRegion(texture, 0, 64, 
				64, 64);
		PlayerPieceView player1 = new PlayerPieceView(1, playerTexture1);
		player1.setPosition(80, 800 - 160);
		player1.setOrigin(20, 20);
		gameBoard.addPlayer(player1);
		// TODO: move this
		TextureRegion playerTexture2 = new TextureRegion(texture, 64, 64, 
				64, 64);
		PlayerPieceView player2 = new PlayerPieceView(2, playerTexture2);
		player2.setPosition(160, 400);
		player2.setOrigin(20, 20);
		gameBoard.addPlayer(player2);
		
		boardStage = new Stage();
		Gdx.input.setInputProcessor(boardStage);
		gameBoard.setVisible(true);
		gameBoard.setPosition(0, 0);
		gameBoard.setSize(200, 200);
		boardStage.addActor(gameBoard);
		
		boardStage.setCamera(boardCamera);
		
		cardStage = new Stage();
		Gdx.input.setInputProcessor(cardStage);
		deckBoard.setVisible(true);
		cardStage.addActor(deckBoard);
		
		cardStage.setCamera(cardCamera);
		
		gameController = new GameController(this);
		Gdx.input.setInputProcessor(new GestureDetector(gameController));
				
		PlayerPieceView p = gameBoard.getPlayer(1);
		p.addAction(Actions.sequence(Actions.moveBy(40, 0, 2),
				Actions.parallel(Actions.fadeOut(2), Actions.scaleTo(0.3f, 0.3f, 2))));
		
		boardStage.addActor(gameBoard.getPlayer(1));
		
//		Gdx.graphics.requestRendering();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		boardStage.dispose();
		cardStage.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
		boardStage.act(Gdx.graphics.getDeltaTime());
		boardStage.draw();
		cardStage.draw();
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
