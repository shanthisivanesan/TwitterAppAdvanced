package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import com.codepath.apps.basictwitter.DetailActivity;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import eu.erikw.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public abstract class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list,container,false);
		//assign our view references
		//lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener ()
		{

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				 customLoadMoreDataFromApi(page, totalItemsCount); 
				
			}
			
		});
		

		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long rowid) {
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("tweet",  aTweets.getItem(position));
				startActivity(i);
			}					
				});

		//return the layout view
		return v;
	}
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int page, int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	Log.d("debug", "page =" + page + " offset="+offset);
    	Log.d ("debug", "number of tweets in list = " + aTweets.getCount ());
    	if (aTweets.getCount () == offset)
    	{
    	   	String max_id = String.valueOf(aTweets.getItem(offset-1).getUid());
        	populateTimeline (max_id);	
    	}
    }

    public abstract void populateTimeline (String max_id);
	
	//delegate the adding to the internal adpter
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
}
