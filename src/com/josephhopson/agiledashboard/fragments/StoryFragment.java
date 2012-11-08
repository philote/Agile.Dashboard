package com.josephhopson.agiledashboard.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.actionbarsherlock.app.SherlockFragment;
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
	
	public static StoryFragment newInstance(String storyId, String projectId) {
		StoryFragment mStoryFragment = new StoryFragment();
		
		Bundle args = new Bundle();
		args.putString(STORY_ID, storyId);
		args.putString(PROJECT_ID, projectId);
		mStoryFragment.setArguments(args);
		
		return mStoryFragment;
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
		return cursorLoaderData;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO refresh view
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO error?
		
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
