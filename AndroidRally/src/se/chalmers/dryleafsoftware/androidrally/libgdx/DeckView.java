package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class DeckView extends Stage {

	private List<Image> deckCards = new ArrayList<Image>();
	private List<Image> chosenCards = new ArrayList<Image>();

	class CardView extends Image {
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

	public DeckView() {
		super();
	}

	public void createDeck(Texture texture) {
		Image card = new CardView(new TextureRegion(texture, 0, 0, 64, 128),
				666);
		card.setPosition(0, 0);
		deckCards.add(card);
		addActor(card);
	}

	public void setDeckCards(List<Image> cards) {

	}

}
