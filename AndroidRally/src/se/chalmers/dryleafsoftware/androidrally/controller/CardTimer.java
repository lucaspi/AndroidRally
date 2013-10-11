package se.chalmers.dryleafsoftware.androidrally.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;


public class CardTimer {
	
	private int robotNbr;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String CARD_TIME_OUT = "cardTimeOut";
	private long seconds;
	private Timer timer;
	private TimerTask timeOut;
	
	public CardTimer(long seconds, int robotID) {
		timer = new Timer();
		this.seconds = seconds * 1000;
		this.robotNbr = robotID;
	}
	
	/**
	 * Starts the timer based on the number of seconds given in the
	 * constructor. Sends an event to listeners with name given by
	 * static modifier CardTimer.CARD_TIME_OUT.
	 */
	public void start() {
		reSchedule();
		timer.schedule(timeOut, seconds);
	}

	public void cancelTask() {
		if (timeOut != null) {
			timeOut.cancel();
		}
	}
	
	private void reSchedule() {
		timeOut = new TimerTask() {
			@Override
			public void run() {
				pcs.firePropertyChange(CARD_TIME_OUT, -1, robotNbr);
			}
		};
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
