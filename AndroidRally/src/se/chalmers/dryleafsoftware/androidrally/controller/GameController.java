package se.chalmers.dryleafsoftware.androidrally.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.CountDownTimer;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GameController {
	private GameModel gameModel;
	private Timer timer;
	private TimerTask endOfRound;
	private int hoursEachRound;
	private boolean isRunRunning;
	private int nbrOfRobotsDone;
	
	public GameController(int nbrOfPlayers) {
		isRunRunning = false;
		nbrOfRobotsDone = 0;
		gameModel = new GameModel(nbrOfPlayers);
		timer = new Timer();
		endOfRound = new TimerTask() {
			@Override
			public void run() {
				isRunRunning = true;
				stopRoundTimer();
				handleRemainingRobots();
				gameModel.moveRobots();
				startRoundTimer();
				nbrOfRobotsDone = 0;
				isRunRunning = false;
			}
		};
		
		hoursEachRound = 24;
	}

	public void handleRemainingRobots() {
		for (int i = 0; i < gameModel.getRobots().size(); i++) {
			if (!gameModel.getRobots().get(i).haveSentCards()) {
				setChosenCardsToRobot(new int[]{0,0,0,0,0}, i);
			}
		}
	}

	public GameModel getGameModel() {
		return gameModel;
	}
	
	/**
	 * Timer is scheduled to what hoursEachRound is set to.
	 * 24 hours as default.
	 */
	public void startRoundTimer() {
		timer.schedule(endOfRound, hoursEachRound * 3600000);
	}
	
	public void stopRoundTimer() {
		timer.cancel();
		timer.purge(); //FIXME If there are problems with the timer, this might be it
	}
	
	/**
	 * Set the given input from the client to a specific robots chosen cards.
	 * 
	 * @param indexOfChosenCard array of length == 5. Index of the robot's card that the robot have chosen.
	 * @param robotID The index of the robot in the list of robots held by GameModel
	 */
	public void setChosenCardsToRobot(int[] indexOfChosenCard, int robotID) { //TODO ClientID?
		List<Card> chosenCards = new ArrayList<Card>();
		Robot robot = gameModel.getRobots().get(robotID);
		for (int i = 0; i < 5; i++) {
			if (indexOfChosenCard[i] == -1) {
				chosenCards.add(null);
			} else {
				chosenCards.add(robot.getCards().get(indexOfChosenCard[i]));
			}
		}
		robot.setChosenCards(chosenCards);
		robot.setSentCards(true);
		nbrOfRobotsDone++;
		
		if(gameModel.getRobots().size() == nbrOfRobotsDone && !isRunRunning) {
			endOfRound.run();
		}
		
	}

	public int getHoursEachRound() {
		return hoursEachRound;
	}

	public void setHoursEachRound(int hoursEachRound) {
		this.hoursEachRound = hoursEachRound;
	}
	
	
	
}