package se.chalmers.dryleafsoftware.androidrally.libgdx;

import se.chalmers.dryleafsoftware.androidrally.IO.IOHandler;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * This is the activity which will simply start the game.
 * 
 * @author
 * 
 */
public class GameActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		// Turn off these to save battery
		cfg.useAccelerometer = false;
		cfg.useCompass = false;

		int ID = getIntent().getIntExtra("GAME_ID", IOHandler.getNewID());
		initialize(new GdxGame(ID, true), cfg);
	}
}