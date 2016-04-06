package com.se.gamesuite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

public class LevelSelector extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_selector);
		
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		
		/*
		 * Create Tab1 - This is Numbers game
		 * Author: IT 2013
		 * 
		 */
		TabHost.TabSpec numbersSpec = tabHost.newTabSpec("Letters");
		numbersSpec.setContent(R.id.letters_tab);
		numbersSpec.setIndicator("Letter Game");
		tabHost.addTab(numbersSpec);
		
		/*
		 * 
		 * Create Tab2 - This is Letters Game
		 * Author: IT 2013
		 * 
		 */
		
		TabHost.TabSpec lettersSpec = tabHost.newTabSpec("Numbers");
		lettersSpec.setContent(R.id.numbers_tab);
		lettersSpec.setIndicator("Number Game");
		tabHost.addTab(lettersSpec);
		
		/*
		 * Use ImageView as a button
		 */
		ImageView levelNumbers1 = (ImageView) findViewById(R.id.level_numbers_1);
		ImageView levelLetters1 = (ImageView) findViewById(R.id.level_letters_1);
		
		levelNumbers1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent to = new Intent(v.getContext(), NumbersGame.class);
				startActivity(to);
			}
		});
		
		levelLetters1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent to = new Intent(v.getContext(), LettersGame.class);
				startActivity(to);
			}
		});
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
