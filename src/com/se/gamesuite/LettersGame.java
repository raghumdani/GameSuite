package com.se.gamesuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.myscript.atk.maw.MathWidgetApi;
import com.se.gamesuite.util.SimpleResourceHelper;
import com.se.gamesuite.MyCertificate;
import com.se.gamesuite.view.CustomEditText;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LettersGame extends Activity implements 
MathWidgetApi.OnWritingListener {
	public CustomEditText mEditText;
	public DictBySE dict;
	public Button submit;
	public Button clearButton;
	private String TAG = "LetterGame";
	
	// Myscript API
	private MathWidgetApi mWidget;
	private final String fileName = "20k.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO : Set the XML such that it is applicable for 
		// All mobiles... Now it only applies for my emulator
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letters_game);
		// Create a DictBySE object and generate a random
		// shuffled word
		dict = new DictBySE();
		try {
			InputStream input = this.getAssets().open(fileName);
			InputStreamReader iss = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(iss);

			dict.addFile(br);

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TextView displayShuffledWords = (TextView) findViewById(R.id.display_shuffled_words);
		displayShuffledWords.setText(dict.getShuffledWord());

		mEditText = (CustomEditText) findViewById(R.id.input_by_user);
		mEditText.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					submit.performClick();
					return true;
				}
				return false;
			}
		});

		submit = (Button) findViewById(R.id.submit_button);
		clearButton = (Button) findViewById(R.id.clear);
		
		mWidget = (MathWidgetApi) findViewById(R.id.myscript_maw);
		mWidget.setOnWritingListener(this);
		
		if(clearButton != null) {
			clearButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mWidget.clear(true);					
				}
			});
		}
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String xx = mEditText.getText().toString();
				if (dict.validateString(xx)) {
				    displayAlertDialogBoxCustom("Well Done! What do you want to do next?", v);
				} else {
					displayAlertDialogBoxCustom("Sorry! " + mEditText.getText() + " is not a meaningful word. "
							+ "The correct answer is " + dict.getAnswer(), v);
				}
			}

			private void displayAlertDialogBoxCustom(String msg, View v) {
			    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			    builder.setMessage(msg)
			           .setCancelable(false)
			           .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							Intent to = getIntent();
							LettersGame.this.finish();
							startActivity(to);
							
						}
					}).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							LettersGame.this.finish();
						}
					});
			    AlertDialog alert = builder.create();
			    alert.show();
			}
		});
		configure();
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

	  private void configure()
	  {
	    // Equation resource    
	    final String[] resources = new String[]{"math-ak.res", "math-grm-maw.res"};

	    // Prepare resources
	    final String subfolder = "math";
	    final String resourcePath = new String(getFilesDir().getPath() + java.io.File.separator + subfolder);
	    SimpleResourceHelper
	        .copyResourcesFromAssets(getAssets(), subfolder /* from */, resourcePath /* to */, resources /* resource names */);

	    // Configure math widget
	    mWidget.setResourcesPath(resourcePath);
	    mWidget.configure(this, resources, MyCertificate.getBytes(), MathWidgetApi.AdditionalGestures.DefaultGestures);
	  }

	@Override
	public void onWritingBegin() {
		Log.d(TAG, "onWritingBegin Executed.");
		// TODO : Do something da
	}

	@Override
	public void onWritingEnd() {
		Log.d(TAG, "onWritingEnd Executed.");
		String s = mWidget.getResultAsText();
		if(s.length() > 1) {
			mEditText.append("" + s.charAt(s.length() - 1));
		} else {
			mEditText.append(s);
		}
	}
}
