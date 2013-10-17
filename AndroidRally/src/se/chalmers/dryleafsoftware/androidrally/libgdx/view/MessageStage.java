package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MessageStage extends Stage {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private final Table container;
	private final Image backGroundImage;
	private final Image inputCatcher;
	private final TextButtonStyle buttonStyle;
	private final Table exitPanel;
	private final Drawable backGround;
	private final LabelStyle labelStyle;

	public static final String EVENT_MENU = "messageOk";
	public static final String EVENT_EXIT = "exitGame";

	public MessageStage() {
		super();

		backGround = new TextureRegionDrawable(new TextureRegion(new Texture(
				Gdx.files.internal("textures/messageBackGround.png")), 0, 0, 280, 200));

		// Default camera
		OrthographicCamera cardCamera = new OrthographicCamera(480, 800);
		cardCamera.zoom = 1.0f;
		cardCamera.position.set(240, 400, 0f);
		cardCamera.update();
		setCamera(cardCamera);

		NinePatchDrawable buttonTexture = new NinePatchDrawable(new NinePatch(
				new Texture(Gdx.files.internal("textures/button9patch.png")), 4, 4, 4, 4));
		NinePatchDrawable buttonTexturePressed = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files.internal("textures/button9patchpressed.png")), 4, 4, 4, 4));
		buttonStyle = new TextButtonStyle(buttonTexture, buttonTexturePressed, null);
		buttonStyle.font = new BitmapFont();

		labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);

		this.container = new Table();
		container.debug();
		container.setSize(480, 800);
		container.setLayoutEnabled(false);
		addActor(container);

		ClickListener gl = new ClickListener();

		inputCatcher = new Image();
		inputCatcher.setSize(480, 800);
		inputCatcher.addListener(gl);

		backGroundImage = new Image(backGround);
		backGroundImage.addListener(gl);

		exitPanel = new Table();
		exitPanel.setBackground(backGround);
		exitPanel.setVisible(true);
		exitPanel.setSize(280, 200);
		exitPanel.setPosition(100, 500);
		exitPanel.debug();
		exitPanel.setLayoutEnabled(false);

		Label label = new Label(
				"If you exit now you will not be able to change your cards."
						+ "Do you want to exit anyway?", labelStyle);
		Table labelContainer = new Table();
		labelContainer.debug();
		labelContainer.setSize(280, 200);
		labelContainer.add(label).pad(10).size(labelContainer.getWidth() - 20, labelContainer.getHeight() - 20);
		label.setWrap(true);
		exitPanel.add(labelContainer);

		TextButton exit = new TextButton("Yes", buttonStyle);
		exit.setSize(100, 30);
		exit.setPosition(40, 20);
		exitPanel.add(exit); // Border
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pcs.firePropertyChange(EVENT_EXIT, 0, 1);
			}
		});

		TextButton noExit = new TextButton("No", buttonStyle);
		noExit.setSize(100, 30);
		noExit.setPosition(140, 20);
		exitPanel.add(noExit); // Border
		noExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				container.clear();
			}
		});
	}

	/**
	 * Add a PropertyChangeListener to the listener list. The listener is
	 * registered for all properties. The same listener object may be added more
	 * than once, and will be called as many times as it is added. If listener
	 * is null, no exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to be added
	 * @see PropertyChangeSupport
	 * @see PropertyChangeListener
	 */
	public void addListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a PropertyChangeListener from the listener list. This removes a
	 * PropertyChangeListener that was registered for all properties. If
	 * listener was added more than once to the same event source, it will be
	 * notified one less time after being removed. If listener is null, or was
	 * never added, no exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            The PropertyChangeListener to be added
	 * @see PropertyChangeSupport
	 * @see PropertyChangeListener
	 */
	public void removeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	/**
	 * Will show the game over screen.
	 * 
	 * @param robots
	 *            The robots in the game.
	 */
	public void dispGameOver(List<RobotView> robots) {
		int scorePos = 0;
		for (RobotView r : robots) {
			if (r.isGameDead()) {
				scorePos++;
			}
		}
		dispWithMessage("You were the " + convertToText(scorePos + 1) + " to die!");
	}

	/**
	 * Displays a "do you really want to close?" dialog
	 */
	public void dispCloseMessage() {
		closeAll();
		setInputCatcher(0, 0, 480, 800);
		container.add(exitPanel);
	}

	/**
	 * Removes any popup on top of the game screen
	 */
	public void closeAll() {
		container.clear();
	}

	/**
	 * Disables the area around the specified borders (useful with popup
	 * dialogs)
	 * 
	 * @param x
	 *            the x-coordinate of the border
	 * @param y
	 *            the y-coordinate of the border
	 * @param w
	 *            the width of the border
	 * @param h
	 *            the height of the border
	 */
	private void setInputCatcher(float x, float y, float w, float h) {
		inputCatcher.setSize(w, h);
		inputCatcher.setPosition(x, y);
		container.add(inputCatcher);
	}

	/**
	 * Converts a number to the corresponding place as a string
	 * 
	 * @param i
	 *            the number to convert
	 * @return The number as a string
	 */
	private String convertToText(int i) {
		return new String[] { "first", "second", "third", "fourth", "fifth",
				"sixth", "seventh", "eighth" }[i - 1];
	}

	/**
	 * Displays a message as a popup
	 * 
	 * @param message
	 *            The message to be displayed
	 */
	private void dispWithMessage(String message) {
		closeAll();

		Table panel = new Table();
		panel.setSize(280, 200);
		panel.setPosition(100, 500);
		panel.debug();
		panel.setBackground(backGround);
		panel.setLayoutEnabled(false);
		panel.setVisible(true);

		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
		Label label = new Label(message, labelStyle);
		Table messageContainer = new Table();
		messageContainer.setSize(280, 200);
		messageContainer.add(label);
		panel.add(messageContainer);

		TextButton gameOverButton = new TextButton("Return to menu", buttonStyle);
		gameOverButton.setPosition(70, 20);
		gameOverButton.setSize(140, 30);
		panel.row();
		panel.add(gameOverButton);
		gameOverButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pcs.firePropertyChange(EVENT_MENU, 0, 1);
			}
		});

		setInputCatcher(0, 0, 480, 800);
		container.add(panel);
	}

	/**
	 * Will show the screen which tells the user who won.
	 * 
	 * @param robots
	 *            The robots in the game.
	 */
	public void dispGameWon(List<RobotView> robots) {
		for (RobotView r : robots) {
			if (r.hasFinished()) {
				dispWithMessage("\"" + r.getName() + "\" just won!");
				break;
			}
		}
	}
}
