package se.chalmers.dryleafsoftware.androidrally.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		
		for (int i = 10; i <= 60; i+=10) {
			cards.add(new Turn(i, TurnType.UTURN));
		}
		for (int i = 70; i <= 410; i+=20) {
			cards.add(new Turn(i, TurnType.LEFT));
		}
		for (int i = 80; i <= 420; i+=20) {
			cards.add(new Turn(i, TurnType.RIGHT));
		}
		for (int i = 430; i <= 480; i+=10) {
			cards.add(new Move(i, -1));
		}
		for (int i = 490; i <= 660; i+=10) {
			cards.add(new Move(i, 1));
		}
		for (int i = 670; i <= 780; i+=10) {
			cards.add(new Move(i, 2));
		}
		for (int i = 790; i <= 840; i+=10) {
			cards.add(new Move(i, 3));
		}
		
		shuffleDeck();
	}
	
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	public Card drawCard() {
		return cards.remove(0);
	}
	
	public void returnCards(List<Card> returnedCards) {
		this.cards.addAll(cards);
	}
}