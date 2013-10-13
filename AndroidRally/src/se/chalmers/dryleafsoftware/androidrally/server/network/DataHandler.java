package se.chalmers.dryleafsoftware.androidrally.server.network;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.controller.GameController;
import se.chalmers.dryleafsoftware.androidrally.network.Connection;
import se.chalmers.dryleafsoftware.androidrally.network.IDataHandler;

public class DataHandler implements IDataHandler{
	private int newID = 0;
	private int newGameID = 0;
	private List<GameController> gameControllers;
	
	
	public DataHandler(List<GameController> gameControllers) {
		this.gameControllers=gameControllers;
		// TODO Auto-generated constructor stub
	}

	public void handle(String data, Connection connection) {
		if (data.substring(0, data.indexOf('$')).equalsIgnoreCase("-1")){//FIXME kan vara fel
			connection.send(generateNewID() + "$");
		}
		
		String tempData = data.substring(data.indexOf('$')+1);
		
		if (tempData.substring(0, tempData.indexOf('$')).equalsIgnoreCase("-1")){
//			int gameID=createNewGame().getGameID();
//			//TODO
//			connection.send("0$"+gameID+"$");
		} else {
			int gameID = parseFirst(tempData, '$');
			
			for (GameController tempGameController : gameControllers){
//				if (tempGameController.getGameID() == gameID){
//					
//					//TODO send data
//				}
			}
			
		}
		

		// TODO Auto-generated method stub
		
	}

	private GameController createNewGame() {
//		GameController c = new GameController(1, 7, 24, 120, null, generateNewGameID());
//		gameControllers.add(c);
		// TODO Auto-generated method stub
		return null;
		
	}

	private int generateNewGameID() {
		newGameID++;
		// TODO Auto-generated method stub
		return newGameID;
	}

	private int generateNewID() {
		newID++;
		// TODO Auto-generated method stub
		return newID;
	}
	
	private int parseFirst(String s, char c){
		try {
			return Integer.parseInt(s.substring(0, s.indexOf(c)));
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

}
