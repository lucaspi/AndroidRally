package se.chalmers.dryleafsoftware.androidrally;

import se.chalmers.dryleafsoftware.androidrally.libgdx.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GameConfigurationActivity extends Activity {

	private TextView roundTimeText, cardTimeText;
	private SeekBar roundTimeBar, cardTimeBar;
	private BarListener barListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_configuration);
		
		//Initialize time configurators
		roundTimeBar = (SeekBar)  findViewById(R.id.roundTimeBar);
		cardTimeBar = (SeekBar) findViewById(R.id.cardTimeBar);
		roundTimeText = (TextView) findViewById(R.id.roundTimeTextView);
		cardTimeText = (TextView) findViewById(R.id.cardTimeTextView);
		barListener = new BarListener();
		roundTimeBar.setOnSeekBarChangeListener(barListener);
		roundTimeBar.setMax(23); // 1-24 hours with 1 hour steps
		roundTimeBar.setProgress(11); //Set to default 12 (11th step)
		cardTimeBar.setOnSeekBarChangeListener(barListener);
		cardTimeBar.setMax(11); //15-180 seconds with 15 second steps
		cardTimeBar.setProgress(2); // Set to default 45 seconds (3rd step)
	}

	/**
	 * Starts the game.
	 * 
	 * @param view
	 */
	public void startGame(View view) {
		Intent i = new Intent(getApplicationContext(), GameActivity.class);
		startActivity(i);
	}

	private class BarListener implements OnSeekBarChangeListener {
		
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				progress += 1;
				if (seekBar.equals(roundTimeBar)) {
					roundTimeText.setText("" + progress + " hours");
				} else if (seekBar.equals(cardTimeBar)) {
					progress *= 15;
					cardTimeText.setText("" + progress + " seconds");
				}
			}
		}

		//Unused methods
		public void onStartTrackingTouch(SeekBar arg0) {}
		public void onStopTrackingTouch(SeekBar arg0) {}
	}

}
