package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardView extends Image {
	private int priority;
	private BitmapFont font;

	public CardView(TextureRegion tr, int priority) {
		super(tr);
		this.priority = priority;
		font = new BitmapFont();
	}

	@Override
	public void draw(SpriteBatch spriteBatch, float f) {
		super.draw(spriteBatch, f);
		font.draw(
				spriteBatch,
				"" + priority,
				getX()
						+ (getWidth() / 2 - font.getWrappedBounds(""
								+ priority, 200f).width / 2), getY()
						+ getHeight());
	}
}