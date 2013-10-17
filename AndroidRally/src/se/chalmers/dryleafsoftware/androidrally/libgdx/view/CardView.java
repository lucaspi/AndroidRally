package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A view class which holds one single card.
 * 
 * @author
 * 
 */
public class CardView extends Image implements Comparable<CardView> {

	private int priority;
	private final BitmapFont font;
	private final int index;

	/**
	 * Creates a new instance with the specified texture.
	 * 
	 * @param tr
	 *            The texture of the card.
	 * @param priority
	 *            The priority of the card.
	 * @param index
	 *            The index this card had when it was received from the dealer.
	 * @param font
	 *            The font to use then drawing the card's priority.
	 */
	public CardView(TextureRegion tr, int priority, int index, BitmapFont font) {
		super(tr);
		this.priority = priority;
		this.index = index;
		this.font = font;
	}

	@Override
	public void draw(SpriteBatch spriteBatch, float f) {
		super.draw(spriteBatch, f);
		font.draw(
				spriteBatch,
				"" + priority,
				getX()
						+ (getWidth() / 2 - font.getWrappedBounds(
								"" + priority, 200f).width / 2), getY()
						+ getHeight() - 11);
	}

	/**
	 * Compares two cards by their priority. A positive integer is returned if
	 * the card compared to has the higher priority.
	 * 
	 * @return The difference between the cards priorities
	 */
	@Override
	public int compareTo(CardView that) {
		return that.getPriority() - this.getPriority();
	}

	/**
	 * Gives the index this card had when it was dealt.
	 * 
	 * @return The index this card had when it was dealt.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Gives the priority of the card.
	 * 
	 * @return The priority of the card.
	 */
	public int getPriority() {
		return this.priority;
	}
}