package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import se.chalmers.dryleafsoftware.androidrally.libgdx.AnimatedImage;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * Action for when a robot is hit by a laser.
 * 
 * @author 
 *
 */
public class LaserHitAction extends GameAction {

	private final AnimatedImage animation;
	
	/**
	 * Creates a new instance which will handle the robot with the specified ID.
	 * @param robotID The ID of the robot to handle.
	 */
	public LaserHitAction(int robotID, Texture texture) {
		super(robotID, 1000);
		this.animation = new AnimatedImage(texture, 4, 2, 1000);
		
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		RobotView r = robots.get(getRobotID());
		animation.setSize(r.getWidth(), r.getHeight());
		animation.setPosition(r.getX(), r.getY());
		animation.setOrigin(r.getOriginX(), r.getOriginY());
		r.getParent().addActor(animation);
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		robots.get(getRobotID()).getParent().removeActor(animation);
	}

}
