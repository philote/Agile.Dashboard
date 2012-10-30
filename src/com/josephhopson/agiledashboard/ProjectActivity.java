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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.josephhopson.agiledashboard.fragments.StoriesListFragment;
import com.josephhopson.agiledashboard.service.R;
import com.josephhopson.analytics.tracking.EasyTracker;

/**
 * ProjectActivity.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 29, 2012
 */
public class ProjectActivity extends BaseActivity implements
		ActionBar.TabListener,
		ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	
	private StoriesListFragment mCurrentListFragment;
	private StoriesListFragment mBacklogListFragment;
	private StoriesListFragment mIceboxListFragment;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        
        EasyTracker.getTracker().setContext(this);
        
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
                titleId = R.string.tab_title_current;
                break;
            case 1:
                titleId = R.string.tab_title_backlog;
                break;
            case 2:
                titleId = R.string.tab_title_icebox;
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
 	// Helper functions
 	// ----------------------
    
	private void loadFragments() {
    	mViewPager = (ViewPager) findViewById(R.id.pager);
    	if (mViewPager != null) {
    		// Phone setup
    		mViewPager.setAdapter(new ProjectPagerAdapter(getSupportFragmentManager()));
            mViewPager.setOnPageChangeListener(this);
    		
    		final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_current)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_backlog)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_icebox)
                    .setTabListener(this));
    	} else {
        	// Tablet setup
    		FragmentManager fm = getSupportFragmentManager();
    		mCurrentListFragment = (StoriesListFragment) fm.findFragmentById(R.id.fragment_current);
    		mBacklogListFragment = (StoriesListFragment) fm.findFragmentById(R.id.fragment_backlog);
    		mIceboxListFragment  = (StoriesListFragment) fm.findFragmentById(R.id.fragment_icebox);
    	}
	}
    
    
    // ----------------------
 	// Pager Adapter Object
 	// ----------------------
	
	/**
     * DashboardPagerAdapter
     * Purpose: 
     * 
     */
    private class ProjectPagerAdapter extends FragmentPagerAdapter {
    	
        public ProjectPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                	 return (mCurrentListFragment = new StoriesListFragment());
                case 1:
                	 return (mBacklogListFragment = new StoriesListFragment());
                case 2:
                	 return (mIceboxListFragment = new StoriesListFragment());
            }
            return null;
        }
        
        @Override
        public int getCount() {
            return 3;
        }
    }
}
