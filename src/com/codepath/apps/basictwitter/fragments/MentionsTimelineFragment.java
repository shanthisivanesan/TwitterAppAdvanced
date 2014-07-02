package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import android.os.Bundle;
import android.util.Log;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline("");
	}
	
	public void populateTimeline(String max_id) {
		client.getMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray tweets) {
				addAll(Tweet.fromJSONArray(tweets));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}

		}
		,max_id);
	}

}
