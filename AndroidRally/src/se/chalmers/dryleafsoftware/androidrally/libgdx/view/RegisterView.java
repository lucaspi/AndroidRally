package se.chalmers.dryleafsoftware.androidrally.libgdx.view;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Panel with the registers.
 * 
 * @author 
 *
 */
public class RegisterView extends Table {

	private final Register[] registers;
	
	/**
	 * Creates a new instance which will use the specified texture.
	 * @param texture The texture to use when building the panel.
	 */
	public RegisterView(Texture texture) {
		setLayoutEnabled(false);
		registers = new Register[5];
		for(int i = 0; i < 5; i++) {
			registers[i] = new Register(texture, i);
			registers[i].setPosition(480 / 5 * (i+0.5f) - registers[i].getWidth()/2 , 0);
			add(registers[i]);			
		}
	}
	
	/**
	 * Removes the cards from all the registers.
	 */
	public void clear() {
		for(Register r : registers) {
			r.clear();
			r.displayOverlay(Register.NORMAL);
		}
	}
	
	/**
	 * Removes the specified listener from all the cards.
	 * @param actorGestureListener The listener to remove from the cards.
	 */
	public void removeCardListener(ActorGestureListener actorGestureListener) {
		for(Register r : registers) {
			if(r.getCard() != null) {
				r.getCard().removeListener(actorGestureListener);
			}
		}
	}
	
	/**
	 * Gives the register at the specified index.
	 * @param index The index of the register to return.
	 * @return The register at the specified index.
	 */
	public Register getRegister(int index) {
		if(index < registers.length && index >= 0) {
			return registers[index];
		}else{
			return null;
		}
	}
	
	/**
	 * Adds the specified card to the registers if there is room.
	 * @param card The card to try to add.
	 * @return <code>true</code> if the card could be added.
	 */
	public boolean addCard(CardView card) {
		for(int i = 0; i < registers.length; i++) {
			if(registers[i].isEmpty()) {
				registers[i].setCard(card);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the card from the register.
	 * @param card The card to remove from the register.
	 * @return <code>true</code> if the specified card was removed.
	 */
	public boolean removeCard(CardView card) {
		for(int i = 0; i < registers.length; i++) {
			if(registers[i].getCard() == card) {
				registers[i].clear();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gives an array of the cards in the registers.
	 * @return An array of the cards in the registers.
	 */
	public CardView[] getCards() {
		CardView[] cards = new CardView[registers.length];
		for(int i = 0; i < registers.length; i++) {
			cards[i] = registers[i].getCard();
		}
		return cards;
	}
}
