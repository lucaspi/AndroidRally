package se.chalmers.dryleafsoftware.androidrally;

import se.chalmers.dryleafsoftware.androidrally.IO.IOHandler;
import se.chalmers.dryleafsoftware.androidrally.libgdx.Client;
import se.chalmers.dryleafsoftware.androidrally.libgdx.GameActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * This is the main activity, i.g. it will be the activity started when opening
 * the app on the phone.
 * 
 * @author
 * 
 */
public class MainActivity extends Activity {

	private ListView gameListView;
	private Client client;
	private int[] games;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.client = Client.getInstance();
		// Sets where to save.
		SharedPreferences prefs = this.getSharedPreferences(
				"androidRallyStorage", Context.MODE_PRIVATE);
		IOHandler.setPrefs(prefs);

		gameListView = (ListView) findViewById(R.id.currentGames);
		refreshGamesList();
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
			showToaster("Refreshing");
			refreshGamesList();
			return true;
		case R.id.action_help:
			LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View popupView = layoutInflater.inflate(R.layout.help, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			Button closeHelp = (Button) popupView.findViewById(R.id.closeHelp);
			closeHelp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
				}
			});
			popupWindow.showAtLocation(findViewById(R.id.action_help), Gravity.CENTER, 0, 0);
			return true;
		default:
			return true;
		}
	}

	private void refreshGamesList() {
		games = client.getSavedGames();
		String[] gameNames = new String[games.length];
		for (int i = 0; i < gameNames.length; i++) {
			gameNames[i] = "Game " + games[i];
		}
		ListAdapter gamesList = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				gameNames);
		gameListView.setAdapter(gamesList);
		// Starts the selected game when being tapped
		gameListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				startChosenGame(view, games[position]);
			}

		});
	}

	/**
	 * Starts the chosen game
	 * 
	 * @param view
	 */
	public void startChosenGame(View view, int gameID) {
		Intent i = new Intent(getApplicationContext(), GameActivity.class);
		i.putExtra("GAME_ID", gameID);
		startActivity(i);
	}

	public void showToaster(CharSequence message) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void startConfiguration(View view) {
		Intent i = new Intent(getApplicationContext(),
				GameConfigurationActivity.class);
		startActivity(i);
	}

}
