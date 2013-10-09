package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.AnimatedImage;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * Action for when a robot dies.
 * 
 * @author 
 *
 */
public class ExplodeAction extends GameAction {

	private final AnimatedImage animation;
	
	/**
	 * Creates a new instance which will handle the robot with the specified ID.
	 * @param robotID The ID of the robot to handle.
	 */
	public ExplodeAction(int robotID, Texture texture) {
		super(robotID, 1000);
		this.animation = new AnimatedImage(texture, 1, 1, 1000);
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		robots.get(getRobotID()).addAction(Actions.parallel(
				Actions.fadeOut(getDuration() / 1000f),
				Actions.scaleTo(1.8f, 1.8f, getDuration() / 1000f)));
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		robots.get(getRobotID()).setDead(true);
		robots.get(getRobotID()).addAction(Actions.parallel(
				Actions.fadeOut(0),
				Actions.scaleTo(1.8f, 1.8f, 0)));
	}

}
