package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This stage holds all the cards the player has to play with.
 * 
 * @author
 * 
 */
public class DeckView extends Stage {
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private List<CardView> deckCards = new ArrayList<CardView>();
	private int position;
	private final CardListener cl;
	private final Table container;
	private final Table lowerArea, upperArea, statusBar;
	private final Table playPanel; // The panel with [Play] [Step] [Skip]
	private final Table drawPanel; // The panel with [Draw cards]
	private final Table allPlayerInfo; // The panel with all the players' info
	private final RegisterView registerView;

	
	public static final String EVENT_PAUSE = "pause";
	/**
	 * Specifying that the game should start.
	 */
	public static final String EVENT_PLAY = "play";
	
	public static final String EVENT_FASTFORWARD = "fast";
	/**
	 * Specifying that the next register's set of actions should display.
	 */
	public static final String EVENT_STEP_ALL = "stepAll";
	
	public static final String EVENT_STEP_CARD = "stepCard";
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
	/**
	 * Specifying that the info button has been pressed.
	 */
	public static final String EVENT_INFO = "info";
	
	private final Timer timer;	
	private final Label timerLabel;
	
	private int cardTick = 0;
	private int roundTick = 0;

	/**
	 * Creates a new default instance.
	 */
	public DeckView(List<RobotView> robots, int robotID) {
		super();
		Texture deckTexture = new Texture(Gdx.files.internal("textures/woodenDeck.png"));
		deckTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Texture compTexture = new Texture(Gdx.files.internal("textures/deckComponents.png"));
		compTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
		cl = new CardListener(this);
		
		// Default camera
		OrthographicCamera cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		setCamera(cardCamera);
		
		// Set background image
		Image deck = new Image(new TextureRegion(deckTexture, 0, 0, 480, 320));
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
		statusBar.setLayoutEnabled(false);
		container.add(statusBar);
		
		registerView = new RegisterView(compTexture);
		registerView.setSize(upperArea.getWidth(), upperArea.getHeight());
		upperArea.add(registerView);
		
		Texture buttonTexture = new Texture(Gdx.files.internal("textures/button.png"));
		buttonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextButtonStyle style = new TextButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttonTexture, 0, 0, 32, 32)),
				new TextureRegionDrawable(new TextureRegion(buttonTexture, 0, 32, 32, 32)),
				null);
		style.font = new BitmapFont();
				
		// TODO: Remove this dummy button!
        TextButton dummy = new TextButton("Force round", style);
        dummy.setPosition(0, 60);
        dummy.setSize(100, 20);
        statusBar.add(dummy); // Border
        dummy.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(TIMER_ROUND, 0, 1);
    		}
    	});
        TextButton dummy2 = new TextButton("Send cards", style);
        dummy2.setPosition(100, 60);
        dummy2.setSize(100, 20);
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
		// Tick every second.
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(cardTick > 0) {
					cardTick--;
					if(cardTick == 0) {
						pcs.firePropertyChange(TIMER_CARDS, 0, 1);
						System.out.println("---------------------------------CARD TIMER-----------");
					}
				}
				if(roundTick > 0) {
					roundTick--;
					if(roundTick == 0) {
						pcs.firePropertyChange(TIMER_ROUND, 0, 1);
						System.out.println("-------------------------------------ROund timer!-----------");
					}
				}
				setTimerLabel(cardTick > 0 ? cardTick : roundTick);
			}
		}, 1000, 1000);

		Table statusCenter = new Table();
		statusCenter.setPosition(200, 0);
		statusCenter.setSize(80, statusBar.getHeight());	
		statusCenter.debug();
		statusBar.add(statusCenter);
    	LabelStyle lStyle = new LabelStyle();
    	lStyle.font = new BitmapFont();
    	timerLabel = new Label("", lStyle);    	
    	statusCenter.add(timerLabel);
    	statusCenter.row();
    	
    	
        final TextButton showPlayers = new TextButton("Info", style);
        statusCenter.add(showPlayers).minWidth(75);
        showPlayers.addListener(new ClickListener() {
        	private boolean dispOpp = false;
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			if(dispOpp) {
    				displayNormal();
    				showPlayers.setText("Info");
    			}else{
    				displayOpponentInfo();
    				showPlayers.setText("Back");
    			}
    			dispOpp = !dispOpp;
    		}
    	});
    	
    	DamageView dv = new DamageView(compTexture, robots.get(robotID));
    	LifeView lv = new LifeView(compTexture, robots.get(robotID));
    	lv.setPosition(0, 20);
    	lv.setSize(120, 40);
    	lv.debug();
    	dv.setPosition(280, 20);
    	dv.setSize(200, 40);
    	dv.debug();
    	statusBar.add(dv);
    	statusBar.add(lv);
    	
    	allPlayerInfo = new Table();
    	allPlayerInfo.setPosition(0, 0);
    	allPlayerInfo.setSize(480, 240);
    	Table scrollContainer = new Table();
    	scrollContainer.defaults().width(240);
    	ScrollPane pane = new ScrollPane(scrollContainer);
    	allPlayerInfo.add(pane);
    	for(int i = 0; i < robots.size(); i++) {
    		if(i != robotID) {    			
    	    	scrollContainer.add(new LifeView(compTexture, robots.get(i)));
    	    	scrollContainer.add(new DamageView(compTexture, robots.get(i)));
    			scrollContainer.row();
    		}
    	}
    	scrollContainer.debug();
	}
	
	/*
	 * Creates the panel with the [Draw Cards] button.
	 */
	private Table buildDrawCardPanel() {
		Texture buttonTexture = new Texture(Gdx.files.internal("textures/button.png"));
		buttonTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
		Texture buttons = new Texture(Gdx.files.internal("textures/playButtons.png"));
		buttons.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
		Table playPanel = new Table();
		playPanel.setSize(480, 120);
		
		// Create pause button
		Button pause = new Button(new ButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttons, 0, 0, 64, 64)),
				new TextureRegionDrawable(new TextureRegion(buttons, 0, 64, 64, 64)),
				null));
    	playPanel.add(pause).size(50, 50).pad(0);
    	pause.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_PAUSE, 0, 1);
    		}
    	});
		
    	// Create play button
		Button play = new Button(new ButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttons, 64, 0, 64, 64)),
				new TextureRegionDrawable(new TextureRegion(buttons, 64, 64, 64, 64)),
				null));
    	playPanel.add(play).size(50, 50).pad(0);
    	play.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_PLAY, 0, 1);
    		}
    	});
    	
    	// Create fast forward button
    	Button fastForward = new Button(new ButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttons, 128, 0, 64, 64)),
				new TextureRegionDrawable(new TextureRegion(buttons, 128, 64, 64, 64)),
				null));
    	playPanel.add(fastForward).size(50, 50).pad(0);
    	fastForward.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_FASTFORWARD, 0, 1);
    		}
    	});
    	
    	// Create button for skip single card
    	Button skipCard = new Button(new ButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttons, 192, 0, 64, 64)),
				new TextureRegionDrawable(new TextureRegion(buttons, 192, 64, 64, 64)),
				null));
    	playPanel.add(skipCard).size(50, 50).pad(0);
    	skipCard.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_STEP_CARD, 0, 1);
    		}
    	});
    	
    	// Create button for skip all cards
    	Button skipAll = new Button(new ButtonStyle(
				new TextureRegionDrawable(new TextureRegion(buttons, 256, 0, 64, 64)),
				new TextureRegionDrawable(new TextureRegion(buttons, 256, 64, 64, 64)),
				null));
    	playPanel.add(skipAll).size(50, 50).pad(0);
    	skipAll.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			pcs.firePropertyChange(EVENT_STEP_ALL, 0, 1);
    		}
    	});
    	
    	return playPanel;
	}
	
	public boolean isCardTimerOn() {
		return cardTick > 0;
	}
	
	/*
	 * Sets the label of the timer to display the specified number of seconds
	 * as [hh:mm:ss].
	 */
	private void setTimerLabel(int ticks) {
		int h = ticks / 3600;
		int m = (ticks / 60) % 60;
		int s = ticks % 60;
		s = Math.max(s, 0);
		timerLabel.setText(String.format("%02d", h) + 
				":" + String.format("%02d", m) + 
				":" + String.format("%02d", s));
	}
	
	/**
	 * Sets the card timer to the specified number in seconds.
	 * @param cardTick The card timer's delay in seconds.
	 */
	public void setCardTick(int cardTick) {
		this.cardTick = cardTick;
		if(cardTick > 0) {
			setTimerLabel(cardTick);
		}
	}
	
	/**
	 * Sets the round timer to the specified number in seconds.
	 * @param roundTick The round timer's delay in seconds.
	 */
	public void setRoundTick(int roundTick) {
		this.roundTick = roundTick;
		if(roundTick > 0) {
			setTimerLabel(roundTick);
		}
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
	 * Only displays the locked cards.
	 * @param input The String with card data.
	 * @param texture The texture to use.
	 */
	public void setChosenCards(String input, Texture texture) {
		setCards(input, texture, true);
	}
	
	/**
	 * Displays all cards.
	 * @param input The String with card data.
	 * @param texture The texture to use.
	 */
	public void setDeckCards(String input, Texture texture) {
		setCards(input, texture, false);
	}
	
	/**
	 * Displays all cards.
	 * @param input A String with all the cards' data.
	 * @param texture The texture to use when creating the cards.
	 * @param onlyLocked Set to <code>true</code> if only locked cards should be displayed.
	 */
	private void setCards(String input, Texture texture, boolean onlyLocked) {
		List<CardView> cards = new ArrayList<CardView>();
		// Clear cards
		registerView.clear();
				
		String indata = input;
		int i = 0;
		for(String card : indata.split(":")) {
			String[] data = card.split(";");
			
			int prio = (data.length == 2) ? Integer.parseInt(data[1]) : Integer.parseInt(data[0]);	
			int regX = 0;
			if(prio <= 60) {
				regX = 0;	// UTURN
			}else if(prio <= 410 && prio % 20 != 0) {
				regX = 64;	// LEFT
			}else if(prio <= 420 && prio % 20 == 0) {
				regX = 128;	// LEFT
			}else if(prio <= 480) {
				regX = 192;	// Back 1
			}else if(prio <= 660) {
				regX = 256;	// Move 1
			}else if(prio <= 780) {
				regX = 320;	// Move 2
			}else if(prio <= 840) {
				regX = 384;	// Move 3
			}	

			CardView cv = new CardView(new TextureRegion(texture, regX, 0, 64, 90), 
					prio, i);
			cv.setSize(78, 110);
			
			if(data.length == 2) {
				int lockPos = Integer.parseInt(data[0].substring(1));
				registerView.getRegister(lockPos).setCard(cv);
				registerView.getRegister(lockPos).displayOverlay(Register.PADLOCK);
			}else if(!onlyLocked){
				cards.add(cv);
			}			
			i++;
		}
		Collections.sort(cards);
		setDeckCards(cards);
	}
	
	/**
	 * Sets which cards the deck should display.
	 * 
	 * @param list
	 *            The cards the deck should display.
	 */
	private void setDeckCards(List<CardView> list) {
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
	
	public void displayOpponentInfo() {
		container.removeActor(lowerArea);
		container.removeActor(upperArea);
		container.add(allPlayerInfo);
	}
	
	/**
	 * Displays the panel which should be visible while waiting for round results.
	 */
	public void displayWaiting() {
		lowerArea.clear();
		registerView.removeCardListener(cardListener);
	}
	
	/**
	 * Displays the normal panel which is divided into an upper and a lower area.
	 */
	public void displayNormal() {
		container.removeActor(allPlayerInfo);
		container.add(upperArea);
		container.add(lowerArea);
	}
	
	/**
	 * Displays the panel which should be visible after viewing the round results.
	 */
	public void displayDrawCard() {
		registerView.clear();
		lowerArea.clear();
		lowerArea.add(drawPanel);
	}
	
	/**
	 * DIsplays the panel which should be visible when viewing the round results.
	 */
	public void displayPlayOptions() {
		lowerArea.clear();
		lowerArea.add(playPanel);
		registerView.removeCardListener(cardListener);
	}
	
	/**
	 * Returns the cards added to the register
	 * 
	 * @return
	 */
	public CardView[] getChosenCards() {
		return registerView.getCards();
	}

	/**
	 * Rerenders all the player's card at their correct positions.
	 */
	public void updateCards() {
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
	
	private void choseCard(CardView card) {
		if(registerView.addCard(card)) {
			deckCards.remove(card);
		}
	}
	
	private void unChoseCard(CardView card) {
		if(registerView.removeCard(card)) {
			lowerArea.add(card);
			deckCards.add(card);
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
