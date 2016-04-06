package com.se.gamesuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LettersGame extends Activity {

	private final String fileName = "20k.txt";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letters_game);
		//Create a DictBySE object and generate a random 
		// shuffled word
		DictBySE dict = new DictBySE();
		try {
			InputStream input = this.getAssets().open(fileName);
			InputStreamReader iss = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(iss);
			
			dict.addFile(br);
			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		TextView displayShuffledWords = (TextView) findViewById(R.id.display_shuffled_words);
		displayShuffledWords.setText(dict.getShuffledWord());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
