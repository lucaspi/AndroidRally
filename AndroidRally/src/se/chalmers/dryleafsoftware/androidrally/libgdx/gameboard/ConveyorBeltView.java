package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * 
 * 
 * @author 
 *
 */
public class ConveyorBeltView extends Image {
	
	private float scrollTimer = 0;
	private TextureRegion texture;
	
	/**
	 * Creates a new conveyor belt at the specified position.
	 * @param texture The texture of the conveyor belt.
	 * @param degree The direction of the conveyorbelt (counterclockwise).
	 */
	public ConveyorBeltView(TextureRegion texture, int degree) {
		super(texture);
		this.texture = texture;
		this.setOrigin(getWidth()/2, getHeight()/2);
		this.rotate(-degree);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		this.setOrigin(getWidth()/2, getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, float f) {	
		scrollTimer += 0.01f;
		if (scrollTimer > 1.0f)
		        scrollTimer = 0.0f;
		texture.setV(scrollTimer);
		texture.setV2(scrollTimer + 1);
		super.draw(spriteBatch, f);
	}
}
