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
package com.josephhopson.agiledashboard.service.provider;

import com.josephhopson.agiledashboard.service.projects.ProjectHelper;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Attachments;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Iterations;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Memberships;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Projects;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Activities;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Tasks;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceDatabase.Tables;
import com.josephhopson.agiledashboard.service.recentactivity.RecentActivityHelper;
import com.josephhopson.agiledashboard.service.stories.StoryHelper;
import com.josephhopson.agiledashboard.service.tasks.TaskHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * ServiceProvider.java
 * Purpose: 
 * 
 * @author joseph
 * @version 1.0 Oct 8, 2012
 */
public class AgileDashboardServiceProvider extends ContentProvider {
	
	private static final String TAG = 
			"com.josephhopson.pivotal.tracker.service.provider.TrackerServiceProvider";
	
	private AgileDashboardServiceDatabase mOpenHelper;
	
	
	// -------------------------------
	// URI interfaces
	// -------------------------------
	
	private static final int PROJECTS = 200;
	private static final int PROJECTS_ID = 201;
	
	private static final int ACTIVITIES = 100;
	private static final int PROJECTS_ACTIVITIES = 101;
	
	private static final int STORIES = 300;
	private static final int STORIES_ID = 301;
	
	private static final int TASKS = 400;
	private static final int TASKS_ID = 401;
	
	private static final int ITERATIONS = 500;
	private static final int ITERATIONS_ID = 501;
	
	private static final int ATTACHMENTS = 600;
	private static final int ATTACHMENTS_ID = 601;
	
	private static final int MEMBERS = 700;
	private static final int MEMBERS_ID = 701;
	
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	/**
     * Build and return a {@link UriMatcher} that catches all {@link Uri}
     * variations supported by this {@link ContentProvider}.
     */
    private static UriMatcher buildUriMatcher() {
    	final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    	final String authority = AgileDashboardServiceContract.CONTENT_AUTHORITY;
    	
    	matcher.addURI(authority, "projects", PROJECTS);
    	matcher.addURI(authority, "projects/*", PROJECTS_ID);
    	
    	matcher.addURI(authority, "activities", ACTIVITIES);
    	matcher.addURI(authority, "projects/*/activities", PROJECTS_ACTIVITIES);
    	
    	matcher.addURI(authority, "projects/*/stories", STORIES);
    	matcher.addURI(authority, "projects/*/stories/*", STORIES_ID);
    	
    	matcher.addURI(authority, "projects/*/stories/*/tasks", TASKS);
    	matcher.addURI(authority, "projects/*/stories/*/tasks/*", TASKS_ID);
    	
    	matcher.addURI(authority, "iterations", ITERATIONS);
    	matcher.addURI(authority, "iterations/*", ITERATIONS_ID);
    	
    	matcher.addURI(authority, "attachments", ATTACHMENTS);
    	matcher.addURI(authority, "attachments/*", ATTACHMENTS_ID);
    	
    	matcher.addURI(authority, "members", MEMBERS);
    	matcher.addURI(authority, "members/*", MEMBERS_ID);
    	
    	return matcher;
    }
	
    
    // -------------------------------
 	// Content Provider methods
 	// -------------------------------
    
