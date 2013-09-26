package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class CardListener extends ActorGestureListener {

	private DeckView deckView;
	private CardView touchedCard;
	private final float LONGPRESS_DURATION = 0.5f;

	public CardListener(DeckView deck) {
		this.getGestureDetector().setLongPressSeconds(LONGPRESS_DURATION);
		this.deckView = deck;
	}

	public void tap(InputEvent event, float x, float y, int count, int button) {
		if (touchedCard == null) {
			deckView.moveCard((CardView) event.getListenerActor());
		}
	}

	public void pan(InputEvent event, float x, float y, float deltaX,
			float deltaY) {
		if (touchedCard != null) {
			setCorrectPositionOfCard(touchedCard, Gdx.input.getX(), Gdx.input.getY());
		} else if (deckView.getCardDeckWidth() > 480) {
			deckView.setPositionX(deckView.getPositionX() + (int) deltaX);
		}
	}

	public boolean longPress(Actor actor, float x, float y) {
		touchedCard = (CardView) actor;
		setCorrectPositionOfCard(touchedCard, Gdx.input.getX(), Gdx.input.getY());
		return false;
	}

	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (touchedCard != null) {
			deckView.placeCardAtPosition(touchedCard);
			touchedCard = null;
		}
	}

	public void setCorrectPositionOfCard(CardView card, float x, float y) {
		Vector3 pos = new Vector3(x, y, 0);
		deckView.getCamera().unproject(pos);
		card.setPosition(pos.x, pos.y);
	}
}
