package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This will render a dock, which is a starting point for a robot.
 * 
 * @author
 *
 */
public class DockView extends Image implements Comparable<DockView> {
	
	private final int number;
	private final BitmapFont font;
	
	/**
	 * Creates a new dock which will use the specified texture and display the specified
	 * number.
	 * @param texture The texture to use.
	 * @param number The number to display.
	 */
	public DockView(TextureRegion texture, int number) {
		super(texture);
		this.number = number;
		font = new BitmapFont();
	}
	
	/**
	 * Gives the number of the dock. Each dock has a unique number.
	 * @return The number of the dock.
	 */
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		super.draw(spriteBatch, f);
		font.draw(spriteBatch, "" + number, getX() + 16, getY() + 26);
	}

	@Override
	public int compareTo(DockView that) {
		return that.getNumber() - this.getNumber();
	}
}