	@Override
	public boolean onCreate() {
		mOpenHelper = new AgileDashboardServiceDatabase(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor queryCursor = null;
		
		switch (sUriMatcher.match(uri)) {
			case ACTIVITIES: {
				Log.d(TAG, "query :: ACTIVITIES");
				
				queryCursor = db.query(Tables.ACTIVITIES, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				RecentActivityHelper.update(getContext());
				
				break;
			}
			case PROJECTS: {
				Log.d(TAG, "query :: PROJECTS");
				
				queryCursor = db.query(Tables.PROJECTS, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				if(queryCursor.getCount() <= 0) {
					ProjectHelper.updateAllProjects(getContext());
				}
				
				break;
			}
			case PROJECTS_ID: {
				Log.d(TAG, "query :: PROJECTS_ID");
				
				selection =  Projects.PROJECT_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Projects.getProjectId(uri);
				queryCursor = db.query(Tables.PROJECTS, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				// TODO check for out of date data
				if(queryCursor.getCount() <= 0) {// || outOfDate(queryCursor)) {
					ProjectHelper.updateProject(getContext(), Projects.getProjectId(uri));
				}
				
				break;
			}
			case STORIES: {
				Log.d(TAG, "query :: STORIES");
				
				selection =  Stories.STORY_PROJECT_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Projects.getProjectId(uri);
				queryCursor = db.query(Tables.STORIES, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				if(queryCursor.getCount() <= 0) {
					StoryHelper.updateAllStories(getContext(), Projects.getProjectId(uri));
				}
				
				break;
			}
			case STORIES_ID: {
				Log.d(TAG, "query :: STORIES_ID");
				
				selection =  Stories.STORY_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Stories.getStoryId(uri);
				queryCursor = db.query(Tables.STORIES, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				// TODO check for out of date data
				if(queryCursor.getCount() <= 0) {// || outOfDate(queryCursor)) {
					StoryHelper.updateStory(getContext(), Projects.getProjectId(uri), Stories.getStoryId(uri));
				}
				
				break;
			}
			case TASKS: {
				Log.d(TAG, "query :: TASKS");
				
				selection =  Tasks.TASKS_STORY_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Stories.getStoryId(uri);
				queryCursor = db.query(Tables.TASKS, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				if(queryCursor.getCount() <= 0) {
					TaskHelper.updateAllTasks(getContext(), 
							Projects.getProjectId(uri), 
							Stories.getStoryId(uri));
				}
				
				break;
			}
			case TASKS_ID: {
				Log.d(TAG, "query :: TASKS_ID");
				
				// project id
				// story id
				// Task id
				
				selection =  Tasks.TASKS_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Tasks.getTaskId(uri);
				queryCursor = db.query(Tables.STORIES, 
						projection, selection, selectionArgs, 
						null, null, sortOrder);
				
				// TODO check for out of date data
				if(queryCursor.getCount() <= 0) {// || outOfDate(queryCursor)) {
					TaskHelper.updateTask(getContext(), 
							Projects.getProjectId(uri), 
							Stories.getStoryId(uri), 
							Tasks.getTaskId(uri));
				}
				
				break;
			}
//			case ITERATIONS: {
//				Log.d(TAG, "query :: ITERATIONS");
//				
//				queryCursor = db.query(Tables.ITERATIONS, 
//						projection, selection, selectionArgs, 
//						null, null, sortOrder);
//				
//				if(queryCursor.getCount() <= 0) {
//					IterationsHelper.updateAllIterations(getContext());
//				}
//				
//				break;
//			}
			default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
		}
		
		queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return queryCursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		if (values == null) {
        	throw new IllegalArgumentException("No content given to insert");
        }
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
			case ACTIVITIES: {
				Log.d(TAG, "insert :: RECENT_ACTIVITY");
				
				String selection =  Activities.ACTIVITY_ID + " = ?";
				String[] selectionArgs = {values.getAsString(Activities.ACTIVITY_ID)};
				
				// Check if the activity is in the DB, if it is; do an update instead
				Cursor cursor = db.query(Tables.ACTIVITIES, null, 
						selection, selectionArgs, null, null, null);
				if(cursor.moveToFirst()) {
					cursor.close();
					update(uri, values, selection, selectionArgs);
					return null;
				} else {
					cursor.close();
					db.insertOrThrow(Tables.ACTIVITIES, null, values);
					getContext().getContentResolver().notifyChange(uri, null);
					return Activities.buildActivityUri(values.getAsString(Activities.ACTIVITY_ID));
				}
			}
			case PROJECTS: {
				Log.d(TAG, "insert :: PROJECTS");
				
				String selection =  Projects.PROJECT_ID + " = ?";
				String[] selectionArgs = {values.getAsString(Projects.PROJECT_ID)};
				
				// Check if the project is in the DB, if it is; do an update instead
				Cursor cursor = db.query(Tables.PROJECTS, null, 
						selection, selectionArgs, null, null, null);
				if(cursor.moveToFirst()) {
					cursor.close();
					update(uri, values, selection, selectionArgs);
					return null;
				} else {
					cursor.close();
					db.insertOrThrow(Tables.PROJECTS, null, values);
					getContext().getContentResolver().notifyChange(uri, null);
					return Projects.buildProjectUri(values.getAsString(Projects.PROJECT_ID));
				}
			}
			case STORIES: {
				Log.d(TAG, "insert :: STORIES");
				
				String selection =  Stories.STORY_ID + " = ?";
				String[] selectionArgs = {values.getAsString(Stories.STORY_ID)};
				
				// Check if the project is in the DB, if it is; do an update instead
				Cursor cursor = db.query(Tables.STORIES, null, 
						selection, selectionArgs, null, null, null);
				if(cursor.moveToFirst()) {
					cursor.close();
					update(uri, values, selection, selectionArgs);
					return null;
				} else {
					cursor.close();
					db.insertOrThrow(Tables.STORIES, null, values);
					getContext().getContentResolver().notifyChange(uri, null);
					return Stories.buildStoryUri(
							values.getAsString(Stories.STORY_PROJECT_ID), 
							values.getAsString(Stories.STORY_ID));
				}
			}
			case TASKS: {
				Log.d(TAG, "insert :: TASKS");
				
				String selection =  Tasks.TASKS_ID + " = ?";
				String[] selectionArgs = {values.getAsString(Tasks.TASKS_ID)};
				
				// Check if the project is in the DB, if it is; do an update instead
				Cursor cursor = db.query(Tables.TASKS, null, 
						selection, selectionArgs, null, null, null);
				if(cursor.moveToFirst()) {
					cursor.close();
					update(uri, values, selection, selectionArgs);
					return null;
				} else {
					cursor.close();
					db.insertOrThrow(Tables.TASKS, null, values);
					getContext().getContentResolver().notifyChange(uri, null);
					return Tasks.buildTaskUri(
							Projects.getProjectId(uri), 
							values.getAsString(Tasks.TASKS_STORY_ID), 
							values.getAsString(Tasks.TASKS_ID));
				}
			}
//			case ITERATIONS: {
//				Log.d(TAG, "insert :: ITERATIONS");
//				
//				String selection =  Iterations.ITERATION_ID + " = ?";
//				String[] selectionArgs = {values.getAsString(Iterations.ITERATION_ID)};
//				
//				// Check if the project is in the DB, if it is; do an update instead
//				Cursor cursor = db.query(Tables.ITERATIONS, null, 
//						selection, selectionArgs, null, null, null);
//				if(cursor.moveToFirst()) {
//					cursor.close();
//					update(uri, values, selection, selectionArgs);
//					return null;
//				} else {
//					cursor.close();
//					db.insertOrThrow(Tables.ITERATIONS, null, values);
//					getContext().getContentResolver().notifyChange(uri, null);
//					return Iterations.builditerationUri(values.getAsString(Iterations.ITERATION_ID));
//				}
//			}
			default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
		}
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		if (values == null) {
        	throw new IllegalArgumentException("No content given to insert");
        }
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
			case ACTIVITIES: {
				Log.d(TAG, "update :: RECENT_ACTIVITY");
				int retVal = db.update(Tables.ACTIVITIES, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case PROJECTS: {
				Log.d(TAG, "update :: PROJECTS");
				int retVal = db.update(Tables.PROJECTS, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case STORIES: {
				Log.d(TAG, "update :: STORIES");
				int retVal = db.update(Tables.STORIES, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case TASKS: {
				Log.d(TAG, "update :: TASKS");
				int retVal = db.update(Tables.TASKS, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
//			case ITERATIONS: {
//				Log.d(TAG, "update :: ITERATIONS");
//				int retVal = db.update(Tables.ITERATIONS, values, selection, selectionArgs);
//				getContext().getContentResolver().notifyChange(uri, null);
//				return retVal;
//			}
			default: {
	            throw new UnsupportedOperationException("Unknown uri: " + uri);
	        }
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		// TODO handle whole database deletes when signing out
		/*
		 if (uri == ScheduleContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            getContext().getContentResolver().notifyChange(uri, null, false);
            return 1;
         }
		 */
		
		// TODO these need a lot of work
		switch (sUriMatcher.match(uri)) {
			case ACTIVITIES: {
				Log.d(TAG, "delete :: ACTIVITIES");
				int retVal = db.delete(Tables.ACTIVITIES, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case PROJECTS: {
				Log.d(TAG, "delete :: PROJECTS");
				
				selection =  Projects.PROJECT_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Projects.getProjectId(uri);
				
				int retVal = db.delete(Tables.PROJECTS, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case STORIES_ID: {
				Log.d(TAG, "delete :: STORIES_ID");
				
				selection =  Stories.STORY_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Stories.getStoryId(uri);
				
				int retVal = db.delete(Tables.STORIES, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
			case TASKS_ID: {
				Log.d(TAG, "delete :: TASKS_ID");
				
				selection =  Tasks.TASKS_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = Tasks.getTaskId(uri);
				
				int retVal = db.delete(Tables.TASKS, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				return retVal;
			}
//			case ITERATIONS: {
//				Log.d(TAG, "delete :: ITERATIONS");
//				
//				selection =  Iterations.ITERATION_ID + " = ?";
//				selectionArgs = new String[1];
//				selectionArgs[0] = Iterations.getIterationId(uri);
//				
//				int retVal = db.delete(Tables.ITERATIONS, selection, selectionArgs);
//				getContext().getContentResolver().notifyChange(uri, null);
//				return retVal;
//			}
			default: {
	            throw new UnsupportedOperationException("Unknown uri: " + uri);
	        }
		}
	}
	
	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case PROJECTS:
				return Projects.CONTENT_TYPE;
			case PROJECTS_ID:
				return Projects.CONTENT_ITEM_TYPE;
			case ACTIVITIES:
				return Activities.CONTENT_TYPE;
			case PROJECTS_ACTIVITIES:
				return Activities.CONTENT_TYPE;
			case STORIES:
				return Stories.CONTENT_TYPE;
			case STORIES_ID:
				return Stories.CONTENT_ITEM_TYPE;
			case TASKS:
				return Tasks.CONTENT_TYPE;
			case TASKS_ID:
				return Tasks.CONTENT_ITEM_TYPE;
			case ITERATIONS:
				return Iterations.CONTENT_TYPE;
			case ITERATIONS_ID:
				return Iterations.CONTENT_ITEM_TYPE;
			case ATTACHMENTS:
				return Attachments.CONTENT_TYPE;
			case ATTACHMENTS_ID:
				return Attachments.CONTENT_ITEM_TYPE;
			case MEMBERS:
				return Memberships.CONTENT_TYPE;
			case MEMBERS_ID:
				return Memberships.CONTENT_ITEM_TYPE;
			default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}
}
