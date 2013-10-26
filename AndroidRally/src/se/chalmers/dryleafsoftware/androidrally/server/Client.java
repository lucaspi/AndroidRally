package se.chalmers.dryleafsoftware.androidrally.server;

public class Client {
	private static int ID = 0;
	
	public static String generateID(){
		ID++;
		return ID + "";
		//TODO
	}

	public static boolean IDExists(String iD) {
		// TODO Auto-generated method stub
		return false;
	}

	public static String getCorrectID(String iD) {
		if (iD.equalsIgnoreCase("-1") || Client.IDExists(iD)){
			iD = Client.generateID();
		}
		// TODO Auto-generated method stub
		return iD;
	}
}