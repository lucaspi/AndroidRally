package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.PlayerView;

public class RotationAction extends GameAction {

	private int rotTimes;
	
	public RotationAction(int player, int rotTimes) {
		super(player);
		this.rotTimes = rotTimes;
	}

	@Override
	public void action(List<PlayerView> players) {
		players.get(getPlayer()).addAction(Actions.rotateTo(-rotTimes * 90, 1));		
	}
}
