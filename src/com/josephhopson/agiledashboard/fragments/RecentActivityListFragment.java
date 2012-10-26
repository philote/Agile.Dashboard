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

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.actionbarsherlock.app.SherlockListFragment;
import com.josephhopson.agiledashboard.adapters.ActivitiesListAdapter;
import com.josephhopson.agiledashboard.service.provider.TrackerServiceContract.RecentActivity;

/**
 * RecentActivityListFragment.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 13, 2012
 */
public class RecentActivityListFragment extends SherlockListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {
	
	ActivitiesListAdapter mActivitiesListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActivitiesListAdapter = new ActivitiesListAdapter(getActivity());
        this.setListAdapter(mActivitiesListAdapter);
        
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // In support library r8, calling initLoader for a fragment 
        // in a FragmentPagerAdapter in the fragment's onCreate may   
        // cause the same LoaderManager to be dealt to multiple 
        // fragments because their mIndex is -1 (haven't been added to 
        // the activity yet). Thus, we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
		CursorLoader cursorLoaderData = new CursorLoader(
				getActivity().getApplicationContext(), 
				RecentActivity.CONTENT_URI, 
				null, null, null, null);
		return cursorLoaderData;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mActivitiesListAdapter.changeCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mActivitiesListAdapter.changeCursor(null);
	}

}
