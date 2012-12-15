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
package com.josephhopson.agiledashboard.service.stories;

import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;
import com.josephhopson.agiledashboard.service.rest.AgileDashboardServiceRestClient;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * StoriesIntentService.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Nov 06, 2012
 */
public class StoriesIntentService extends IntentService {
	
	public static final String URL 
		= "com.josephhopson.pivotal.tracker.service.stories.StoriesIntentService.url";
	public static final String ID 
		= "com.josephhopson.pivotal.tracker.service.stories.StoriesIntentService.id";
	
	public StoriesIntentService() {
		super("StoriesIntentService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		String url = intent.getStringExtra(URL);
		String id = intent.getStringExtra(ID);
		SharedPreferences mSharedPreferences = getSharedPreferences(AgileDashboardServiceConstants.TOKEN_PREF, MODE_PRIVATE);
        String token = mSharedPreferences.getString(AgileDashboardServiceConstants.TOKEN_PREF_KEY, "");
        
		AgileDashboardServiceRestClient mRestClient = new AgileDashboardServiceRestClient(url, token);
		
		try {
			mRestClient.execute();
			
			StoryProcessor mStoryProcessor = new StoryProcessor(this);
			mStoryProcessor.processStory(mRestClient.getResponse());
			
		} catch (Exception e) {
			Log.e(getClass().getName(), "Exception getting the story(ies)", e);
			StoryHelper.requestFinished(id);
			// TODO broadcast the error
		}
	}

}
