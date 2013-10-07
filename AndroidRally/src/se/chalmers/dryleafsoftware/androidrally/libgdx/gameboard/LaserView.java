package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import se.chalmers.dryleafsoftware.androidrally.sharred.MapBuilder;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This view class only displays the laser beam. For proper collision when drawing, use
 * <code>setCollisionMatrix()</code>.
 * 
 * @author
 *
 */
public class LaserView extends AnimatedImage {
	
	private CollisionMatrix collisionMatrix;
	private boolean hasCalc = false;
	private boolean outer = false;
	
	/**
	 * Creates a new laser which will use the specified texture.
	 * @param texture The texture of the laser beam.
	 * @param x The X-coordinate. (In array).
	 * @param y The Y-coordinate. (In array).
	 * @param dir The direction to shot.
	 */
	public LaserView(TextureRegion texture, int dir) {
		super(texture);
		setRotation(-dir * 90);
		setVisible(false);
		setPhaseMask(5);
	}
	
	/**
	 * Sets where the laser is located on the tile. If it is "outer" then it is placed
	 * on the border of the tile facing out, otherwise it will be pointing inward, i.e. the laser will
	 * pass through the tile it is on. However, it will always face the direction it is specified
	 * to face.
	 * @param outer 
	 */
	public void setIsOuter(boolean outer) {
		this.outer = outer;
	}
	
	/**
	 * Set to use proper collision.
	 * @param collisionMatrix Array of booleans describing collision.
	 */
	public void setCollisionMatrix(CollisionMatrix collisionMatrix) {
		this.collisionMatrix = collisionMatrix;
		hasCalc = false;
	}
	
	@Override
	public void enable(int phase) {
		super.enable(phase);
		setVisible(isEnabled());
	}
	
	@Override
	public void disable() {
		super.disable();
		setVisible(false);
	}
	
	private int calcHeight() {
		int length = 0;
		int x = (int) (getX() / 40);
		int y = 15 - (int) (getY() / 40);
		int dir = ((int)(getRotation() / -90) + 4) % 4;
		if(!outer && collisionMatrix.isBlocked(x, y)) {
			return 0;
		}
		switch(dir) {
		case MapBuilder.DIR_NORTH:
			length = 40 * (y+1);
			for(int i = 0; i <= y; i++) {
				if(collisionMatrix.cannotTravel(x, y-i, dir)) {
					length = 40 * (i+1);
					break;
				}
			}
			break;
		case MapBuilder.DIR_SOUTH:
			length = 40 * (16-y);
			for(int i = y; i <= 16; i++) {
				if(collisionMatrix.cannotTravel(x, i, dir)) {
					length = 40 * (i-y+1);
					break;
				}
			}
			break;
		case MapBuilder.DIR_WEST:
			length = 40 * (x+1);
			for(int i = 0; i <= x; i++) {
				if(collisionMatrix.cannotTravel(x-i, y, dir)) {
					length = 40 * (i+1);
					break;
				}
			}
			break;
		case MapBuilder.DIR_EAST:
			length = 40 * (12-x);
			for(int i = x; i <= 12; i++) {
				if(collisionMatrix.cannotTravel(i, y, dir)) {
					length = 40 * (i-x+1);
					break;
				}
			}
			break;
		}
		return length;
	}

	@Override
	public void animate(float timeDelta) {
		if(!hasCalc) {
			setHeight(calcHeight());
			layout();
			hasCalc = true;
		}
	}
}
