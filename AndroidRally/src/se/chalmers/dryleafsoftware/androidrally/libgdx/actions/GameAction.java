package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.PlayerView;

public abstract class GameAction {

	private int player;
	
	public GameAction(int player) {
		this.player = player;
	}
	
	protected int getPlayer() {
		return this.player;
	}
	
	public abstract void action(List<PlayerView> players);
}
