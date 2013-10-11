package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class CheckPointHandler implements PropertyChangeListener {

	private final List<CheckPointView> checkPoints;
	private final Drawable reachedTexture, unreachedTexture, targetTexture;

	public CheckPointHandler(List<CheckPointView> checkPoints, Texture texture) {
		this.checkPoints = checkPoints;
		Collections.sort(checkPoints);
		this.unreachedTexture = new TextureRegionDrawable(
				new TextureRegion(texture, 192, 0, 64, 64));
		this.reachedTexture = new TextureRegionDrawable(
				new TextureRegion(texture, 192, 64, 64, 64));
		this.targetTexture = new TextureRegionDrawable(
				new TextureRegion(texture, 192, 128, 64, 64));	
		for(int i = 0; i < checkPoints.size(); i++) {
			if(i != 0) {
				checkPoints.get(i).setDrawable(unreachedTexture);
			}else{
				checkPoints.get(i).setDrawable(targetTexture);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().equals(RobotView.EVENT_CHECKPOINT_CHANGE)) {
			int nextTarget = (Integer)arg0.getNewValue();
			for(int i = 0; i < checkPoints.size(); i++) {
				if(i < nextTarget) {
					checkPoints.get(i).setDrawable(reachedTexture);
				}else if(i == nextTarget) {
					checkPoints.get(i).setDrawable(targetTexture);
				}else{
					checkPoints.get(i).setDrawable(unreachedTexture);
				}
			}
		}
	}
}
