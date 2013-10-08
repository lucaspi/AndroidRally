package se.chalmers.dryleafsoftware.androidrally.libgdx.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Register used when programming a robot.
 * 
 * @author
 *
 */
public class Register extends Table {
	
	private CardView card;
//	private final Image padLock;
	private final Image overlay;
	private final TextureRegionDrawable padLock, grayed, current;
	
	public static final int NORMAL = 0,
			PADLOCK = 1,
			UNFOCUS = 2,
			INFOCUS = 3;
	
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
		
		padLock = new TextureRegionDrawable(new TextureRegion(texture, 0, 90, 64, 90));
		grayed = new TextureRegionDrawable(new TextureRegion(texture, 64, 0, 64, 90));
		current = new TextureRegionDrawable(new TextureRegion(texture, 128, 0, 64, 90));
		
		overlay = new Image();
		overlay.setSize(78, 110);
		
		this.setSize(background.getWidth(), background.getHeight());
		this.add(background);
	}

	public void displayOverlay(int image) {
		switch(image) {
		case NORMAL:
			removeActor(overlay);
			break;
		case PADLOCK:
			overlay.setDrawable(padLock);
			add(overlay);
			break;
		case INFOCUS:
			overlay.setDrawable(current);
			add(overlay);
			break;
		case UNFOCUS:
			overlay.setDrawable(grayed);
			add(overlay);
			break;
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
