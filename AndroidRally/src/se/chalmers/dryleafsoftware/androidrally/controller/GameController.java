package se.chalmers.dryleafsoftware.androidrally.controller;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Timer;

import se.chalmers.dryleafsoftware.androidrally.model.cards.Card;
import se.chalmers.dryleafsoftware.androidrally.model.gameModel.GameModel;
import se.chalmers.dryleafsoftware.androidrally.model.robots.Robot;

public class GameController implements PropertyChangeListener {
	private GameModel gameModel;
	private Timer timer;
	private Timer.Task endOfRound;
	private int hoursEachRound;
	private boolean isRunRunning;
	private int nbrOfRobotsDone;
	private CardTimer[] cardTimer;
	private int nbrOfRobotsStilInGame;
	private String nbrOfPlayers;
	private String mapAsString;


	public GameController(int nbrOfPlayers) {
		this.nbrOfPlayers = String.valueOf(nbrOfPlayers);
		isRunRunning = false;
		nbrOfRobotsDone = 0;
		gameModel = new GameModel(this, nbrOfPlayers);
		
		String[][] map = this.gameModel.getMap();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			sb.append("y");
			for (int j = 0; j < map[0].length; j++) {
				sb.append("x");
				sb.append(map[i][j]);
			}
		}
		mapAsString = sb.toString();
		
		timer = new Timer();
		endOfRound = new Timer.Task() {
			/* Method that is executing if the round time is out or
			 * all robots are done playing their cards. */
			@Override
			public void run() {
				isRunRunning = true;
				stopRoundTimer();
				handleRemainingRobots();
				gameModel.moveRobots();
				//If the game is over a new round will not be started. Game will end.
				if (!gameModel.isGameOver()) {
					newRound();
				}
				isRunRunning = false;
			}
		};
		cardTimer = new CardTimer[nbrOfPlayers];
		for (int i = 0; i < nbrOfPlayers; i++) {
			cardTimer[i] = new CardTimer(30, i, this);
		}
		hoursEachRound = 24;
	}
	
	/**
	 * DO NOT USE!!!!!!!!!!!!!!!!!!
	 * TODO: remove
	 * @return
	 */
	public GameModel getModel() {
		// TODO: remove
		return this.gameModel;
	}

	public void handleRemainingRobots() {
		for (int i = 0; i < gameModel.getRobots().size(); i++) {
			if (!gameModel.getRobots().get(i).haveSentCards()) {
				setChosenCardsToRobot(new int[]{0,0,0,0,0}, i);
			}
		}
	}

	/**
	 * Timer is scheduled to what hoursEachRound is set to.
	 * 24 hours as default.
	 */
	public void startRoundTimer() {
		Timer.schedule(endOfRound, hoursEachRound * 3600000);
	}

	public void stopRoundTimer() {
		timer.stop();
		//timer.clear(); //FIXME If there are problems with the timer, this might be it
	}

	/**
	 * Set the given input from the client to a specific robots chosen cards.
	 * 
	 * @param indexOfChosenCard array of length == 5. Index of the robot's card that the robot have chosen.
	 * @param robotID The index of the robot in the list of robots held by GameModel
	 */
	public synchronized void setChosenCardsToRobot(int[] indexOfChosenCard, int robotID) { //TODO ClientID?
		cardTimer[robotID].stop();
		//cardTimer[robotID].clear(); //FIXME If there are problems with the timer, this might be it
		List<Card> chosenCards = new ArrayList<Card>();
		Robot robot = gameModel.getRobots().get(robotID);
		for (int i = 0; i < 5; i++) {
			if (indexOfChosenCard[i] == -1) {
				chosenCards.add(null);
			} else {
				if (!chosenCards.contains(robot.getCards().get(indexOfChosenCard[i]))) {
					chosenCards.add(robot.getCards().get(indexOfChosenCard[i]));
				} else {
					chosenCards.add(null);
				}
			}
		}
		robot.setChosenCards(chosenCards);
		robot.setSentCards(true);
		nbrOfRobotsDone++;
		if(nbrOfRobotsStilInGame == nbrOfRobotsDone && !isRunRunning) {
			endOfRound.run();
		}

	}

	/**
	 * Get how long the round is totally.
	 * @return the number of hours a round is
	 */
	public int getHoursEachRound() {
		return hoursEachRound;
	}

	/**
	 * Set how long a round should be. (24 hours as default in the constructor).
	 * @param hoursEachRound the number of hours a round should be
	 */
	public void setHoursEachRound(int hoursEachRound) {
		this.hoursEachRound = hoursEachRound;
	}

	/**
	 * If the server hasn't received information from the client within
	 * the time of the card timer + ping "time out"-time randomized cards
	 * will be given to the robot that the card timer went out from.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals(CardTimer.CARD_TIME_OUT)) {
			setChosenCardsToRobot(new int[]{-1,-1,-1,-1,-1}, (Integer)pce.getNewValue());
		} else if (pce.getPropertyName().equals(GameModel.ROBOT_LOST)) {
			//send to client that a specific robot lost
		} else if (pce.getPropertyName().equals(GameModel.ROBOT_WON)) {
			//send to all clients which robot that has won
		}
	}

	/**
	 * Get the cards of a specific robot.
	 * @param robotID the index of the robot in the GameModel's robot list.
	 * @return the robot's cards (not chosen cards)
	 */
	public List<Card> getCards(int robotID) {
		System.out.println("getCards anropas f�r " + robotID);
		cardTimer[robotID] = new CardTimer(30, robotID, this);
		return gameModel.getRobots().get(robotID).getCards();
	}

	/**
	 * All robots will receive new cards and
	 * the round timer will start.
	 * <p>
	 * ONLY call when a new game is to be started.
	 * After that the method will be called when another
	 * round has ended (unless the game is over).
	 */
	public void newRound() {
		gameModel.dealCards();
		startRoundTimer();
		
		//Check how many robots that are still alive this round
		nbrOfRobotsStilInGame = 0;
		for (Robot robotInList : gameModel.getRobots()) {
			if (robotInList.getLife() > 0) {
				nbrOfRobotsStilInGame++;
			}
		}
		nbrOfRobotsDone = 0;
	}
	
	public String getMap() {
		return mapAsString;
	}
	
	public String getNbrOfPlayers() {
		return nbrOfPlayers;
	}
	
	public String getRoundResults() {
		return this.gameModel.getAllMoves();
	}
}