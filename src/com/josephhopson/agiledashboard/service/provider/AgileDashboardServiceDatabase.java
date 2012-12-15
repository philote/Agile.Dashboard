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

import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.AttachmentsColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Iterations;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.IterationsColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.MembershipColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Projects;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.ProjectsColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.ActivitiesColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.StoriesColumns;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.TasksColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * ServiceDatabaseHelper.java
 * Purpose: 
 * 
 * @author joseph
 * @version 1.0 Oct 8, 2012
 */
public class AgileDashboardServiceDatabase extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "tracker.db";
	private static final int DATABASE_VERSION = 1;
	
	interface Tables {
		String ACTIVITIES = "activities";
		String PROJECTS = "projects";
		String STORIES = "stories";
		String TASKS = "tasks";
		String ATTACHMENTS = "attachments";
		String ITERATIONS = "iterations";
		String ITERATIONS_STORIES_PROJECTS = "iterations_stories_projects";
		String MEMBERSHIPS = "memberships";
	}
	
	public interface IterationStoriesProjects {
		String ITERATION_ID = "iteration_id";
		String STORY_ID = "story_id";
		String PROJECT_ID = "project_id";
	}
	
	/** {@code REFERENCES} clauses. */
    private interface References {
    	String ITERATION_ID = "REFERENCES " + Tables.ITERATIONS + "(" + Iterations.ITERATION_ID + ")";
		String STORY_ID = "REFERENCES " + Tables.STORIES + "(" + Stories.STORY_ID+ ")";
		String PROJECT_ID = "REFERENCES " + Tables.PROJECTS + "(" + Projects.PROJECT_ID + ")";
    }
	
    private final String CREATE_TABLE_ITERATIONS_STORIES_PROJECTS = "CREATE TABLE " 
			+ Tables.ITERATIONS_STORIES_PROJECTS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + IterationStoriesProjects.ITERATION_ID + " TEXT NOT NULL " + References.ITERATION_ID + ", "
            + IterationStoriesProjects.STORY_ID + " TEXT NOT NULL " + References.STORY_ID + ", "
            + IterationStoriesProjects.PROJECT_ID + " TEXT NOT NULL " + References.PROJECT_ID + ","
            + "UNIQUE (" + IterationStoriesProjects.ITERATION_ID + ","
            		+ IterationStoriesProjects.STORY_ID + ","
                    + IterationStoriesProjects.PROJECT_ID + ") ON CONFLICT REPLACE);";
    
    private final String CREATE_TABLE_RECENT_ACTIVITY = "CREATE TABLE " 
			+ Tables.ACTIVITIES + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ActivitiesColumns.ACTIVITY_VERSION + " TEXT, "
            + ActivitiesColumns.ACTIVITY_EVENT_TYPE + " TEXT, "
            + ActivitiesColumns.ACTIVITY_OCCURRED_AT + " TEXT, "
            + ActivitiesColumns.ACTIVITY_AUTHOR + " TEXT, "
            + ActivitiesColumns.ACTIVITY_PROJECT_ID + " TEXT, "
            + ActivitiesColumns.ACTIVITY_DESCRIPTION + " TEXT, "
            + ActivitiesColumns.ACTIVITY_ID + " TEXT NOT NULL, "
            + "UNIQUE (" + ActivitiesColumns.ACTIVITY_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_PROJECTS = "CREATE TABLE " 
			+ Tables.PROJECTS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProjectsColumns.PROJECT_NAME + " TEXT, "
            + ProjectsColumns.PROJECT_ITERATION_LENGTH + " TEXT, "
            + ProjectsColumns.PROJECT_WEEK_START_DAY + " TEXT, "
            + ProjectsColumns.PROJECT_POINT_SCALE + " TEXT, "
            + ProjectsColumns.PROJECT_ACCOUNT + " TEXT, "
            + ProjectsColumns.PROJECT_VELOCITY_SCHEME + " TEXT, "
            + ProjectsColumns.PROJECT_CURRENT_VELOCITY + " TEXT, "
            + ProjectsColumns.PROJECT_INITIAL_VELOCITY + " TEXT, "
            + ProjectsColumns.PROJECT_NUMBER_OF_DONE_ITERATIONS_TO_SHOW + " TEXT, "
            + ProjectsColumns.PROJECT_LABELS + " TEXT, "
            + ProjectsColumns.PROJECT_ALLOW_ATTACHMENTS + " TEXT, "
            + ProjectsColumns.PROJECT_PUBLIC + " TEXT, "
            + ProjectsColumns.PROJECT_USE_HTTPS + " TEXT, "
            + ProjectsColumns.PROJECT_BUGS_CHORES_ARE_ESTIMATABLE + " TEXT, "
            + ProjectsColumns.PROJECT_COMMIT_MODE + " TEXT, "
            + ProjectsColumns.PROJECT_LAST_ACTIVITY_AT + " TEXT, "
            + ProjectsColumns.PROJECT_ID + " TEXT NOT NULL, "
			+ "UNIQUE (" + ProjectsColumns.PROJECT_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_STORIES = "CREATE TABLE " 
			+ Tables.STORIES + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StoriesColumns.STORY_ID + " TEXT NOT NULL, "
            + StoriesColumns.STORY_PROJECT_ID + " TEXT, "
            + StoriesColumns.STORY_NAME + " TEXT, "
            + StoriesColumns.STORY_TYPE + " TEXT, "
            + StoriesColumns.STORY_URL + " TEXT, "
            + StoriesColumns.STORY_ESTIMATE + " TEXT, "
            + StoriesColumns.STORY_CURRENT_STATE + " TEXT, "
            + StoriesColumns.STORY_LIGHTHOUSE_ID + " TEXT, "
            + StoriesColumns.STORY_LIGHTHOUSE_URL + " TEXT, "
            + StoriesColumns.STORY_DESCRIPTION + " TEXT, "
            + StoriesColumns.STORY_REQUESTED_BY + " TEXT, "
            + StoriesColumns.STORY_OWNED_BY + " TEXT, "
            + StoriesColumns.STORY_CREATED_AT + " TEXT, "
            + StoriesColumns.STORY_ACCEPTED_AT + " TEXT, "
            + StoriesColumns.STORY_LABELS + " TEXT, "
            + "UNIQUE (" + StoriesColumns.STORY_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_ATTACHMENTS = "CREATE TABLE " 
			+ Tables.ATTACHMENTS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AttachmentsColumns.ATTACHMENT_ID + " TEXT NOT NULL, "
            + AttachmentsColumns.ATTACHMENT_STORY_ID + " TEXT, "
            + AttachmentsColumns.ATTACHMENT_FILENAME + " TEXT, "
            + AttachmentsColumns.ATTACHMENT_DESCRIPTION + " TEXT, "
            + AttachmentsColumns.ATTACHMENT_UPLOADED_BY + " TEXT, "
            + AttachmentsColumns.ATTACHMENT_UPLOADED_AT + " TEXT, "
            + AttachmentsColumns.ATTACHMENT_URL + " TEXT, "
            + "UNIQUE (" + AttachmentsColumns.ATTACHMENT_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_TASKS = "CREATE TABLE " 
			+ Tables.TASKS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TasksColumns.TASKS_ID + " TEXT NOT NULL, "
            + TasksColumns.TASKS_STORY_ID + " TEXT NOT NULL, "
            + TasksColumns.TASKS_DESCRIPTION + " TEXT, "
            + TasksColumns.TASKS_POSITION + " TEXT, "
            + TasksColumns.TASKS_COMPLETE + " TEXT, "
            + TasksColumns.TASKS_CREATED_AT + " TEXT, "
            + "UNIQUE (" + TasksColumns.TASKS_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_ITERATIONS = "CREATE TABLE " 
			+ Tables.ITERATIONS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + IterationsColumns.ITERATION_ID + " TEXT NOT NULL, "
            + IterationsColumns.ITERATION_NUMBER + " TEXT, "
            + IterationsColumns.ITERATION_START + " TEXT, "
            + IterationsColumns.ITERATION_FINISH + " TEXT, "
            + IterationsColumns.ITERATION_TEAM_STRENGTH + " TEXT, "
            + "UNIQUE (" + IterationsColumns.ITERATION_ID + ") ON CONFLICT REPLACE);";
	
	private final String CREATE_TABLE_MEMBERSHIPS = "CREATE TABLE " 
			+ Tables.MEMBERSHIPS + " ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MembershipColumns.MEMBERSHIP_ID + " TEXT NOT NULL, "
            + MembershipColumns.MEMBERSHIP_PERSON_EMAIL + " TEXT, "
            + MembershipColumns.MEMBERSHIP_PERSON_NAME + " TEXT, "
            + MembershipColumns.MEMBERSHIP_PERSON_INITIALS + " TEXT, "
            + MembershipColumns.MEMBERSHIP_ROLE + " TEXT, "
            + MembershipColumns.MEMBERSHIP_PROJECT_ID + " TEXT, "
            + MembershipColumns.MEMBERSHIP_PROJECT_NAME + " TEXT, "
            + "UNIQUE (" + MembershipColumns.MEMBERSHIP_ID + ") ON CONFLICT REPLACE);";
	
	public AgileDashboardServiceDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_RECENT_ACTIVITY);
		db.execSQL(CREATE_TABLE_PROJECTS);
		db.execSQL(CREATE_TABLE_STORIES);
		db.execSQL(CREATE_TABLE_ATTACHMENTS);
		db.execSQL(CREATE_TABLE_TASKS);
		db.execSQL(CREATE_TABLE_ITERATIONS);
		db.execSQL(CREATE_TABLE_ITERATIONS_STORIES_PROJECTS);
		db.execSQL(CREATE_TABLE_MEMBERSHIPS);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion != DATABASE_VERSION) {
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ACTIVITIES);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PROJECTS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.STORIES);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ATTACHMENTS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.TASKS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ITERATIONS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ITERATIONS_STORIES_PROJECTS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.MEMBERSHIPS);
			
			onCreate(db);
		}
	}
}
