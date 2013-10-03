package se.chalmers.dryleafsoftware.androidrally.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.utils.Timer;

public class CardTimer extends Timer {
	
	private int robotNbr;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String CARD_TIME_OUT = "cardTimeOut";
	private long secondsInFuture;
	
	public CardTimer(long secondsInFuture, int robotID, PropertyChangeListener pcl) {
		super();
		this.secondsInFuture = secondsInFuture;
		this.robotNbr = robotID;
		pcs.addPropertyChangeListener(pcl);
		stop();
	}
	
	public void resetCardTimer() {
		schedule(new Timer.Task() {
			@Override
			public void run() {
				pcs.firePropertyChange(CARD_TIME_OUT, -1, robotNbr);
			}
		}, secondsInFuture);
	}
	
	/**
	 * NEVER CALL if you have not first called resetCardTimer right before.
	 * <br>
	 * How the API is built is the problem.
	 */
	@Override
	public void start() {
		super.start();
	}
}
