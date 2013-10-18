package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class displays how many lives a robot has.
 * 
 * @author
 * 
 */
public class LifeView extends Table implements PropertyChangeListener {

	private final Image[] lifeIndicator = new Image[RobotView.MAX_LIVES];

	private final TextureRegion notLife;
	private final TextureRegion gotLife;

	/**
	 * Creates a new instance which will display the specified robot's data
	 * using the specified texture.
	 * 
	 * @param texture
	 *            The texture to use when displaying data.
	 * @param playerData
	 *            The robot which data should be displayed.
	 */
	public LifeView(Texture texture, RobotView playerData) {
		super();
		playerData.addListener(this);
		add(new Image(playerData.getDrawable())).maxSize(40, 40).left();
		notLife = new TextureRegion(texture, 424, 14, 24, 24);
		gotLife = new TextureRegion(texture, 400, 14, 24, 24);
		for (int i = 0; i < lifeIndicator.length; i++) {
			lifeIndicator[i] = new Image(gotLife);
			add(lifeIndicator[i]);
		}
		setLives(playerData.getLives());
		Label name = new Label(playerData.getName(), new LabelStyle(
				new BitmapFont(), Color.WHITE));
		name.setPosition(10, 0);
		name.setWrap(true);
		row();
		add(name).left();
	}

	private void setLives(int lives) {
		for (int i = 0; i < lifeIndicator.length; i++) {
			if (i < lives) {
				lifeIndicator[lifeIndicator.length - 1 - i]
						.setDrawable(new TextureRegionDrawable(gotLife));
			} else {
				lifeIndicator[lifeIndicator.length - 1 - i]
						.setDrawable(new TextureRegionDrawable(notLife));
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals(RobotView.EVENT_LIFE_CHANGE)) {
			int lives = (Integer) arg0.getNewValue();
			setLives(lives);
		}
	}
}
