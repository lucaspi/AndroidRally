package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A AnimatedImage helps with turning on and off an animated image.
 * 
 * @author
 *
 */
public abstract class AnimatedElement extends Image {

	private boolean enabled = false;
	private int[] phaseMask = new int[]{0};
	
	/**
	 * Creates a new instance which will use the specified texture when rendering.
	 * @param texture The texture to use when rendering.
	 */
	public AnimatedElement(TextureRegion texture) {
		super(texture);
	}
	
	/**
	 * Will enable the animation if the image is specified for that phase.
	 * @param phase The current phase.
	 */
	public void enable(int phase) {
		for(int i = 0; i < phaseMask.length; i++) {
			if(phase == phaseMask[i]) {
				this.enabled = true;
				break;
			}
		}
	}
	
	/**
	 * Stops the animation.
	 */
	public void disable() {
		this.enabled = false;
	}
	
	/**
	 * Sets for which phases this image will animate.
	 * @param mask An array of phases.
	 */
	public void setPhaseMask(int[] mask) {
		this.phaseMask = mask;
	}
	
	/**
	 * Sets for which phase this image will animate.
	 * This is the same as calling <code>setPhaseMask(int[] mask)</code> with an
	 * array of length 1.
	 * @param phase The phase.
	 */
	public void setPhaseMask(int phase) {
		this.phaseMask = new int[]{phase};
	}
	
	/**
	 * Gives <code>true</code> if this image is currently animated.
	 * @return <code>true</code> if this image is currently animated.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if(isEnabled()) {
			animate(delta);
		}
	}
	
	/**
	 * Animates the image.
	 */
	public abstract void animate(float timeDelta);
}
