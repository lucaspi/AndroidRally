package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This view will render a conveyer belt, a board element which moves robots in the 
 * direction the conveyer belt is moving.
 * 
 * @author 
 *
 */
public class ConveyorBeltView extends AnimatedImage {
	
	private float scrollTimer = 0;
	private final TextureRegion texture;
	private float speedMulti;
	
	/**
	 * Creates a new conveyer belt at the specified position.
	 * @param texture The texture of the conveyer belt.
	 * @param degree The direction of the conveyer belt (counterclockwise).
	 * @param speedMulti A speed multiplier of the conveyer belt's speed.
	 */
	public ConveyorBeltView(TextureRegion texture, int degree, int speedMulti) {
		super(texture);
		this.texture = texture;
		this.setOrigin(getWidth()/2, getHeight()/2);
		this.rotate(-degree);
//		this.speedMulti = speedMulti;
		this.speedMulti = 1;
		int[] mask = new int[speedMulti];
		for(int i = 0; i < mask.length; i++) {
			mask[i] = 10 + i + 1;
		}
		this.setPhaseMask(mask);
	}

	@Override
	public void animate(float timeDelta) {
		scrollTimer += timeDelta * speedMulti;
		if (scrollTimer > 1.0f)
			scrollTimer = 0.0f;
		texture.setV(scrollTimer);
		texture.setV2(scrollTimer + 1);
	}
}
