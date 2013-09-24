package se.chalmers.dryleafsoftware.androidrally.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.os.CountDownTimer;

public class CardTimer extends CountDownTimer {
	private int robotID;
	private PropertyChangeSupport pcs;
	
	public CardTimer(long millisInFuture, long countDownInterval, int robotID, PropertyChangeListener pcl) {
		super(millisInFuture, countDownInterval);
		this.robotID = robotID;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(pcl);
	}

	@Override
	public void onFinish() {
		pcs.firePropertyChange("cardTimeOut", -1, robotID);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		//No extension
	}

}
