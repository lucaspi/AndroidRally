package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DeckView extends Table {

	private List<Sprite> deckCards = new ArrayList<Sprite>();
	private List<Sprite> chosenCards = new ArrayList<Sprite>();

	class CardView extends Sprite {
		private int priority;
		private BitmapFont font;

		public CardView(TextureRegion tr, int priority) {
			super(tr);
			this.priority = priority;
			font = new BitmapFont();
		}

		@Override
		public void draw(SpriteBatch spriteBatch) {
			super.draw(spriteBatch);
			font.draw(spriteBatch, "" + priority, getX() + (getWidth() / 2
					- font.getWrappedBounds("" + priority, 200f).width/2), getY()
					+ getHeight());
		}
	}

	public DeckView() {
		super();
	}

	public void CreateBoard(Texture texture) {
		Sprite card = new CardView(new TextureRegion(texture, 0, 0, 64, 128),
				666);
		card.setPosition(0, 0);
		deckCards.add(card);
	}

	public void setDeckCards(List<Sprite> cards) {

	}

	@Override
	public void draw(SpriteBatch sb, float arg) {
		super.draw(sb, arg);
		for (Sprite s : deckCards) {
			s.draw(sb);
		}
	}
}
