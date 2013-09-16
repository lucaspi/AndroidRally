package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GdxGame implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private Texture texture;	// TODO: Move this
	private List<Sprite> sprites = new ArrayList<Sprite>();	// TODO: Move this
	
	@Override
	public void create() {	
				
		camera = new OrthographicCamera(480, 800);
		camera.position.set(240, 400, 0f);
		camera.update();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		texture = new Texture(Gdx.files.internal("textures/testTile.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion factoryFloor = new TextureRegion(texture, 0, 0, 
				texture.getWidth()/2, texture.getHeight());
		TextureRegion dockFloor = new TextureRegion(texture, texture.getWidth()/2, 0, 
				texture.getWidth()/2, texture.getHeight());
		
		// TODO: Move this
		for(int x = 0; x < 12; x++) {
			for(int y = 0; y < 12; y++) {
				Sprite s = new Sprite(factoryFloor);
				s.setSize(40, 40);
				s.setPosition(40 * x, 760 - 40 * y);
				sprites.add(s);
			}
			for(int y = 0; y < 4; y++) {
				Sprite s = new Sprite(dockFloor);
				s.setSize(40, 40);
				s.setPosition(40 * x, 760 - 40 * y - 40 * 12);
				sprites.add(s);
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();	
		for(Sprite s : sprites) {
			s.draw(batch);
		}
		batch.end();
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
