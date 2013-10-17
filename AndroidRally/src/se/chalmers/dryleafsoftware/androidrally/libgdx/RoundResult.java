package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.actions.GameAction;

/**
 * This holds all the result from one round, but keeps the actions from each
 * phase separate.
 * 
 * @author
 * 
 */
public class RoundResult {

	private final List<List<GameAction>> actions;
	private List<GameAction> currentList;
	private int returnIndex = 0;

	/**
	 * Creates a new empty instance.
	 */
	public RoundResult() {
		this.actions = new ArrayList<List<GameAction>>();
	}

	/**
	 * Clears all the actions.
	 */
	public void clear() {
		actions.clear();
	}

	/**
	 * Sets the next list as the current.
	 */
	public void newPhase() {
		currentList = new ArrayList<GameAction>();
		actions.add(currentList);
	}

	/**
	 * Adds the specified action to the result.
	 * 
	 * @param action
	 *            The action to add.
	 */
	public void addAction(GameAction action) {
		currentList.add(action);
	}

	/**
	 * Gives <code>true</code> if there is more result to be fetched from here.
	 * 
	 * @return <code>true</code> if there is more result to be fetched from
	 *         here.
	 */
	public boolean hasNext() {
		return returnIndex < actions.size();
	}
	
	/**
	 * Gives the next phase's actions.
	 * 
	 * @return A list of the next phase's actions.
	 */
	public List<GameAction> getNextResult() {
		return actions.get(returnIndex++);
	}
}
