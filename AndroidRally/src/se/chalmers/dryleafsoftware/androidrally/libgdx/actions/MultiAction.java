package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

/**
 * A MultiAction can do several SingleActions at the same time.
 * 
 * @author
 *
 */
public class MultiAction extends GameAction {

	private List<GameAction> actions = new ArrayList<GameAction>();
	
	/**
	 * Creates a new empty instance.
	 */
	public MultiAction() {
		super(-1, -1);		
	}
	
	/**
	 * Creates a new instance which will add the specified actions.
	 * @param action The actions to add.
	 */
	public MultiAction(SingleAction... action) {
		this();
		for(GameAction a : action) {
			add(a);
		}		
	}
	
	/**
	 * Adds the specified action.
	 * @param a The action to add.
	 */
	public void add(GameAction a) {
		actions.add(a);
		setDuration(Math.max(getDuration(), a.getDuration()));
	}

	@Override
	public void action(List<RobotView> robots) {
		start();
		for(GameAction a : actions) {
			a.action(robots);
		}
	}

	@Override
	public void cleanUp(List<RobotView> robots) {
		for(GameAction a : actions) {
			a.cleanUp(robots);
		}
	}
}
