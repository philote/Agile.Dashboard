package com.josephhopson.agiledashboard.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.josephhopson.agiledashboard.R;
import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;

/**
 * StoryFragment.java
 * Purpose:
 * 
 * @author Joseph Hopson
 * @version 1.0 Nov 6, 2012
 */
public class StoryFragment extends SherlockFragment 
		implements LoaderManager.LoaderCallbacks<Cursor> {
	
	public static final String STORY_ID = "com.josephhopson.agiledashboard.fragments.StoryFragment.storyid";
	public static final String PROJECT_ID = "com.josephhopson.agiledashboard.fragments.AttachmentsListFragment.projectid";
	
	private TextView storyname;
	private TextView storytype;
	private TextView storypoints;
	private TextView storystate;
	private TextView storyrequester;
	private TextView storyowner;
	private TextView storydescription;
	private TextView storylabels;
	
	public static StoryFragment newInstance(String projectId, String storyId) {
		StoryFragment mStoryFragment = new StoryFragment();
		
		Bundle args = new Bundle();
		args.putString(STORY_ID, storyId);
		args.putString(PROJECT_ID, projectId);
		mStoryFragment.setArguments(args);
		
		return mStoryFragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.story_detail_fragment, container, false);
		
		storyname = (TextView) view.findViewById(R.id.storyname);
		storytype = (TextView) view.findViewById(R.id.storytype);
		storypoints = (TextView) view.findViewById(R.id.storypoints);
		storystate = (TextView) view.findViewById(R.id.storystate);
		storyrequester = (TextView) view.findViewById(R.id.storyrequester);
		storyowner = (TextView) view.findViewById(R.id.storyowner);
		storydescription = (TextView) view.findViewById(R.id.storydescription);
		storylabels = (TextView) view.findViewById(R.id.storylabels);
		
		return view;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
		// In support library r8, calling initLoader for a fragment 
        // in a FragmentPagerAdapter in the fragment's onCreate may   
        // cause the same LoaderManager to be dealt to multiple 
        // fragments because their mIndex is -1 (haven't been added to 
        // the activity yet). Thus, we do this in onActivityCreated.
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(
        		AgileDashboardServiceConstants.TOKEN_PREF, Context.MODE_PRIVATE);
        String token = mSharedPreferences.getString(AgileDashboardServiceConstants.TOKEN_PREF_KEY, "");
        if(!TextUtils.isEmpty(token)) {
        	getLoaderManager().initLoader(0, null, this);
        } else {
        	// TODO Error
        }
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
		CursorLoader cursorLoaderData = new CursorLoader(
				getActivity().getApplicationContext(), 
				Stories.buildStoryUri(getArguments().getString(PROJECT_ID), getArguments().getString(STORY_ID)), 
				null, null, null, null);
		if(cursorLoaderData.loadInBackground().moveToFirst()) {
			setView(cursorLoaderData.loadInBackground());
		}
		return cursorLoaderData;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		setView(data);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO error?
		
	}
	
	private void setView(Cursor story) {
		if(story.moveToFirst()) {
			storyname.setText(story.getString(story.getColumnIndex(Stories.STORY_NAME)));
			storytype.setText(story.getString(story.getColumnIndex(Stories.STORY_TYPE)));
			storypoints.setText(story.getString(story.getColumnIndex(Stories.STORY_ESTIMATE)));
			storystate.setText(story.getString(story.getColumnIndex(Stories.STORY_CURRENT_STATE)));
			storyrequester.setText(story.getString(story.getColumnIndex(Stories.STORY_REQUESTED_BY)));
			storyowner.setText(story.getString(story.getColumnIndex(Stories.STORY_OWNED_BY)));
			storydescription.setText(story.getString(story.getColumnIndex(Stories.STORY_DESCRIPTION)));
			storylabels.setText(story.getString(story.getColumnIndex(Stories.STORY_LABELS)));
		} else {
			// TODO real error
			Toast.makeText(getActivity(), "No data for this story.", Toast.LENGTH_SHORT).show();
		}
	}

	public void refreshData() {
		SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(AgileDashboardServiceConstants.TOKEN_PREF, Context.MODE_PRIVATE);
        String token = mSharedPreferences.getString(AgileDashboardServiceConstants.TOKEN_PREF_KEY, "");
        if(!TextUtils.isEmpty(token)) {
        	getLoaderManager().initLoader(0, null, this);
        } else {
        	// TODO Error
        }
	}
}
