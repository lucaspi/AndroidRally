package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This view renders a robot.
 * 
 * @author 
 *
 */
public class RobotView extends Image  {

	private final int robotID;
	
	/**
	 * Creates a new instance of a robot with the specified ID-number.
	 * @param robotID The ID-number of this robot.
	 * @param texture The texture to use.
	 */
	public RobotView(int robotID, TextureRegion texture) {
		super(texture);
		this.setSize(40, 40);
		this.robotID = robotID;
	}
	
	/**
	 * Gives the ID-number of the robot.
	 * @return The ID-number of the robot.
	 */
	public int getRobotID() {
		return robotID;
	}
}
