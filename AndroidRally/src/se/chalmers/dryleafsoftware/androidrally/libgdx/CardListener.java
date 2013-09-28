package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Listens to input from the cards the player can choose and interact with.
 * 
 * @author
 *
 */
public class CardListener extends ActorGestureListener {

	private DeckView deckView;
	private CardView touchedCard;
	private final float LONGPRESS_DURATION = 0.5f;

	/**
	 * Creates a new instance which will handle the specified deck of cards.
	 * @param deck The deck of cards to handle.
	 */
	public CardListener(DeckView deck) {
		this.getGestureDetector().setLongPressSeconds(LONGPRESS_DURATION);
		this.deckView = deck;
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {
		if (touchedCard == null) {
			deckView.moveCard((CardView) event.getListenerActor());
		}
	}

	@Override
	public void pan(InputEvent event, float x, float y, float deltaX,
			float deltaY) {
		if (touchedCard != null) {
			setCorrectPositionOfCard(touchedCard, Gdx.input.getX(), Gdx.input.getY());
		} else if (deckView.getCardDeckWidth() > 480) {
			deckView.setPositionX(deckView.getPositionX() + (int) deltaX);
		}
	}

	@Override
	public boolean longPress(Actor actor, float x, float y) {
		touchedCard = (CardView) actor;
		setCorrectPositionOfCard(touchedCard, Gdx.input.getX(), Gdx.input.getY());
		return false;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (touchedCard != null) {
			deckView.placeCardAtPosition(touchedCard);
			touchedCard = null;
		}
	}

	/**
	 * 
	 * @param card
	 * @param x
	 * @param y
	 */
	public void setCorrectPositionOfCard(CardView card, float x, float y) {
		Vector3 pos = new Vector3(x, y, 0);
		deckView.getCamera().unproject(pos);
		card.setPosition(pos.x, pos.y);
	}
}
