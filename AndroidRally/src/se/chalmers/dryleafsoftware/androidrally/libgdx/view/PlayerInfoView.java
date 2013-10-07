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
public class PlayerInfoView extends Table implements PropertyChangeListener {
				
	private final Image[] lifeIndicator = new Image[RobotView.MAX_LIVES];
	private final Image[] damageIndicator = new Image[RobotView.MAX_DAMAGE];
	
	private final TextureRegion notDamage;
	private final TextureRegion takenDamage;
	
	private final TextureRegion notLife;
	private final TextureRegion gotLife;
	
	/**
	 * Creates a new instance which will display the specified robot's data using the 
	 * specified texture.
	 * @param texture The texture to use when displaying data.
	 * @param playerData The robot which data should be displayed.
	 */
	public PlayerInfoView(Texture texture, RobotView playerData) {
		super();
		playerData.addListener(this);
		notDamage = new TextureRegion(texture, 420, 0, 20, 14);
		takenDamage = new TextureRegion(texture, 400, 0, 20, 14);		
		notLife = new TextureRegion(texture, 424, 14, 24, 24);
		gotLife = new TextureRegion(texture, 400, 14, 24, 24);		
		for(int i = 0; i < damageIndicator.length; i++) {
			damageIndicator[i] = new Image(notDamage);
			add(damageIndicator[i]);
		}
		for(int i = 0; i < lifeIndicator.length; i++) {
			lifeIndicator[i] = new Image(gotLife);
			add(lifeIndicator[i]);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().equals(RobotView.EVENT_DAMAGE_CHANGE)) {
			int damage = (Integer)arg0.getNewValue();
			for(int i = 0; i < damageIndicator.length; i++) {
				if(i < damage) {
					damageIndicator[i].setDrawable(new TextureRegionDrawable(takenDamage));
				}else{
					damageIndicator[i].setDrawable(new TextureRegionDrawable(notDamage));
				}
			}
		}else if(arg0.getPropertyName().equals(RobotView.EVENT_LIFE_CHANGE)) {
			int lives = (Integer)arg0.getNewValue();
			for(int i = 0; i < lifeIndicator.length; i++) {
				if(i < lives) {
					lifeIndicator[i].setDrawable(new TextureRegionDrawable(gotLife));
				}else{
					lifeIndicator[i].setDrawable(new TextureRegionDrawable(notLife));
				}
			}
		}
	}
}
