package se.chalmers.dryleafsoftware.androidrally.shared;

import se.chalmers.dryleafsoftware.androidrally.game.GameSettings;

public interface IProtocol {
	
	@OperatorValue( value = 1)
	public String getMap();

	@OperatorValue( value = 2)
	public int getRound();
	
	@OperatorValue( value = 3)
	public String getInitGameData() ;
	
	@OperatorValue( value = 4)
	public String getNbrOfRobots();

	@OperatorValue( value = 5)
	public String getCards(int i, int robotID);

	@OperatorValue( value = 6)
	public void save(int gameID);

	@OperatorValue( value = 7)
	public String getRoundResults(int i);

	@OperatorValue( value = 8)
	public void setChosenCardsToRobot(int robotID, String substring) ;
	
	@OperatorValue( value = 9)
	public void createGame(GameSettings settings);

}
