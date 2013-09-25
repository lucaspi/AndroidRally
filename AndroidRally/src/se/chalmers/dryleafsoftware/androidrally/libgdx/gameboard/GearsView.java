package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This will render a gear, a board element which rotates robots.
 * 
 * @author 
 *
 */
public class GearsView extends Image {

	private int rotSpeed = -3;
	
	/**
	 * Creates a new gear which will rotate in the specified direction.
	 * @param texture The texture to use.
	 * @param clockWise Set to <code>true</code> if the gears should rotate clockwise (right).
	 */
	public GearsView(TextureRegion texture, boolean clockWise) {
		super(texture);	
		this.setOrigin(getWidth()/2, getHeight()/2);
		if(!clockWise) {
			texture.flip(true, false);
			rotSpeed *= -1;
		}
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		this.setOrigin(getWidth()/2, getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		this.rotate(rotSpeed);
		super.draw(spriteBatch, f);
	}
}
