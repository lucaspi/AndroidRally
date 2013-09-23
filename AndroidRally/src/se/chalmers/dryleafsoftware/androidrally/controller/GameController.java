package se.chalmers.dryleafsoftware.androidrally.controller;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.CountDownTimer;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GameController implements PropertyChangeListener {
	private GameModel gameModel;
	private Timer timer;
	private TimerTask endOfRound;
	private int hoursEachRound;
	private boolean isRunRunning;
	private int nbrOfRobotsDone;
	private CardTimer[] cardTimer;
	
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
				startRoundTimer(); //TODO maybe move?
				nbrOfRobotsDone = 0;
				isRunRunning = false;
			}
		};
		cardTimer = new CardTimer[nbrOfPlayers];
		for (int i = 0; i < nbrOfPlayers; i++) {
			cardTimer[i] = new CardTimer(65000, 1000, i, this);
		}
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
	public void startRoundTimer() { //TODO maybe do more stuff?
		
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
		cardTimer[robotID].cancel();
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

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals("cardTimeOut")) {
			setChosenCardsToRobot(new int[]{0,0,0,0,0}, (Integer)pce.getNewValue());
		}
	}
	
	public List<Card> getCards(int robotID) {
		return null; //FIXME dealCards 
	}	
}