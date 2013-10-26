package se.chalmers.dryleafsoftware.androidrally.client;


public class Client{
	private String ID = "-1";
	//TODO Game ID?
	
	public Client(){
		this.ID = loadLocalID();
	}
	
	public String getID() {
		return ID;
	}

	private String loadLocalID() {
		//TODO should load id local from file on unit
		return "-1";
	}
	
	public void setID(String iD) {
		this.ID = iD;
		System.out.println("Client ID set to:" + iD);
	}

}
