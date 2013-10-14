package se.chalmers.dryleafsoftware.androidrally.shared;

public interface IDataHandler {
	public void handle(String data, IConnection connection);
}
