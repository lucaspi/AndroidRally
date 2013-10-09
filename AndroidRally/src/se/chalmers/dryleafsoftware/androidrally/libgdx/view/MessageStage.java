package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MessageStage extends Stage { 
	
	private final Table container;
	private final Table gameOverPanel;
	private final Table gameWonPanel;

	public MessageStage() {
		super();

		// Default camera
		OrthographicCamera cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		setCamera(cardCamera);

		Texture buttonTexture = new Texture(Gdx.files.internal("textures/button.png"));
		buttonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextButtonStyle style = new TextButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttonTexture, 0, 0, 32, 32)),
				new TextureRegionDrawable(new TextureRegion(buttonTexture, 0, 32, 32, 32)),
				null);
		style.font = new BitmapFont();
		
		this.container = new Table();
		container.debug();
		container.setSize(480, 700);
		container.setLayoutEnabled(false);
		addActor(container);
		
		this.gameOverPanel = new Table();
		gameOverPanel.setSize(280, 100);
		gameOverPanel.setPosition(100, 500);
		gameOverPanel.debug();
				
        TextButton gameOverButton = new TextButton("GAME OVER ! ! !", style);
        gameOverPanel.add(gameOverButton); // Border
        gameOverButton.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
//    			pcs.firePropertyChange(TIMER_ROUND, 0, 1);
    		}
    	});
		
		this.gameWonPanel = new Table();
		gameWonPanel.setSize(280, 100);
		gameWonPanel.setPosition(100, 500);
		gameWonPanel.debug();
		
		TextButton gameWonButton = new TextButton("GAME WON BY SOMEONE ! ! !", style);
		gameWonPanel.add(gameOverButton); // Border
        gameWonButton.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
//    			pcs.firePropertyChange(TIMER_ROUND, 0, 1);
    		}
    	});       
	}
	
	public void dispGameOver(List<RobotView> robots) {
		System.out.println("Displaying game over");
		container.add(gameOverPanel);
	}
	
	public void dispGameWon(List<RobotView> robots) {
		System.out.println("Displaying game won");
		container.add(gameWonPanel);
	}
}
