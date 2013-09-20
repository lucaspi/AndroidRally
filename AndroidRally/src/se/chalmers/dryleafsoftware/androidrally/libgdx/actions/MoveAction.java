package se.chalmers.dryleafsoftware.androidrally.libgdx.actions;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.PlayerView;

public class MoveAction extends GameAction {

	private final int x, y;
	
	public MoveAction(int player, int x, int y) {
		super(player);
		this.x = x;
		this.y = y;
	}

	@Override
	public void action(List<PlayerView> players) {
		players.get(getPlayer()).addAction(Actions.moveBy(x * 40,  y * 40, 1));
	}

}
