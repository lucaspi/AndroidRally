package se.chalmers.dryleafsoftware.androidrally.shared;

public abstract class ACClient {
	private IConnection connection = null;
//	private IDataHandler dataHandler = null;
	private String ID = "-1";
	
	public ACClient(IConnection c){
		this.connection = c;
//		this.dataHandler = d;
	}
	

	public IConnection getConnection() {
		return connection;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

//	public IDataHandler getDataHandler() {
//		return dataHandler;
//	}
	

}
