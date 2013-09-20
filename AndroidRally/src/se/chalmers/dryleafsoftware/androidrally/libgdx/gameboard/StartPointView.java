package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StartPointView extends Image {
	
	private int number;
	private BitmapFont font;
	
	public StartPointView(TextureRegion texture, int number) {
		super(texture);
		this.number = number;
		font = new BitmapFont();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		super.draw(spriteBatch, f);
		font.draw(spriteBatch, "" + number, getX() + 20, getY() + 20);
	}
}
