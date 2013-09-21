package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardView extends Image implements Comparable<CardView> {
	
	private int priority;
	private BitmapFont font;
	private final int index;

	public CardView(TextureRegion tr, int priority, int index) {
		super(tr);
		this.priority = priority;
		this.index = index;
		font = new BitmapFont();
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void draw(SpriteBatch spriteBatch, float f) {
		super.draw(spriteBatch, f);
		font.draw(
				spriteBatch,
				"" + priority,
				getX() + (getWidth() / 2 - font.getWrappedBounds("" + priority, 200f).width / 2), 
				getY() + getHeight() - 5);
	}

	@Override
	public int compareTo(CardView that) {
		return that.getPriority() - this.getPriority();
	}
}