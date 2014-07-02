package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends Activity{

	private static final int STATUS_MAX_LENGTH = 140;
	private EditText etNewTweet;
	private TextView tvCount;
	TextView tvScreenName;
	TextView tvUserName;
	EditText etCompose;
	ImageView ivProfileImage;
	Button post;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		String screenName = getIntent ().getStringExtra("screenName");
		String userName = getIntent ().getStringExtra("userName");
		setupViews(screenName, userName);
		//setupViews();
		tvCount.setText(String.valueOf(STATUS_MAX_LENGTH));
		tvCount.setTextColor(Color.BLACK);
		tvCount.setTextSize(20);

		etNewTweet.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) {
				int count = STATUS_MAX_LENGTH - s.length();
				tvCount.setText(String.valueOf(count));
				if (count > 10) {
					tvCount.setTextColor(Color.MAGENTA);
				} else if (count > 5){
					tvCount.setTextColor(Color.rgb(240, 190, 40)); // almost close - orange
				} else {
					tvCount.setTextColor(Color.RED);//Exceeded limit - red
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose_menu, menu);
		return true;
	}

	void setupViews(String screenName, String userName) {
		etNewTweet = (EditText) findViewById(R.id.etStatus);
		tvCount = (TextView) findViewById(R.id.tvCharCount);
		post = (Button)findViewById (R.id.post);
		tvScreenName = (TextView)findViewById (R.id.tvComposeScreenName);
		tvUserName = (TextView)findViewById(R.id.tvComposeUserName);
		ivProfileImage = (ImageView)findViewById(R.id.ivComposeProfileImage);
		
		tvScreenName.setText(screenName);
		tvUserName.setText(userName);
	}

	public void onCancel(MenuItem mi) {
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}

	public void onSend(MenuItem mi) {
		Intent i = new Intent();
		i.putExtra("status", etNewTweet.getText().toString());
		setResult(RESULT_OK, i);
		finish();
	}
}
