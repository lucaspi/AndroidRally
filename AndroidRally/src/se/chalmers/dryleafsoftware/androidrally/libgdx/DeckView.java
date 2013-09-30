package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

/**
 * This stage holds all the cards the player has to play with.
 * 
 * @author
 * 
 */
public class DeckView extends Stage {

	private final Texture buttonTexture;
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private List<CardView> deckCards = new ArrayList<CardView>();
	private final Register[] registers;
	private int position;
	private final CardListener cl;
	private Table container;
	private final Table lowerArea, upperArea, statusBar;
	private final Table playPanel; // The panel with [Play] [Step] [Skip]
	private final Table drawPanel; // The panel with [Draw cards]

	/**
	 * Specifying that the game should start.
	 */
	public static final String EVENT_PLAY = "play";
	/**
	 * Specifying that the next register's set of actions should display.
	 */
	public static final String EVENT_STEP = "step";
	/**
	 * Specifying that all the actions should be skipped.
	 */
	public static final String EVENT_SKIP = "skip";
	/**
	 * Specifying that the player should be given new cards.
	 */
	public static final String EVENT_DRAW_CARDS = "drawCards";
	/**
	 * Specifying that the player has looked at its cards long enough.
	 */
	public static final String TIMER_CARDS = "timerCards";
	/**
	 * Specifying that the round has ended.
	 */
	public static final String TIMER_ROUND = "timerRound";
	
	private int timerTick;
	private final Timer timer;	
	private final Label timerLabel;

