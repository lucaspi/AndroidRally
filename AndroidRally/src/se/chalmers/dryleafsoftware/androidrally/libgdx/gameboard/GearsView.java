package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This will render a gear, a board element which rotates robots.
 * 
 * @author 
 *
 */
public class GearsView extends AnimatedImage {

	private int direction = -1;
	
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
			direction *= -1;
		}
		this.setPhaseMask(4);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		this.setOrigin(getWidth()/2, getHeight()/2);
	}

	@Override
	public void animate(float timeDelta) {
		this.rotate(90 * timeDelta * direction);
	}
}
