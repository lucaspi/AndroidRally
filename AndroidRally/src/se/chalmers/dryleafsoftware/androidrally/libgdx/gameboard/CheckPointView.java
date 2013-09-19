package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * 
 * 
 * @author 
 *
 */
public class CheckPointView extends Image {

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
	public void draw(SpriteBatch spriteBatch, float f) {	
		super.draw(spriteBatch, f);
		font.draw(spriteBatch, "" + number, getX() + 20, getY() + 20);
	}
}
