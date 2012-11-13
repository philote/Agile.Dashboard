package com.josephhopson.agiledashboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.josephhopson.agiledashboard.fragments.AttachmentsListFragment;
import com.josephhopson.agiledashboard.fragments.StoryFragment;
import com.josephhopson.agiledashboard.fragments.TasksListFragment;
import com.josephhopson.agiledashboard.service.R;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;
import com.josephhopson.analytics.tracking.EasyTracker;

/**
 * StoryActivity.java
 * Purpose:
 * 
 * @author Joseph Hopson
 * @version 1.0 Nov 6, 2012
 */
public class StoryActivity extends BaseActivity implements
		ActionBar.TabListener,
		ViewPager.OnPageChangeListener {
	
	public static final String STORY_ID_KEY = "com.josephhopson.agiledashboard.ProjectActivity.storyidkey";
	public static final String STORY_PROJECT_ID_KEY = "com.josephhopson.agiledashboard.ProjectActivity.storyprojectidkey";
	
	private ViewPager mViewPager;
	
	private StoryFragment mStoryFragment;
	private TasksListFragment mTasksListFragment;
	private AttachmentsListFragment mAttachmentsListFragment;
	
	private String storyId;
	private String projectId;
	private String storyName;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        
    	storyId = getIntent().getExtras().getString(STORY_ID_KEY);
        projectId = getIntent().getExtras().getString(STORY_PROJECT_ID_KEY);
        
        Cursor mCursor = getContentResolver().query(Stories.buildStoryUri(projectId, storyId), null, null, null, null);
        if(mCursor.moveToFirst()) {
        	storyName = mCursor.getString(mCursor.getColumnIndex(Stories.STORY_NAME));
        } else {
        	storyName = "Unknown";
        }
        mCursor.close();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(storyName);
        
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
    	Intent intent;
        switch (item.getItemId()) {
        	// TODO add menu case's here
        	case android.R.id.home:
        		intent = new Intent(this, ProjectActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent.putExtra(ProjectActivity.PROJECT_ID_KEY, projectId);
	        	startActivity(intent);
	        	finish();
	        	return true;
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
                titleId = R.string.tab_title_story;
                break;
            case 1:
                titleId = R.string.tab_title_tasks;
                break;
            case 2:
                titleId = R.string.tab_title_attachments;
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
    		mViewPager.setAdapter(new StoryPagerAdapter(getSupportFragmentManager()));
            mViewPager.setOnPageChangeListener(this);
    		
    		final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_story)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_tasks)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.tab_title_attachments)
                    .setTabListener(this));
    	} else {
        	// Tablet setup
    		FragmentManager fm = getSupportFragmentManager();
    		FragmentTransaction ft = fm.beginTransaction();
    		mStoryFragment = StoryFragment.newInstance(projectId, storyId);
    		ft.replace(R.id.fragment_story, mStoryFragment);
    		mTasksListFragment = TasksListFragment.newInstance(projectId, storyId);
    		ft.replace(R.id.fragment_tasks, mTasksListFragment);
    		mAttachmentsListFragment = AttachmentsListFragment.newInstance(projectId, storyId);
    		ft.replace(R.id.fragment_attachments, mAttachmentsListFragment);
    		ft.commit();
    	}
	}
	
	
    // ----------------------
 	// Pager Adapter Object
 	// ----------------------
	
	/**
     * StoryPagerAdapter
     * Purpose: 
     * 
     */
    private class StoryPagerAdapter extends FragmentPagerAdapter {
    	
        public StoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                	 return (mStoryFragment = StoryFragment.newInstance(projectId, storyId));
                case 1:
                	 return (mTasksListFragment = TasksListFragment.newInstance(projectId, storyId));
                case 2:
                	 return (mAttachmentsListFragment = AttachmentsListFragment.newInstance(projectId, storyId));
            }
            return null;
        }
        
        @Override
        public int getCount() {
            return 3;
        }
    }
}
