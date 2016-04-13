package com.se.gamesuite;

import java.util.ArrayList;

import com.myscript.atk.maw.MathWidgetApi;
import com.se.gamesuite.util.SimpleResourceHelper;
import com.myscript.certificate.MyCertificate;
import com.se.gamesuite.view.CustomEditText;
import com.se.gamesuite.FindEquation;

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

public class NumbersGame extends Activity implements 
MathWidgetApi.OnWritingListener {
	public CustomEditText mEditText;
	public Button submit;
	public Button clearButton;
	public FindEquation fe;
	private String TAG = "NumbersGame";
	
	private MathWidgetApi mWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO : Set the XML such that it is applicable for 
		// All mobiles... Now it only applies for my emulator
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numbers_game);
		
		try {
			fe = new FindEquation();
		} catch(Exception e) {
			Log.d(TAG, "");
			e.printStackTrace();
		}
		//Create FindEquation to find the random equation 
		// This also finds the answer and user has to enter + , -, / , * signs
		// Done TODO : Improve it to blank numbers also
		TextView displayNumbers = (TextView) findViewById(R.id.display_numbers);
		submit = (Button) findViewById(R.id.submit_button_numbers);
		clearButton = (Button) findViewById(R.id.clear_numbers);
		
		mWidget = (MathWidgetApi) findViewById(R.id.myscript_maw_numbers);
		ArrayList<Integer> nms = fe.getArguments();
		String toWrite = "Numbers : ";
		int cnt = 0;
		for(Integer x : nms) {
			if(cnt != 0) {
				toWrite += ", ";
			}
			toWrite += x;
			cnt++;
		}
		toWrite += ".\n\nAnswer: " + fe.getAnswer();
		displayNumbers.setText(toWrite);

		mEditText = (CustomEditText) findViewById(R.id.input_by_user_numbers);
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
				if (fe.validateAnswer(xx)) {
				    displayAlertDialogBoxCustom("Well Done! What do you want to do next?", v);
				} else {
					displayAlertDialogBoxCustom("Sorry! " + mEditText.getText() + " doesn't evaluate to " + fe.getAnswer()
							+ ". The correct answer is " + fe.getEquation(), v);
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
							NumbersGame.this.finish();
							startActivity(to);
							
						}
					}).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							NumbersGame.this.finish();
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
