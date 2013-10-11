package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image {
    
    private Animation walkAnimation;
    private TextureRegion[] walkFrames;
    
    private float stateTime; 

    public AnimatedImage(Texture texture, int cols, int rows, int duration) {
    	super();
    	TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 
    			cols, texture.getHeight() / rows);
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
