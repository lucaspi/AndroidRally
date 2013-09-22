package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class CardListener extends ActorGestureListener {
	
	private DeckView deckView;
	
	public CardListener (DeckView deck) {
		this.deckView = deck;
	}
	
	public void tap (InputEvent event, float x, float y, int count, int button) {
		deckView.moveCard((CardView) event.getListenerActor());
	}
}
