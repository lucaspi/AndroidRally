package se.chalmers.dryleafsoftware.androidrally.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A timer that checks that the player places his cards on the right time.
 */
public class CardTimer {
	private int robotNbr;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String CARD_TIME_OUT = "cardTimeOut";
	private long seconds;
	private Timer timer;
	private TimerTask timeOut;
	
	/**
	 * Create a new CardTimer.
	 * @param seconds the length of the timer
	 * @param robotID the robot that is supposed to have the timer
	 */
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

	/**
	 * Cancel the task and the timer will not send an event.
	 */
	public void cancelTask() {
		if (timeOut != null) {
			timeOut.cancel();
		}
	}
	
	/**
	 * Set a new task to be able to start a new round. Is called by start().
	 */
	private void reSchedule() {
		timeOut = new TimerTask() {
			@Override
			public void run() {
				pcs.firePropertyChange(CARD_TIME_OUT, -1, robotNbr);
			}
		};
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}