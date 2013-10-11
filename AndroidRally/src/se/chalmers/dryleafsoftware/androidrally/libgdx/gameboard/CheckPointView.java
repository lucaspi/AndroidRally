package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This view will render a checkpoint, a board element which the robots must get to.
 * 
 * @author 
 *
 */
public class CheckPointView extends Image implements Comparable<CheckPointView> {

	private final int number;
	private final BitmapFont font;
	
	/**
	 * Creates a new checkpoint with the specified texture and the specified number.
	 * @param number The number of the checkpoint.
	 */
	public CheckPointView(int number) {
		super();
		this.number = number;
		font = new BitmapFont();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		super.draw(spriteBatch, f);
		font.draw(spriteBatch, "" + number, getX() + 7, getY() + 35);
	}
	
	@Override
	public int compareTo(CheckPointView that) {
		return this.number - that.number;
	}
}
