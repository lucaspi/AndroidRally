package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This view will render a conveyer belt, a board element which moves robots in the 
 * direction the conveyer belt is moving.
 * 
 * @author 
 *
 */
public class ConveyorBeltView extends Image {
	
	private float scrollTimer = 0;
	private final TextureRegion texture;
	private float scrollSpeed = 0.01f;
	
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
		this.scrollSpeed *= speedMulti;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		this.setOrigin(getWidth()/2, getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		scrollTimer += scrollSpeed;
		if (scrollTimer > 1.0f)
		        scrollTimer = 0.0f;
		texture.setV(scrollTimer);
		texture.setV2(scrollTimer + 1);
		super.draw(spriteBatch, f);
	}
}
