package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This stage holds all the cards the player has to play with.
 * 
 * @author
 * 
 */
public class DeckView extends Stage {

	private List<CardView> deckCards = new ArrayList<CardView>();
	private List<CardView> chosenCards = new ArrayList<CardView>();
	private int position;
	private CardListener cl;

	/**
	 * Creates a new default instance.
	 */
	public DeckView() {
		super();
		cl = new CardListener(this);
	}

	/**
	 * Creates a deck with the supplied texture
	 * 
	 * @param texture
	 *            The texture to be applied
	 */
	public void createDeck(Texture texture) {
		Image deck = new Image(new TextureRegion(texture, 0, 0, 512, 256));
		deck.setPosition(0, 0);
		addActor(deck);
	}

	/**
	 * Sets which cards the deck should display.
	 * 
	 * @param list
	 *            The cards the deck should display.
	 */
	public void setDeckCards(List<CardView> list) {
		this.deckCards = list;
		for (int i = 0; i < list.size(); i++) {
			CardView cv = list.get(i);
			cv.setPosition((cv.getWidth() + 10) * i, 0);
			addActor(cv);
		}
		for (CardView cv : deckCards) {
			cv.addListener(cl);
		}
	}

	/**
	 * Returns the cards added to the register
	 * 
	 * @return
	 */
	public List<CardView> getChosenCards() {
		return this.chosenCards;
	}

	/**
	 * Rerenders all the player's card at their correct positions.
	 */
	public void updateCards() {
		updateChosenCards();
		updateDeckCards();
	}

	/**
	 * Rerenders all the cards added to the register.
	 */
	public void updateChosenCards() {
		for (int i = 0; i < this.chosenCards.size(); i++) {
			CardView cv = this.chosenCards.get(i);
			cv.setPosition(
					240 - this.getChosenCardDeckWidth() / 2
							+ (cv.getWidth() + 10) * i, cv.getHeight() + 10);
		}
	}

	/**
	 * Renders all the cards not yet added to the register
	 */
	public void updateDeckCards() {
		if (this.getCardDeckWidth() < 480) {
			this.position = 240 - this.getCardDeckWidth() / 2;
		} else {
			if (this.position > 0) {
				this.position = 0;
			} else if (this.position + getCardDeckWidth() < 480) {
				this.position = 480 - getCardDeckWidth();
			}
		}
		Collections.sort(this.deckCards);
		for (int i = 0; i < this.deckCards.size(); i++) {
			CardView cv = this.deckCards.get(i);
			cv.setPosition((cv.getWidth() + 10) * i + this.position, 0);
		}
	}

	/**
	 * Sets the X-coordinate of the leftmost card not yet added to a register.
	 * 
	 * @param position
	 */
	public void setPositionX(int position) {
		this.position = position;
		updateDeckCards();
	}

	/**
	 * Gives the X-coordinate of the leftmost card not yet added to a register.
	 * 
	 * @return
	 */
	public int getPositionX() {
		return this.position;
	}

	/**
	 * Gives the total width it takes to render the cards not yet added to a
	 * register.
	 * 
	 * @return
	 */
	public int getCardDeckWidth() {
		return this.deckCards.size()
				* ((int) this.deckCards.get(0).getWidth() + 10) - 10;
	}

	/**
	 * Returns the total width it takes to render the cards added to the
	 * register
	 * 
	 * @return
	 */
	public int getChosenCardDeckWidth() {
		if (this.chosenCards.size() != 0) {
			return this.chosenCards.size()
					* ((int) this.chosenCards.get(0).getWidth() + 10) - 10;
		} else {
			return 0;
		}
	}

	/**
	 * Moves a card to either the chosen cards or deck cards when it is tapped
	 * 
	 * @param card
	 *            The card that was tapped
	 */
	public void moveCard(CardView card) {
		if (chosenCards.remove(card)) {
			deckCards.add(card);
		} else if (chosenCards.size() < 5) {
			if (deckCards.remove(card)) {
				chosenCards.add(card);
			}
		}
		updateCards();
	}

	/**
	 * Places a card at the correct place when dragged in place
	 * 
	 * @param card
	 *            The card to be placed
	 * @param x
	 *            The x coordinate of the card
	 * @param y
	 *            The y coordinate of the card
	 * @return
	 */
	public void placeCardAtPosition(CardView card) {
		System.out.println(card.getX() + " " + card.getY());
		if (card.getY() > 90 && card.getY() < 240) {
			if (chosenCards.size() == 0) {
				moveCard(card);
				return;
			} else if (card.getX() > chosenCards.get(chosenCards.size()-1).getX()) {
				if (chosenCards.size() < 5 && deckCards.remove(card)) {
					chosenCards.add(chosenCards.size(), card);
					updateCards();
					return;
				} else if (chosenCards.remove(card)) {
					chosenCards.add(chosenCards.size(), card);
					updateCards();
					return;
				}
			} else if (chosenCards.size() <= 5) {
				for (int i = 0; i < chosenCards.size(); i++) {
					if (card.getX() < chosenCards.get(i).getX()) {
						if (chosenCards.size() < 5 && deckCards.remove(card)) {
							chosenCards.add(i, card);
							updateCards();
							return;
						} else if (chosenCards.contains(card) && chosenCards.indexOf(card) < i && chosenCards.remove(card)) {
							chosenCards.add(i-1, card);
							updateCards();
							return;
						} else if (chosenCards.remove(card)) {
							chosenCards.add(i, card);
							updateCards();
							return;
						}
					}
				}
			}
		} else if (card.getY() < 90) {
			if (chosenCards.remove(card)) {
				deckCards.add(card);
			}
		}
		updateCards();
	}

}
