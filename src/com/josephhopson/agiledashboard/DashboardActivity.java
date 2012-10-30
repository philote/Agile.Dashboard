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
package com.josephhopson.agiledashboard;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.josephhopson.agiledashboard.fragments.ProjectsListFragment;
import com.josephhopson.agiledashboard.fragments.RecentActivityListFragment;
import com.josephhopson.agiledashboard.fragments.SignInDialogFragment;
import com.josephhopson.agiledashboard.interfaces.OnAuthTokenReceivedListener;
import com.josephhopson.analytics.tracking.EasyTracker;
import com.josephhopson.agiledashboard.service.R;
import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * DashboardActivity.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 13, 2012
 */
public class DashboardActivity extends BaseActivity implements
		ActionBar.TabListener,
		ViewPager.OnPageChangeListener, 
		OnAuthTokenReceivedListener {
	
	private RecentActivityListFragment mRecentActivityListFragment;
	private ProjectsListFragment mProjectsListFragment;
	
	private ViewPager mViewPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        EasyTracker.getTracker().setContext(this);
        
        SharedPreferences mSharedPreferences = getSharedPreferences(AgileDashboardServiceConstants.TOKEN_PREF, MODE_PRIVATE);
        String token = mSharedPreferences.getString(AgileDashboardServiceConstants.TOKEN_PREF_KEY, "");
        if(TextUtils.isEmpty(token)) {
        	Log.d(getClass().getName(), "No Token in shared prefs");
        	showSignInDialog();
        }
        loadFragments();
    }

    
    // ----------------------
 	// Menu functions
 	// ----------------------
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // TODO add menu layout here
        // getSupportMenuInflater().inflate(R.menu.home, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	// TODO add menu case's here
//		    case R.id.menu_refresh:
//		        triggerRefresh();
//		        return true;
//		    case R.id.menu_sign_out:
//		        AccountUtils.signOut(this);
//		        finish();
//		        return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    // ----------------------
 	// Pager functions
 	// ----------------------
    
    @Override
    public void onPageSelected(int position) {
        getSupportActionBar().setSelectedNavigationItem(position);

        int titleId = -1;
        switch (position) {
            case 0:
                titleId = R.string.title_projects_list;
                break;
            case 1:
                titleId = R.string.title_activity_feed;
                break;
        }
        
        EasyTracker.getTracker().trackView(getString(titleId));
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

	@Override
    public void onPageScrolled(int i, float v, int i1) {}

	@Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

	@Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
    
    
    // ----------------------
 	// Authentication functions
 	// ----------------------
    
    @Override
	public void authTokenReceived(String errorMessage) {
    	if(!TextUtils.isEmpty(errorMessage)) {
    		Log.d(getClass().getName(), errorMessage);
    		Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
    		// TODO pop dialog
    	} else {
    		// TODO update fragments
    		mProjectsListFragment.refreshData();
    		mRecentActivityListFragment.refreshData();
    	}
	}
    
    
    // ----------------------
 	// Helper functions
 	// ----------------------
    
    private void loadFragments() {
    	mViewPager = (ViewPager) findViewById(R.id.pager);
        if (mViewPager != null) {
        	// Phone setup
        	mViewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager()));
            mViewPager.setOnPageChangeListener(this);
            
            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.title_projects_list)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.title_activity_feed)
                    .setTabListener(this));
        } else {
        	// Tablet setup
        	FragmentManager fm = getSupportFragmentManager();
        	mRecentActivityListFragment = (RecentActivityListFragment) 
        			fm.findFragmentById(R.id.fragment_recent_activites);
        	mProjectsListFragment = (ProjectsListFragment) 
        			fm.findFragmentById(R.id.fragment_projects);
        }
    }
    
    private void showSignInDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SignInDialogFragment mLoginDialogFragment = new SignInDialogFragment();
        mLoginDialogFragment.show(fm, "fragment_sign_in");
    }
    
    
    // ----------------------
 	// Pager Adapter Object
 	// ----------------------
    
    /**
     * DashboardPagerAdapter
     * Purpose: 
     * 
     */
    private class DashboardPagerAdapter extends FragmentPagerAdapter {
    	
        public DashboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                	return (mProjectsListFragment = new ProjectsListFragment());
                case 1:
                	return (mRecentActivityListFragment = new RecentActivityListFragment());
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
