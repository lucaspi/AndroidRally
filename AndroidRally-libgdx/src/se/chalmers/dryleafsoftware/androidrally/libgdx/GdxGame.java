package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GdxGame implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create() {	
				
		camera = new OrthographicCamera(480, 800);
		camera.position.set(240, 400, 0f);
		camera.update();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
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
		batch.end();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(0, 0, 480, 800);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.filledRect(50, 50, 480-100, 800-100);
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Circle);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.circle(240, 400, 100);
		shapeRenderer.end();
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
