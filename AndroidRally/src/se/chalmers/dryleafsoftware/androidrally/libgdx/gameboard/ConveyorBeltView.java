package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 
 * 
 * @author 
 *
 */
public class ConveyorBeltView extends Sprite {
	
	private float scrollTimer = 0;
	
	/**
	 * Creates a new conveyor belt at the specified position.
	 * @param texture The texture of the conveyor belt.
	 * @param degree The direction of the conveyorbelt (counterclockwise).
	 */
	public ConveyorBeltView(TextureRegion texture, int degree) {
		super(texture);
		this.setOrigin(getWidth()/2, getHeight()/2);
		this.rotate(-degree);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		this.setOrigin(getWidth()/2, getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {	
		scrollTimer += 0.01f;
		if (scrollTimer > 1.0f)
		        scrollTimer = 0.0f;
		setV(scrollTimer);
		setV2(scrollTimer + 1);
		super.draw(spriteBatch);
	}
}
