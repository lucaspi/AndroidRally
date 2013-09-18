package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CheckPointView extends Sprite {

	private int number;
	private BitmapFont font;
	
	public CheckPointView(TextureRegion texture, int number) {
		super(texture);
		this.number = number;
		font = new BitmapFont();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {	
		super.draw(spriteBatch);
		font.draw(spriteBatch, "" + number, getX() + 20, getY() + 20);
	}
}
