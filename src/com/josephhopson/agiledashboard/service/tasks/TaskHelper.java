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
package com.josephhopson.agiledashboard.service.tasks;

import java.util.Stack;

import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

import android.content.Context;
import android.content.Intent;

/**
 * StoryHelper.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Nov 06, 2012
 */
public class TaskHelper {
	
	public static final String ALL = "com.josephhopson.pivotal.tracker.service.tasks.TasksHelper.all";
	
	private static TaskHelper ourInstance = new TaskHelper();
	private static Stack<String> processingRequestsStack = new Stack<String>();
	
	private TaskHelper() {}
	
	public static void updateTask(Context context, String projectId, String storyId, String taskId) {
		int result = processingRequestsStack.search(taskId);
		if(result == -1) {
    		processingRequestsStack.push(taskId);
    		
    		// TODO Change this to use a URL builder
    		String url = AgileDashboardServiceConstants.TRACKER_SERVICE_URL 
    				+ "/"
    				+ AgileDashboardServiceConstants.PROJECTS
    				+ "/"
    				+ projectId
    				+ "/"
    				+ AgileDashboardServiceConstants.STORIES
    				+ "/"
    				+ storyId
    				+ "/"
    				+ AgileDashboardServiceConstants.TASKS
    				+ "/"
    				+ taskId;
    		
    		
    		Intent mTasksIntentService = new Intent(context, TasksIntentService.class);
    		mTasksIntentService.putExtra(TasksIntentService.URL, url);
    		mTasksIntentService.putExtra(TasksIntentService.ID, taskId);
    		mTasksIntentService.putExtra(TasksIntentService.STORY_ID, storyId);
    		context.startService(mTasksIntentService);
		}
	}
	
	public static void updateAllTasks(Context context, String projectId, String storyId) {
		int result = processingRequestsStack.search(ALL);
		if(result == -1) {
    		processingRequestsStack.push(ALL);
    		
    		// TODO Change this to use a URL builder
    		String url = AgileDashboardServiceConstants.TRACKER_SERVICE_URL 
    				+ "/"
    				+ AgileDashboardServiceConstants.PROJECTS
    				+ "/"
    				+ projectId
    				+ "/"
    				+ AgileDashboardServiceConstants.STORIES
    				+ "/"
    				+ storyId
    				+ "/"
    				+ AgileDashboardServiceConstants.TASKS;
    		
    		Intent mTasksIntentService = new Intent(context, TasksIntentService.class);
    		mTasksIntentService.putExtra(TasksIntentService.URL, url);
    		mTasksIntentService.putExtra(TasksIntentService.ID, ALL);
    		mTasksIntentService.putExtra(TasksIntentService.STORY_ID, storyId);
    		context.startService(mTasksIntentService);
		}
	}
	
	// -------------------------------
  	// Helper functions
  	// -------------------------------
     
    public static TaskHelper getInstance() {
        return ourInstance;
    }
    
    public static void requestFinished(String id) {
     	processingRequestsStack.remove(id);
    }
}
