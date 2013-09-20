package se.chalmers.dryleafsoftware.androidrally.controller;


import java.util.Timer;
import java.util.TimerTask;

import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;

public class GameController {
	private GameModel gameModel;
	private Timer timer;

	
	public GameController(int nbrOfPlayers) {
		gameModel = new GameModel(nbrOfPlayers);
		timer = new Timer();
	}

	public GameModel getGameModel() {
		return gameModel;
	}
	
	public void startRoundTimer() {
		TimerTask endOfRound = new TimerTask() {
			@Override
			public void run() {
				//ge slumpade kort till robotar som inte 
			}
		};
//		timer.schedule(end, when)

		
		
	}
	
	public void stopRoundTimer() {
		timer.cancel();
		timer.purge();
	}
	
	
	
}