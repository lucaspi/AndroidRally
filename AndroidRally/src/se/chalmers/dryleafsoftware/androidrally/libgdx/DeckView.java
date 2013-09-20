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

	private List<CardView> deckCards = new ArrayList<CardView>();
	private List<CardView> chosenCards = new ArrayList<CardView>();

	public DeckView() {
		super();
	}

	public void createDeck(Texture texture) {
		Image deck = new Image(new TextureRegion(texture, 0, 0, 512, 256));
		deck.setPosition(0, 0);
		addActor(deck);
	}

	public void setDeckCards(List<CardView> list) {
		this.deckCards = list;
	}

}
