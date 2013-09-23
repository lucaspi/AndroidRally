package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A AnimatedImage helps with turning on and off an animated image.
 * 
 * @author
 *
 */
public abstract class AnimatedImage extends Image {

	private boolean enabled = false;
	
	/**
	 * Creates a new instance which will use the specified texture when rendering.
	 * @param texture The texture to use when rendering.
	 */
	public AnimatedImage(TextureRegion texture) {
		super(texture);
	}
	
	/**
	 * Set to turn on/off the animation on this image.
	 * @param enabled Set to turn on/off the animation on this image.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Gives <code>true</code> if this image is currently animated.
	 * @return <code>true</code> if this image is currently animated.
	 */
	public boolean getEnabled() {
		return this.enabled;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(getEnabled()) {
			animate(delta);
		}
	}
	
	/**
	 * Animates the image.
	 */
	public abstract void animate(float timeDelta);
}
