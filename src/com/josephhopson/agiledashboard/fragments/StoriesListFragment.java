/*
 * Copyright © 2012 Joseph Hopson
 * 
 * This file is part of Agile.Dashboard.
 * 
 * This software is dual-licensed:
 * Agile.Dashboard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Agile.Dashboard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Agile.Dashboard.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Alternatively, you can be released from the requirements of the license 
 * by purchasing a commercial license. Buying such a license is mandatory 
 * as soon as you develop commercial activities involving the Agile.Dashboard 
 * software without disclosing the source code of your own applications.
 * 
 * For more information, please contact Joseph Hopson. at the following
 * address: sales@josephhopson.com
 */
package com.josephhopson.agiledashboard.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.josephhopson.agiledashboard.StoryActivity;
import com.josephhopson.agiledashboard.adapters.StoriesListAdapter;
import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;

/**
 * CurrentListFragment.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 29, 2012
 */
public class StoriesListFragment extends SherlockListFragment 
		implements LoaderManager.LoaderCallbacks<Cursor> {

	public static final String STORIES_TYPE_KEY = "com.josephhopson.agiledashboard.fragments.StoriesListFragment.storiesTypeKey";
	public static final String STORIES_PROJECT_ID_KEY = "com.josephhopson.agiledashboard.fragments.StoriesListFragment.storiesProjectIdKey";
	
	// Story Type
	public static enum StoryType {
		CURRENT("Current"), 
		BACKLOG("Backlog"),
		DONE("Done"),
		ICEBOX("Icebox");
			
		private String text;
			
		StoryType (String text) {
		    this.text = text;
		}
			
		public String getText() {
			return this.text;
		}
			
		public static StoryType fromString(String text) {
			if (text != null) {
				for (StoryType b : StoryType.values()) {
					if (text.equalsIgnoreCase(b.text)) {
						return b;
					}
				}
			}
			return StoryType.CURRENT;
		}
	}
	
	private StoriesListAdapter mStoriesListAdapter;
	
	public static StoriesListFragment newInstance(StoryType storiesType, String projectId) {
		StoriesListFragment mStoriesListFragment = new StoriesListFragment();
		
		Bundle args = new Bundle();
		args.putString(STORIES_TYPE_KEY, storiesType.getText());
		args.putString(STORIES_PROJECT_ID_KEY, projectId);
		mStoriesListFragment.setArguments(args);
		
		return mStoriesListFragment;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mStoriesListAdapter = new StoriesListAdapter(getActivity());
        this.setListAdapter(mStoriesListAdapter);
        
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
	public void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), StoryActivity.class);
		mStoriesListAdapter.getCursor().moveToPosition(position);
		String storyId = mStoriesListAdapter.getCursor().getString(
				mStoriesListAdapter.getCursor().getColumnIndex(Stories.STORY_ID));
		intent.putExtra(StoryActivity.STORY_ID_KEY, storyId);
		String projectId = mStoriesListAdapter.getCursor().getString(
				mStoriesListAdapter.getCursor().getColumnIndex(Stories.STORY_PROJECT_ID));
		intent.putExtra(StoryActivity.STORY_PROJECT_ID_KEY, projectId);
		startActivity(intent);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
		CursorLoader cursorLoaderData = new CursorLoader(
				getActivity().getApplicationContext(), 
				Stories.buildAllStoriesUri(getArguments().getString(STORIES_PROJECT_ID_KEY)), 
				null, null, null, null);
		return cursorLoaderData;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mStoriesListAdapter.changeCursor(data);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mStoriesListAdapter.changeCursor(null);
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
