package com.codepath.apps.basictwitter;


import org.json.JSONObject;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
	ImageView ivProfileImage;
	Button post;
	TwitterClient client;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		String screenName = getIntent ().getStringExtra("screenName");
		String userName = getIntent ().getStringExtra("userName");
		setupViews(screenName, userName);
		//setupViews();
		client = TwitterApplication.getRestClient();
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
		tvScreenName = (TextView)findViewById (R.id.tvScreenName);
		tvUserName = (TextView)findViewById(R.id.tvUserName);
		ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		
		tvScreenName.setText(screenName);
		tvUserName.setText(userName);
	}

	public void onCancel(MenuItem mi) {
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}

	public void onSend(MenuItem mi) {
		String tweet = etNewTweet.getText().toString();
		//Toast.makeText(this, etNewTweet.getText(), Toast.LENGTH_SHORT).show();

			//client.postTweet(tweet);
			//finish();
		Intent i = new Intent();
		i.putExtra("status", tweet);
		setResult(RESULT_OK, i);
		
		client.postTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject jsonObject) {
				Tweet newTweet = Tweet.fromJSON(jsonObject);
				Intent i = new Intent();
				//i.putExtra("status", tweet);
				//setResult(RESULT_OK, i);
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s.toString());
			}
		}, tweet);
		
	}
	
	
}
