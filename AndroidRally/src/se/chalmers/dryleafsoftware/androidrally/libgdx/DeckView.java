package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * This view holds all the cards the player has to play with.
 * 
 * @author 
 *
 */
public class DeckView extends Stage {

	private List<CardView> deckCards = new ArrayList<CardView>();
	private List<CardView> chosenCards = new ArrayList<CardView>();
	private int position;
	
	/**
	 * Creates a new default instance.
	 */
	public DeckView() {
		super();
	}
	
	public void createDeck(Texture texture) {
		Image deck = new Image(new TextureRegion(texture, 0, 0, 512, 256));
		deck.setPosition(0, 0);
		addActor(deck);
	}

	/**
	 * Sets which cards the deck should display.
	 * @param list The cards the deck should display.
	 */
	public void setDeckCards(List<CardView> list) {
		this.deckCards = list;
		for(int i = 0; i < list.size(); i++) {
			CardView cv = list.get(i);
			cv.setPosition((cv.getWidth() + 10) * i, 0);			
			addActor(cv);
		}
		for(CardView cv : deckCards) {
			cv.addListener(new ActorGestureListener() {
				public void tap(InputEvent event, float x, float y, int count, int button){
					CardView card =  (CardView) event.getListenerActor();
					chooseCard(card);
				}
			});
		}
	}
	
	/**
	 * Rerenders all the cards currently in the registers at their correct positions.
	 */
	public void updateChosenCards() {
		for(int i = 0; i < this.chosenCards.size(); i++) {
			CardView cv = this.chosenCards.get(i);
			cv.setPosition((cv.getWidth() + 10) * i, 100);
		}
	}
	
	/**
	 * Adds the specified card to the registers.
	 * @param card The card to add to the registers.
	 */
	public void chooseCard(CardView card) {
		if(deckCards.remove(card)) {
			chosenCards.add(card);
		}
		updateCards();
	}
	
	/**
	 * Rerenders all the player's card at their correct positions.
	 */
	public void updateCards() {
		updateChosenCards();
		updateDeckCards();
	}
	
	/**
	 * Rerenders all the cards not yet added to a register.
	 */
	public void updateDeckCards() {
		for(int i = 0; i < this.deckCards.size(); i++) {
			CardView cv = this.deckCards.get(i);
			cv.setPosition((cv.getWidth() + 10) * i + position, 0);
		}
	}
	
	/**
	 * Sets the X-coordinate of the cards not yet added to a register.
	 * @param position
	 */
	public void setPositionX(int position) {
		this.position = position;
		updateDeckCards();
	}
	
	/**
	 * Gives the X-coordinate of the cards not yet added to a register.
	 * @return
	 */
	public int getPositionX() {
		return this.position;
	}
	
	/**
	 * Gives the total width it takes to render the cards not yet added to a register.
	 * @return
	 */
	public int getCardDeckWidth() {
		return this.deckCards.size() * ((int)this.deckCards.get(0).getWidth() + 10) - 10;
	}

}
