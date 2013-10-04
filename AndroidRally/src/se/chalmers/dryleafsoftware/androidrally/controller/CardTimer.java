package se.chalmers.dryleafsoftware.androidrally.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.badlogic.gdx.utils.Timer;

public class CardTimer {
	
	private int robotNbr;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String CARD_TIME_OUT = "cardTimeOut";
	private long seconds;
	private Timer timer;
	
	public CardTimer(long seconds, int robotID) {
		super();
		timer = new Timer();
		timer.stop();
		this.seconds = seconds;
		this.robotNbr = robotID;
	}
	
	private void resetCardTimer() {
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				pcs.firePropertyChange(CARD_TIME_OUT, -1, robotNbr);
			}
		}, seconds);
	}
	
	/**
	 * Starts the timer based on the number of seconds given in the
	 * constructor. Sends an event to listeners with name given by
	 * static modifier CardTimer.CARD_TIME_OUT.
	 */
	public void start() {
		resetCardTimer();
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void clear() {
		timer.clear();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
