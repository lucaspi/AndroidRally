package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import se.chalmers.dryleafsoftware.androidrally.sharred.MapBuilder;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LaserView extends AnimatedImage {
	
	private int x, y;
	private int dir;
	private boolean[][][] collisionMatrix;
	private TextureRegion texture;
	
	public LaserView(TextureRegion texture, int x, int y, int dir) {
		super(texture);
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void setCollisionMatrix(boolean[][][] collisionMatrix) {
		this.collisionMatrix = collisionMatrix;
	}
	
	int length;
	boolean hasCalc = false;
	
	@Override
	public void draw(SpriteBatch spriteBatch, float a) {
		if(getEnabled()) {
			spriteBatch.draw(
					texture,
			        getX() + 4,
			        getY(),
			        texture.getRegionWidth()/2,
			        texture.getRegionHeight()/2 + 4,
			        texture.getRegionWidth(),
			        length,
			        1,
			        1,
			        -dir * 90);
		}
	}

	@Override
	public void animate(float timeDelta) {
		if(!hasCalc) {
			switch(dir) {
			case MapBuilder.DIR_NORTH:
				length = 40 * (y+1);
				for(int i = 0; i <= y; i++) {
					if(collisionMatrix[x][y - i][0]) {
						length = 40 * (i+1);
						break;
					}
				}
				break;
			case MapBuilder.DIR_SOUTH:
				length = 40 * (16-y);
				for(int i = y; i < 16; i++) {
					if(collisionMatrix[x][i][0]) {
						length = 40 * (i-y);
						break;
					}
					System.out.println(x + ", " + i + ", " + collisionMatrix[x][i][0]);
				}
				break;
			case MapBuilder.DIR_WEST:
				length = 40 * (x+1);
				for(int i = 0; i <= x; i++) {
					if(collisionMatrix[x - i][y][1]) {
						length = 40 * (i+1);
						break;
					}
				}
				break;
			case MapBuilder.DIR_EAST:
				length = 40 * (12-x);
				for(int i = x; i < 12; i++) {
					if(collisionMatrix[i][y][1]) {
						length = 40 * (i-x);
						break;
					}
					System.out.println(x + ", " + i + ", " + collisionMatrix[x][i][0]);
				}
				break;
			}
			hasCalc = true;
		}
	}
}
