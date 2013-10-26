package se.chalmers.dryleafsoftware.androidrally.client;

import java.lang.reflect.Method;

import se.chalmers.dryleafsoftware.androidrally.shared.IProtocol;
import se.chalmers.dryleafsoftware.androidrally.shared.OperatorValue;
import se.chalmers.dryleafsoftware.androidrally.controller.GameController;
import se.chalmers.dryleafsoftware.androidrally.game.GameSettings;

public class Protocol implements IProtocol {
//	private Connection connection = new Connection();
	private GameController controller = null; 

	@Override
	@OperatorValue(1)
	public String getMap() {
		// TODO Auto-generated method stub
		return controller.getMap();
//		return connection.send("1$");
	}

	@Override
	@OperatorValue(2)
	public int getRound() {
		return controller.getRound();
		// TODO Auto-generated method stub
//		return Integer.parseInt(connection.send("2$"));
	}

	@Override
	@OperatorValue(3)
	public String getInitGameData() {
		return controller.getInitGameData();
		// TODO Auto-generated method stub
//		return  connection.send("3$");
	}

	@Override
	@OperatorValue(4)
	public String getNbrOfRobots() {
		return controller.getNbrOfRobots();
		// TODO Auto-generated method stub
//		return  connection.send("4$");
	}

	@Override
	@OperatorValue(5)
	public String getCards(int i, int robotID) {
		return controller.getCards(i, robotID);
		// TODO Auto-generated method stub
//		return  connection.send("5$" + i +"$" + robotID + "$");
	}

	@Override
	@OperatorValue(6)
	public void save(int gameID) {
		controller.save(gameID);
		// TODO Auto-generated method stub
//		 connection.send("6$" + gameID + "$");
	}

	@Override
	@OperatorValue(7)
	public String getRoundResults(int i) {
		return controller.getRoundResults(i);
		// TODO Auto-generated method stub
//		return  connection.send("7$" + i + "$");
	}

	@Override
	@OperatorValue(8)
	public void setChosenCardsToRobot(int robotID, String substring) {
		controller.setChosenCardsToRobot(robotID, substring);
		// TODO Auto-generated method stub
//		 connection.send("8$" + robotID + "$" + substring + "$");
		
	}

	@Override
	@OperatorValue(9)
	public void createGame(GameSettings settings) {
			
		// TODO Auto-generated method stub
//		resetGameValues();
		this.controller = new GameController(settings.getNbrOfHumanPlayers(),
				settings.getNbrOfBots(), settings.getHoursEachRound(),
				settings.getCardTimerSeconds(), settings.getRandomMap());
		controller.newRound();
		
	}

	
}
