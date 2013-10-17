package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class helps with the animation of an image.
 * 
 * @author
 *
 */
public class AnimatedImage extends Image {

	private Animation walkAnimation;
	private TextureRegion[] walkFrames;
	private float stateTime;

	/**
	 * Creates a new instance which will use the specified texture.
	 * @param texture The texture with all the frames.
	 * @param cols The number of columns on the texture.
	 * @param rows The number of rows on the texture.
	 */
	public AnimatedImage(Texture texture, int cols, int rows) {
		super();
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()
				/ cols, texture.getHeight() / rows);
		walkFrames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.025f, walkFrames);
		stateTime = 0f;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		setDrawable(new TextureRegionDrawable(currentFrame));
	}
}
