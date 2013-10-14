package se.chalmers.dryleafsoftware.androidrally;

import se.chalmers.dryleafsoftware.androidrally.IO.IOHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This is the main activity, i.g. it will be the activity started when opening
 * the app on the phone.
 * 
 * @author
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Sets where to save.
		SharedPreferences prefs = this.getSharedPreferences(
			      "androidRallyStorage", Context.MODE_PRIVATE);
		IOHandler.setPrefs(prefs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			// TODO: refresh()
			showToaster("Refreshing");
			return true;
		case R.id.action_help:
			// Todo: help someone
			showToaster("Helping");
			return true;
		default:
			return true;
		}
	}
	
	/**
	 * Starts the chosen game
	 * 
	 * @param view
	 */
	public void startChosenGame(View view) {
		ListView lv = (ListView) view.findViewById(R.id.currentGames);
//		lv.get the chosen one
//		configure the correct game to start
//		Intent i = new Intent(getApplicationContext(), GameActivity.class);
//		startActivity(i);
	}

	public void showToaster(CharSequence message) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	

	public void startConfiguration(View view) {
		Intent i = new Intent(getApplicationContext(), GameConfigurationActivity.class);
		startActivity(i);
	}

}
