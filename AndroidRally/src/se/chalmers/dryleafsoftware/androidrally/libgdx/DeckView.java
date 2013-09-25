package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
	private CardListener cl;
	private Table container;
	private final Table lowerArea, upperArea, statusBar;
	private final Table playPanel; // The panel with [Play] [Step] [Skip]
	
	/**
	 * Creates a new default instance.
	 */
	public DeckView() {
		super();
		Texture deckTexture = new Texture(Gdx.files.internal("textures/woodenDeck.png"));
		deckTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		cl = new CardListener(this);
		
		// Default camera
		OrthographicCamera cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		setCamera(cardCamera);
		
		Image deck = new Image(new TextureRegion(deckTexture, 0f, 0f, 1f, 1f));
		deck.setPosition(0, 0);
		deck.setSize(480, 320);
		addActor(deck);
		
		container = new Table();
		container.debug();
		container.setSize(480, 320);
		container.setLayoutEnabled(false);
		addActor(container);
		
		lowerArea = new Table();
		lowerArea.debug();
		lowerArea.setSize(480, 120);
		lowerArea.setLayoutEnabled(false);
		container.add(lowerArea);
		
		upperArea = new Table();
		upperArea.debug();
		upperArea.setSize(480, 120);
		upperArea.setPosition(0, 120);
		container.add(upperArea);
		
		statusBar = new Table();
		statusBar.debug();
		statusBar.setSize(480, 80);
		statusBar.setPosition(0, 240);
		container.add(statusBar);
		
		playPanel = buildPlayPanel(480, 120);
	}
	
	private Table buildPlayPanel(int w, int h) {
		Table table = new Table();
		table.setSize(w, h);
		return table;
	}

	/**
	 * Sets which cards the deck should display.
	 * @param list The cards the deck should display.
	 */
	public void setDeckCards(List<CardView> list) {
		Table holder = new Table();
		holder.setSize(480, 160);
		holder.setLayoutEnabled(false);
		lowerArea.clear();
		lowerArea.add(holder);
		this.deckCards = list;
		for (int i = 0; i < list.size(); i++) {
			CardView cv = list.get(i);
			cv.setPosition((cv.getWidth() + 10) * i, 0);
			holder.add(cv);
		}
		for (CardView cv : deckCards) {
			cv.addListener(cl);
		}
	}
	
	public void displayPlayOptions() {
		
	}
	
	public List<CardView> getChosenCards() {
		return this.chosenCards;
	}
	
	/**
	 * Adds the specified card to the registers.
	 * @param card The card to add to the registers.
	 */
	public void chooseCard(CardView card) {
		if (deckCards.remove(card)) {
			chosenCards.add(card);
		}
		updateCards();
	}

	public void removeChosenCard(CardView card) {
		if (chosenCards.remove(card)) {
			deckCards.add(card);
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
	public void updateChosenCards() {
		for (int i = 0; i < this.chosenCards.size(); i++) {
			CardView cv = this.chosenCards.get(i);
			cv.setPosition(240 - this.getChosenCardDeckWidth()/2 + (cv.getWidth() + 10) * i, cv.getHeight() + 10);
		}
	}

	public void updateDeckCards() {
		if (this.getCardDeckWidth() < 480) {
			this.position = 240 - this.getCardDeckWidth()/2;
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
		return this.deckCards.size()
				* ((int) this.deckCards.get(0).getWidth() + 10) - 10;
	}

	public int getChosenCardDeckWidth() {
		if (this.chosenCards.size() != 0) {
			return this.chosenCards.size()
					* ((int) this.chosenCards.get(0).getWidth() + 10) - 10;
		} else {
			return 0;
		}
	}

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

}
