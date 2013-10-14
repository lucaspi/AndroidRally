package se.chalmers.dryleafsoftware.androidrally.server;

import se.chalmers.dryleafsoftware.androidrally.shared.IConnection;

public class Operator {
	public void handle(Game game, String operatorString, String data){
		int operator = Integer.parseInt(operatorString);
		switch (operator){
			case 1: {
				break;
			} default : {
				break;
			}
		}
	}

	public static void handle(IConnection connection, String clientID,
			Game game, String substring) {
		// TODO Auto-generated method stub
		
	}
}
