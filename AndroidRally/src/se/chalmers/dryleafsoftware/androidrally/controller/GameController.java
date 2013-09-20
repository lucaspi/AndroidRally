package se.chalmers.dryleafsoftware.androidrally.controller;

import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;

public class GameController {
	GameModel gameModel;
	
	public GameController(int nbrOfPlayers) {
		gameModel = new GameModel(nbrOfPlayers);
	}

	public GameModel getGameModel() {
		return gameModel;
	}
}