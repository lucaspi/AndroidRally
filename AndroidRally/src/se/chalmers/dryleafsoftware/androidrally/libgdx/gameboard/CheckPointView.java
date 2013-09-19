package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * 
 * @author 
 *
 */
public class CheckPointView extends Sprite {

	private int number;
	private BitmapFont font;
	
	/**
	 * Creates a new checkpoint with the specified texture and the specified number.
	 * @param texture The texture of the checkpoint.
	 * @param number The number of the checkpoint.
	 */
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
