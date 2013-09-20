package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import java.util.Comparator;

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
public class StartPointView extends Image implements Comparable<StartPointView> {
	
	private int number;
	private BitmapFont font;
	
	/**
	 * Creates a new startpoint which will use the specified texture and display the specified
	 * number.
	 * @param texture The texture to use.
	 * @param number The number to display.
	 */
	public StartPointView(TextureRegion texture, int number) {
		super(texture);
		this.number = number;
		font = new BitmapFont();
	}
	
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		super.draw(spriteBatch, f);
		font.draw(spriteBatch, "" + number, getX() + 20, getY() + 20);
	}

	@Override
	public int compareTo(StartPointView that) {
		return that.getNumber() - this.getNumber();
	}
}
