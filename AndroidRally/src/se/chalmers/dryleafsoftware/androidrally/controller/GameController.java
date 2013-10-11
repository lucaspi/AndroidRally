package se.chalmers.dryleafsoftware.androidrally.controller;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	private String nbrOfRobots;
	private String mapAsString;
	private AIRobotController aiRobotController;
	private int nbrOfHumanPlayers;
	private int nbrOfBots;
	private List<String> allMoves;
	private List<String[]> allCards;
	private int cardTimerSeconds;

	public GameController(int nbrOfHumanPlayers, int nbrOfBots, int hoursEachRound, int cardTimerSeconds, String map) {
		this.nbrOfHumanPlayers = nbrOfHumanPlayers;
		this.nbrOfBots = nbrOfBots;
		isRunRunning = false;
		gameModel = new GameModel(nbrOfHumanPlayers + nbrOfBots);
		this.nbrOfRobots = String.valueOf(gameModel.getRobots().size());
		allMoves = new ArrayList<String>();
		allCards = new ArrayList<String[]>();
		
		aiRobotController = new AIRobotController(gameModel.getGameBoard());
		
		mapAsString = gameModel.getMap();
		
		cardTimerSeconds = Math.max(cardTimerSeconds, 15); //Make cardTimerSeconds be in the interval 1-24
		cardTimerSeconds = Math.min(cardTimerSeconds, 180); // -''-
		this.cardTimerSeconds = cardTimerSeconds;
		//FIXME Kolla med Lucas om 15, 180, 1 och 24 �r r�tt v�rden!!
		hoursEachRound = Math.max(hoursEachRound, 1); //Make hoursEachRound be in the interval 1-24
		hoursEachRound = Math.min(hoursEachRound, 24);// -''-
		this.hoursEachRound = hoursEachRound;
		
		timer = new Timer();
		cardTimer = new CardTimer[Integer.parseInt(nbrOfRobots)];
		for (int i = 0; i < Integer.parseInt(nbrOfRobots); i++) {
			cardTimer[i] = new CardTimer(cardTimerSeconds, i); //let the time be a variable
			cardTimer[i].addPropertyChangeListener(this);
		}
	}

	private void handleRemainingRobots() {
		for (int i = 0; i < gameModel.getRobots().size(); i++) {
			if (!gameModel.getRobots().get(i).haveSentCards()) {
				setRandomCards(i);
			}
		}
	}

	/**
	 * Timer is scheduled to what hoursEachRound is set to.
	 * 24 hours as default.
	 */
	public void startRoundTimer() {
		reScheduleTimer();
		timer.schedule(endOfRound, hoursEachRound * 3600000);
	}

	public void stopRoundTimer() {
		endOfRound.cancel();
	}
	
	private void reScheduleTimer() {
		endOfRound = new TimerTask() {
			/* Method that is executing if the round time is out or
			 * all robots are done playing their cards. */
			@Override
			public synchronized void run() {
				isRunRunning = true;
				stopRoundTimer();
				handleRemainingRobots();
				
				gameModel.moveRobots();
				allMoves.add(gameModel.getAllMoves());
				//If the game is over a new round will not be started. Game will end.
				if (!gameModel.isGameOver()) {
					newRound();
				} else {
					timer.cancel();
				}
				isRunRunning = false;
			}
		};
	}

	/**
	 * Set the given input from the client to a specific robots chosen cards.
	 * 
	 * @param chosenCards should contain data about the robot and the chosenCards according to
	 * separate document.
	 * @return a String containing data of the locked cards.
	 */
	public synchronized void setChosenCardsToRobot(int robotID, String chosenCards) {
		cardTimer[robotID].cancelTask();
		if(!gameModel.getRobots().get(robotID).hasLost()){
			try {
				String[] cardStrings = chosenCards.split(":");
				List<Card> cards = new ArrayList<Card>();
				Robot robot = gameModel.getRobots().get(robotID);
				for (int i = 0; i < 5; i++) {
					if (Integer.parseInt(cardStrings[i]) == -1) {
						cards.add(null);
					} else if(Integer.parseInt(cardStrings[i]) < robot.getCards().size()){
						cards.add(robot.getCards().get(Integer.parseInt(cardStrings[i])));
					}
				}
				robot.setChosenCards(cards);
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
			gameModel.getRobots().get(robotID).fillEmptyCardRegisters();
			gameModel.getRobots().get(robotID).setSentCards(true);
			gameModel.getRobots().get(robotID).setLastChosenCards(getCurrentChosenCards(robotID));
			String[] cards = new String[gameModel.getRobots().size()];
			for(int i = 0; i < gameModel.getRobots().size(); i++){
				cards[i] = gameModel.getRobots().get(i).getLastRoundChosenCards();
			}
			allCards.add(new String[gameModel.getRobots().size()]);
		}
		nbrOfRobotsDone++;

		if(gameModel.getRobotsPlaying() == nbrOfRobotsDone && !isRunRunning) {
			endOfRound.run();
		}
	}
	
	private String getCurrentChosenCards(int robotID){
		StringBuilder sb = new StringBuilder();
		for(Card card : gameModel.getRobots().get(robotID).getChosenCards()) {
			sb.append(card.getPriority() + ":");
		}
		return sb.toString();
	}
	
	/**
	 * Return a string representing the last chosen cards from a specific robot.
	 * @param robotID the id of the robot to get cards for.
	 * @return a string representing the last chosen cards from a specific robot.
	 */
	public String getChosenCards(int robotID){
		return gameModel.getRobots().get(robotID).getLastRoundChosenCards();
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
			setRandomCards((Integer) pce.getNewValue());
		}
	}

	/**
	 * Get the cards of a specific robot.
	 * @param robotID the index of the robot in the GameModel's robot list.
	 * @return the robot's cards represented as a string (not chosen cards).
	 */
	public String getCards(int robotID) {
		List<Card> cards = gameModel.getRobots().get(robotID).getCards();
		Card[] chosenCards = gameModel.getRobots().get(robotID).getChosenCards();
		StringBuilder sb = new StringBuilder();
		
		for(Card c : cards) {
			for(int i = 0; i < chosenCards.length; i++) {
				if(chosenCards[i] == c) {
					sb.append("L" + i + ";");
					break;
				}
			}
			sb.append(c.getPriority() + ":");
		}
		System.out.println("getCards" + sb.toString());
		cardTimer[robotID].start();
		return sb.toString(); 
	}
	
	/**
	 * Gives data the client needs when connecting to a game. (Such as the length of the 
	 * timers etc.)
	 * @return Data the client needs when connecting.
	 */
	public String getInitGameData() {
		return cardTimerSeconds + ";" + hoursEachRound;
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
		System.out.println("------------------------newRound---------------------------");
		gameModel.dealCards();
		startRoundTimer();
		nbrOfRobotsDone = 0;
		for (int i = nbrOfHumanPlayers ; i < Integer.parseInt(nbrOfRobots); i++) {
			aiRobotController.makeMove(gameModel.getRobots().get(i));
		}
	}
	
	public String getMap() {
		return mapAsString;
	}
	
	public String getNbrOfPlayers() {
		return nbrOfRobots;
	}
	
	/**
	 * Return a string containing all data from the last round.
	 * @param round the round to get results from. The first round is 1 and not 0.
	 * @return a string containing all data from the last round.
	 */
	public String getRoundResults(int round) {
		return allMoves.get(round-1);
	}
	
	/**
	 * Returns a String representing the chosenCards for a specific robot
	 * and round.
	 * @param round the round to get the cards from. The first round is 1 and not 0.
	 * @param robot the robotID to get cards for
	 * @return the chosenCards for a specific robot and round.
	 */
	public String getCards(int round, int robot){
		if(round > allMoves.size()) {
			return getCards(robot);
		}else{
			return allCards.get(round-1)[robot];
		}
	}
	
	/**
	 * Return the current round. The first round is 1 and not 0.
	 * @return the current round.
	 */
	public int getRound(){
		return allMoves.size();
	}
	
	private void setRandomCards(int robotID) {
		setChosenCardsToRobot(robotID, ":-1:-1:-1:-1:-1");
	}
}