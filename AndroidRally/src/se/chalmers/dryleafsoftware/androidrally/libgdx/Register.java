package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Register used when programming a robot.
 * 
 * @author
 *
 */
public class Register extends Table {
	
	private CardView card;
	private final Image padLock;
	
	/**
	 * Creates a new instance with the specifying texture and the specified number.
	 * @param texture The texture to use.
	 * @param i The index of the register.
	 */
	public Register(Texture texture, int i) {
		super();
		this.setLayoutEnabled(false);
		
		Image background = new Image(new TextureRegion(texture, 64 * i, 0, 64, 90));
		background.setSize(78, 110);
		
		padLock = new Image(new TextureRegion(texture, 320, 0, 64, 90));
		padLock.setSize(78, 110);
		
		this.setSize(background.getWidth(), background.getHeight());
		this.add(background);
	}
	
	/**
	 * Sets if this register should display its padlock.
	 * @param locked Set to <code>true</code> to display its padlock.
	 */
	public void setLocked(boolean locked) {
		if(locked) {
			add(padLock);
		}else{
			removeActor(padLock);
		}
	}

	/**
	 * Sets the card in this register.
	 * @param card The new card in this register.
	 */
	public void setCard(CardView card) {
		this.card = card;
		card.setPosition(0, 0);
		this.add(card);
	}
	
	/**
	 * Gives the card in this register.
	 * @return The card in this register.
	 */
	public CardView getCard() {
		return this.card;
	}
	
	/**
	 * Removes the card in this register.
	 */
	public void clear() {
		this.removeActor(card);
		this.card = null;
	}
	
	/**
	 * Gives <code>true</code> if this register is empty.
	 * @return <code>true</code> if this register is empty.
	 */
	public boolean isEmpty() {
		return card == null;
	}
}
