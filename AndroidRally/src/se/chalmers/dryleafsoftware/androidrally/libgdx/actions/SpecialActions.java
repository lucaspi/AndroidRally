package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Holds special actions as static instances.
 * 
 * @author
 * 
 */
public class SpecialActions {

	/**
	 * Simulates falling in a dark hole.
	 */
	public static final Action HOLE_ACTION = Actions.parallel(
			Actions.fadeOut(1), Actions.scaleTo(0.3f, 0.3f, 1));
}
