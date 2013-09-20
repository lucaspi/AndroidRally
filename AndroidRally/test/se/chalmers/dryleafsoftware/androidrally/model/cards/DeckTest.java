package se.chalmers.dryleafsoftware.androidrally.model.cards;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DeckTest {
	
	@Test
	public void testDeckSize() {
		Deck deck = new Deck();
		assertTrue(84 == deck.getCards().size());
	}

	@Test
	public void testDrawCard() {
		Deck deck = new Deck();
		List<Card> drawnCards = new ArrayList<Card>();
		Card lastDrawnCard;
		
		for (int i = 0; i < 84; i++) {
			lastDrawnCard = deck.drawCard();
			assertFalse(drawnCards.contains(lastDrawnCard));
			drawnCards.add(lastDrawnCard);
		}
		assertTrue(deck.getCards().size() == 0);
	}
	
	@Test
	public void testReturnCards() {
		Deck deck = new Deck();
		List<Card> drawnCards = new ArrayList<Card>();
		
		for (int i = 0; i < 9; i++) {
			drawnCards.add(deck.drawCard());
		}
		assertTrue(deck.getCards().size() == 75);
		assertTrue(drawnCards.size() == 9);
		deck.returnCards(drawnCards);
		assertTrue(deck.getCards().size() == 84);
	}

}
