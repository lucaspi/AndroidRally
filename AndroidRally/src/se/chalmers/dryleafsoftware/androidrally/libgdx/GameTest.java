package se.chalmers.dryleafsoftware.androidrally.libgdx;

import android.graphics.Camera;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameTest implements ApplicationListener {

	private Stage stage;
	private OrthographicCamera camera;

	public void create () {
	        stage = new Stage();
	        Gdx.input.setInputProcessor(stage);
	        
	        camera = new OrthographicCamera(480, 800);
	        camera.zoom = 1.0f;
	        camera.position.set(240, 400, 0f);
	        camera.update();
	        stage.setCamera(camera);
	        	        
	        // Add widgets to the stage bellow.

	        Texture texture = new Texture(Gdx.files.internal("textures/button.png"));
	        TextureRegion upRegion = new TextureRegion(texture, 0, 0, 32, 32);
	        TextureRegion downRegion = new TextureRegion(texture, 0, 32, 32, 32);
	        BitmapFont buttonFont = new BitmapFont();

	        TextButtonStyle style = new TextButtonStyle();	        
	        style.up = new TextureRegionDrawable(upRegion);
	        style.down = new TextureRegionDrawable(downRegion);
	        style.font = buttonFont;
	        
	        Table scrollTable = new Table();
	        scrollTable.debug();
	        ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
	        ScrollPane pane = new ScrollPane(scrollTable, scrollStyle);
	        
	        Table scrollParent = new Table();
	        scrollParent.add(pane);
	        scrollParent.debug();
	        scrollParent.setPosition(100, 200);
	        scrollParent.setSize(280, 100);
	        stage.addActor(scrollParent);

	        scrollTable.setLayoutEnabled(true);
	        Texture cardTexture = new Texture(Gdx.files.internal("textures/card.png"));
	        Client client = new Client(0);
	        for(CardView c : client.getCards(cardTexture)) {
	        	scrollTable.add(c).pad(5);
	        }
//	        for(int i = 0; i < 100; i++) {
//	        	TextButton button1 = new TextButton("Button " + i, style);
//	        	button1.pad(0, 5, 0, 5); // Internal padding
//	        	scrollTable.add(button1).pad(5); // Border
//	        }
	        
	        camera.zoom = 0.9f;
	        scrollParent.setPosition(240 * (1-camera.zoom), 400 * (1-camera.zoom));
	        scrollParent.setWidth((240 - scrollParent.getX()) * 2);
	}

	public void resize (int width, int height) {
	        stage.setViewport(width, height, true);
	}

	public void render () {
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();

	        Table.drawDebug(stage); 
	}

	public void dispose() {
	        stage.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
