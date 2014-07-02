package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	 public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	    public static final String REST_CONSUMER_KEY = "UyLZ5CAF6ct2TQ7hNwvyZZnCc";       // Change this
	    public static final String REST_CONSUMER_SECRET = "0c49GpQeAZkvdpEjHQ3oXTrD5M6BQyknNeSMyseNNY1d4mgHTI"; // Change this
	    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
       public void getHomeTimeline(AsyncHttpResponseHandler handler, String max_id) {
        String apiUrl = getApiUrl ("statuses/home_timeline.json");
        RequestParams params = new RequestParams ();
        
        if (max_id != "")
        {
            params.put("max_id", max_id);
        }
        
        params.put("since", "1");
        //params.put("count", "25");
        client.get(apiUrl, params, handler);
    }
    
    public void getProfile(AsyncHttpResponseHandler handler, String screenName) {
        String apiUrl = null;
        RequestParams params = null;
        if (screenName == null)
        {
            apiUrl = getApiUrl ("account/verify_credentials.json");
        }
        else
        {
            apiUrl = getApiUrl ("users/show.json");
            params = new RequestParams();
            params.put("screen_name", screenName);
        }
 
 
        client.get(apiUrl, params, handler);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, String uid) {
        String apiUrl = getApiUrl ("statuses/user_timeline.json");
        RequestParams params = null;
        
        if (uid != null)
        {
            params = new RequestParams ();
            params.put("screen_name", uid.toString());
        }
  
        client.get(apiUrl, params, handler);
    }
    
    public void postTweet(AsyncHttpResponseHandler handler, String msg)
    {
        String apiUrl = getApiUrl ("statuses/update.json");
        RequestParams params = new RequestParams ();
        params.put ("status", msg);
        client.post(apiUrl, params, handler);
    }

    public void getMentionsTimeline(
            AsyncHttpResponseHandler handler, String max_id) {
        String apiUrl = getApiUrl ("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams ();
        
        if (max_id != "")
        {
            params.put("max_id", max_id);
        }
        
        params.put("since", "1");
        client.get(apiUrl, params, handler);
        
    }
}