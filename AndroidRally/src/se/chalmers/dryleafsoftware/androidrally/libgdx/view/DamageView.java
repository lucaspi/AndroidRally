package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This view displays info about a specified robot.
 * 
 * @author 
 *
 */
public class DamageView extends Table implements PropertyChangeListener {
				
	private final Image[] damageIndicator = new Image[RobotView.MAX_DAMAGE];
	
	private final TextureRegion notDamage;
	private final TextureRegion takenDamage;
	private final Image[] lockedCard;
	
	/**
	 * Creates a new instance which will display the specified robot's data using the 
	 * specified texture.
	 * @param texture The texture to use when displaying data.
	 * @param playerData The robot which data should be displayed.
	 */
	public DamageView(Texture texture, RobotView playerData) {
		super();
		setLayoutEnabled(false);
		playerData.addListener(this);
		notDamage = new TextureRegion(texture, 420, 0, 20, 14);
		takenDamage = new TextureRegion(texture, 400, 0, 20, 14);		
		for(int i = 0; i < damageIndicator.length; i++) {
			damageIndicator[i] = new Image(notDamage);
			damageIndicator[i].setPosition(20 * i, 30);
			add(damageIndicator[i]);
		}
		row();
		lockedCard = new Image[5];
		for(int i = 0; i < 5; i++) {
			Image image = new Image(new TextureRegion(texture, 400 + i * 20, 38, 20, 30));
			image.setPosition(20 * i, 0);
			add(image);
			lockedCard[i] = new Image(new TextureRegion(texture, 380, 38, 20, 30));
			lockedCard[i].setPosition(20 * i, 0);
			lockedCard[i].setVisible(false);
			add(lockedCard[i]);
		}
		setDamage(playerData.getDamage());
	}
	
	private void setDamage(int damage) {
		for(int i = 0; i < damageIndicator.length; i++) {
			if(i < damage) {
				damageIndicator[damageIndicator.length - 1 - i].
				setDrawable(new TextureRegionDrawable(takenDamage));
			}else{
				damageIndicator[damageIndicator.length - 1 - i].
				setDrawable(new TextureRegionDrawable(notDamage));
			}
			if(i < 5) {
				lockedCard[i].setVisible(damageIndicator.length - i <= damage);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().equals(RobotView.EVENT_DAMAGE_CHANGE)) {
			int damage = (Integer)arg0.getNewValue();
			setDamage(damage);
		}
	}
}
