package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ConveyorBeltCurve extends AnimatedImage {
	
	private int direction = -1;
	
	/**
	 * Creates a new conveyer belt which will rotate in the specified direction.
	 * @param texture The texture to use.
	 * @param clockWise Set to <code>true</code> if the conveyer belt 
	 * should rotate clockwise (right).
	 */
	public ConveyorBeltCurve(TextureRegion texture, boolean clockWise) {
		super(texture);	
		if(!clockWise) {
			texture.flip(true, false);
			direction *= -1;
		}
		this.setPhaseMask(4);
	}

	@Override
	public void animate(float timeDelta) {
		this.rotate(90 * timeDelta * direction);
	}
}
