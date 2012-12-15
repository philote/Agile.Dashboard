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
package com.josephhopson.agiledashboard.service.recentactivity;

import java.util.Stack;

import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

import android.content.Context;
import android.content.Intent;

/**
 * RecentActivityServiceHelper.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 14, 2012
 */
public class RecentActivityHelper {

	private static final String ALL = "com.josephhopson.pivotal.tracker.service.projects.ProjectServiceHelper.all";
	
	private static RecentActivityHelper ourInstance = new RecentActivityHelper();
	public static Stack<String> processingRequestsStack = new Stack<String>();
	
	private RecentActivityHelper() {}
	
	public static void update(Context context) {
		int result = processingRequestsStack.search(ALL);
		if(result == -1) {
    		processingRequestsStack.push(ALL);
    		
    		// TODO Change this to use a URL builder
    		String url = AgileDashboardServiceConstants.TRACKER_SERVICE_URL 
    				+ "/"
    				+ AgileDashboardServiceConstants.ACTIVITES;
    		
    		Intent mIntent = new Intent(context, RecentActivityIntentService.class);
    		mIntent.putExtra(RecentActivityIntentService.URL, url);
    		context.startService(mIntent);
		}
	}
	
	// -------------------------------
  	// Helper functions
  	// -------------------------------
     
    public static RecentActivityHelper getInstance() {
        return ourInstance;
    }
    
    public static void requestFinished(String id) {
     	processingRequestsStack.remove(id);
    }

}
