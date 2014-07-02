package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screenName = getIntent().getStringExtra("screenName");
		loadProfileInfo(screenName);
		FragmentManager manager = getSupportFragmentManager();
    	
    	FragmentTransaction ft = manager.beginTransaction();
    	UserTimelineFragment fragment = new UserTimelineFragment();
    	fragment.setScreenName(screenName);
    	ft.replace(R.id.fragmentUserTimeline, fragment);
    	ft.commit();
	}

	private void loadProfileInfo(String screenName) {
		//Toast.makeText(this, "loadProfileInfo", Toast.LENGTH_SHORT).show();
		TwitterApplication.getRestClient().getProfile(
				new JsonHttpResponseHandler(){
			
				public void onSuccess(JSONObject json) {
					//Toast.makeText(ProfileActivity.this, "success", Toast.LENGTH_SHORT).show();
					User u = User.fromJson(json);
					getActionBar().setTitle(u.getScreenName());
					populateProfileHeader (u);
				}	
				public void onFailure(Throwable err) 
				{
					Log.d("DEBUG", "error in getting profile " + err.getMessage());
				};
			}
					,screenName);
		}
	private void populateProfileHeader(User user)
	{
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvfollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvfollowing);
		ImageView ivImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount()+" Followers");
		tvFollowing.setText(user.getFollowingCount()+" Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivImage);
	}
	
}
