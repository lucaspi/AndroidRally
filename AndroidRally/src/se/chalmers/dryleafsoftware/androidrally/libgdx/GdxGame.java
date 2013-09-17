package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GdxGame implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private BoardView gameBoard;
	private Texture texture;
	
	@Override
	public void create() {					
		camera = new OrthographicCamera(480, 800);
		camera.position.set(240, 400, 0f);
		camera.update();
		spriteBatch = new SpriteBatch();	
		
		texture = new Texture(Gdx.files.internal("textures/testTile.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		gameBoard = new BoardView();		
		gameBoard.createBoard(texture);
		
		TextureRegion playerTexture1 = new TextureRegion(texture, 0, 64, 
				64, 64);
		PlayerPieceView player1 = new PlayerPieceView(1, playerTexture1);
		player1.setPosition(80, 800 - 160);
		gameBoard.addPlayer(player1);
		
		TextureRegion playerTexture2 = new TextureRegion(texture, 64, 64, 
				64, 64);
		PlayerPieceView player2 = new PlayerPieceView(2, playerTexture2);
		player2.setPosition(160, 400);
		gameBoard.addPlayer(player2);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();	
		gameBoard.render(spriteBatch);
		spriteBatch.end();
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
}
