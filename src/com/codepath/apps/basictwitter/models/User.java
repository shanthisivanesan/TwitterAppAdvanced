package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private long uid;
	private String name;
	private String screenName;
	private String imageProfileUrl;
	private String tagLine;
	private int followersCount;
	private int followingCount;

	public long getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getTagline() {
		return tagLine;
	}
	
	public String getProfileImageUrl() {
		return imageProfileUrl;
	}


	public int getFollowersCount() {
		return followersCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}

	public static User fromJson(JSONObject jsonObject) {
		User user = new User();

		try {
			user.uid = jsonObject.getLong("id");
			user.name = jsonObject.getString("name");
			user.screenName = "@" + jsonObject.getString("screen_name");
			user.imageProfileUrl = jsonObject.getString("profile_image_url");
			user.tagLine = jsonObject.getString("description");
			user.followersCount = jsonObject.getInt("followers_count");
			user.followingCount = jsonObject.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return user;
	}


}