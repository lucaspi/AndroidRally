package se.chalmers.dryleafsoftware.androidrally.shared;

public abstract class ACClient {
	private String ID = "-1";
	private ACConnection connection = null;
	
	public ACClient(ACOperator o){
		this.connection = generateConnection(o);		
	}
	
	protected abstract ACConnection generateConnection(ACOperator o);
	
	
	public ACConnection getConnection() {
		return connection;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}


}