	/**
	 * Creates a new default instance.
	 */
	public DeckView() {
		super();
		Texture deckTexture = new Texture(Gdx.files.internal("textures/woodenDeck.png"));
		deckTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		buttonTexture = new Texture(Gdx.files.internal("textures/button.png"));
		buttonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
		cl = new CardListener(this);
		
		// Default camera
		OrthographicCamera cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		setCamera(cardCamera);
		
		// Set background image
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
		upperArea.setLayoutEnabled(false);
		container.add(upperArea);
		
		statusBar = new Table();
		statusBar.debug();
		statusBar.setSize(480, 80);
		statusBar.setPosition(0, 240);
		container.add(statusBar);
		
		Texture compTexture = new Texture(Gdx.files.internal("textures/deckComponents.png"));
		compTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		registers = new Register[5];
		for(int i = 0; i < 5; i++) {
			registers[i] = new Register(compTexture, i);
			registers[i].setPosition(480 / 5 * (i+0.5f) - registers[i].getWidth()/2 , 0);
			upperArea.add(registers[i]);
			
		}
		
		// TODO: Remove this dummy button!
		TextButtonStyle style = new TextButtonStyle();	  
        style.up = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 0, 32, 32));
        style.down = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 32, 32, 32));
        style.font = new BitmapFont();
        TextButton dummy = new TextButton("Force round", style);
        statusBar.add(dummy); // Border
        dummy.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(TIMER_ROUND, 0, 1);
    		}
    	});
        TextButton dummy2 = new TextButton("Send cards", style);
        statusBar.add(dummy2); // Border
        dummy2.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(TIMER_CARDS, 0, 1);
    		}
    	});
		
		playPanel = buildPlayerPanel();		
		drawPanel = buildDrawCardPanel();	

		timer = new Timer();
		timer.start();
		// Tick every second.
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				timerTick--;
				setTimerLabel(timerTick);
			}
		}, 0, 1f);

    	LabelStyle lStyle = new LabelStyle();
    	lStyle.font = new BitmapFont();
    	timerLabel = new Label("", lStyle);
    	statusBar.add(timerLabel);
	}
	
	/*
	 * Creates the panel with the [Draw Cards] button.
	 */
	private Table buildDrawCardPanel() {
		TextButtonStyle style = new TextButtonStyle();	  
        style.up = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 0, 32, 32));
        style.down = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 32, 32, 32));
        style.font = new BitmapFont();
		
		int internalPadding = 50, externalPadding = 10;
		Table drawPanel = new Table();
    	drawPanel.setSize(480, 120);
    	TextButton draw = new TextButton("Draw Cards", style);
    	draw.pad(0, internalPadding, 0, internalPadding); // Internal padding
    	drawPanel.add(draw).pad(externalPadding); // Border
    	
    	draw.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_DRAW_CARDS, 0, 1);
    		}
    	});
    	return drawPanel;
	}
	
	/*
	 * Creates the panel with the [Play] [Step] [Skip] buttons.
	 */
	private Table buildPlayerPanel() {
		TextButtonStyle style = new TextButtonStyle();	  
        style.up = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 0, 32, 32));
        style.down = new TextureRegionDrawable(
        		new TextureRegion(buttonTexture, 0, 32, 32, 32));
        style.font = new BitmapFont();
		
		int internalPadding = 50, externalPadding = 10;
		Table playPanel = new Table();
		playPanel.setSize(480, 120);
		TextButton play = new TextButton("Play", style);
		play.pad(0, internalPadding, 0, internalPadding); // Internal padding
    	playPanel.add(play).pad(externalPadding); // Border
    	TextButton step = new TextButton("Step", style);
    	step.pad(0, internalPadding, 0, internalPadding); // Internal padding
    	playPanel.add(step).pad(externalPadding); // Border
    	TextButton skip = new TextButton("Skip", style);
    	skip.pad(0, internalPadding, 0, internalPadding); // Internal padding
    	playPanel.add(skip).pad(externalPadding); // Border
    	
    	play.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_PLAY, 0, 1);
    		}
    	});
    	step.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_STEP, 0, 1);
    		}
    	});
    	skip.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_SKIP, 0, 1);
    		}
    	});
    	return playPanel;
	}
	
	/*
	 * Sets the label of the timer to display the specified number of seconds
	 * as [hh:mm:ss].
	 */
	private void setTimerLabel(int ticks) {
		int h = timerTick / 3600;
		int m = (timerTick / 60) % 60;
		int s = timerTick % 60;
		s = Math.max(s, 0);
		timerLabel.setText(String.format("%02d", h) + 
				":" + String.format("%02d", m) + 
				":" + String.format("%02d", s));
	}
	
	/**
	 * Gives the current seconds left in the timer.
	 * @return The current number of seconds left in the timer.
	 */
	public int getTimerSeconds() {
		return this.timerTick;
	}
	
	/**
	 * Sets the timer to count down the specified amount of seconds.
	 * @param seconds The seconds to count down.
	 * @param event The event to fire when the timer reach zero.
	 */
	public void setTimer(int seconds, final String event) {
		this.timerTick = seconds;	
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				pcs.firePropertyChange(event, 0, 1);
			}
		}, seconds);
		setTimerLabel(timerTick);
	}
	
	/**
	 * Adds the specified listener.
	 * @param listener The listener to add.
	 */
	public void addListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	/**
	 * Removes the specified listener.
	 * @param listener The listener to remove.
	 */
	public void removeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	/**
	 * Sets which cards the deck should display.
	 * 
	 * @param list
	 *            The cards the deck should display.
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
			cv.addListener(cardListener);
		}
	}
	
	/**
	 * Displays the panel which should be visible while waiting for round results.
	 */
	public void displayWaiting() {
		lowerArea.clear();
		for(Register r : registers) {
			if(r.getCard() != null) {
				r.removeListener(cardListener);
			}
		}
	}
	
	/**
	 * Displays the panel which should be visible after viewing the round results.
	 */
	public void displayDrawCard() {
		for(Register r : registers) {
			r.clear();
		}
		lowerArea.clear();
		lowerArea.add(drawPanel);
	}
	
	/**
	 * DIsplays the panel which should be visible when viewing the round results.
	 */
	public void displayPlayOptions() {
		lowerArea.clear();
		lowerArea.add(playPanel);
		for(Register r : registers) {
			if(r.getCard() != null) {
				r.removeListener(cardListener);
			}
		}
	}
	
	/**
	 * Returns the cards added to the register
	 * 
	 * @return
	 */
	public CardView[] getChosenCards() {
		CardView[] cards = new CardView[registers.length];
		for(int i = 0; i < registers.length; i++) {
			cards[i] = registers[i].getCard();
		}
		return cards;
	}

	/**
	 * Rerenders all the player's card at their correct positions.
	 */
	public void updateCards() {
//		updateChosenCards();
		updateDeckCards();
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
		if(this.deckCards.isEmpty()) {
			return 0;
		}else{
			return this.deckCards.size()
					* ((int) this.deckCards.get(0).getWidth() + 10) - 10;
		}
	}
	
	public void clearChosen() {
		for(Register r : registers) {
			r.clear();
			r.setLocked(false);
		}
	}
	
	public void setLockedCard(CardView cv, int index) {
		registers[index].setCard(cv);
		registers[index].setLocked(true);
	}
	
	private void choseCard(CardView card) {
		for(int i = 0; i < registers.length; i++) {
			if(registers[i].isEmpty()) {
				registers[i].setCard(card);
				deckCards.remove(card);
				break;
			}
		}
	}
	
	public void unChoseCard(CardView card) {
		for(int i = 0; i < registers.length; i++) {
			if(registers[i].getCard() == card) {
				registers[i].clear();
				lowerArea.add(card);
				deckCards.add(card);
				break;
			}
		}
	}
	
	private final ActorGestureListener cardListener = new ActorGestureListener() {
		
		@Override
		public void tap(InputEvent event, float x, float y, int count, int button) {
			Actor pressed = getTouchDownTarget();
			if(pressed instanceof CardView) {
				CardView card = (CardView)pressed;
				if(deckCards.contains(card)) {
					choseCard(card);
				}else{
					unChoseCard(card);
				}
			}
			updateCards();
		}
	};
}
