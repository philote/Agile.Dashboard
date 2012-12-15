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

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * ServiceContract.java
 * Purpose: Contract class for interacting with {@link AgileDashboardServiceProvider}
 * 
 * @author Joseph T. Hopson
 * @version 1.0 Oct 8, 2012
 */
public class AgileDashboardServiceContract {
	
	private AgileDashboardServiceContract() {}
	
	interface ProjectsColumns {
		/** Unique string identifying this project. */
		String PROJECT_ID = "project_id";
		/** The name this project. */
		String PROJECT_NAME = "project_name";
		/** The iteration length this project. */
		String PROJECT_ITERATION_LENGTH = "project_iteration_length";
		/** The day of the week that this project starts. */
		String PROJECT_WEEK_START_DAY = "project_week_start_day";
		/** This project's point scale. */
		String PROJECT_POINT_SCALE = "project_point_scale";
		/** The account that this project belongs to. */
		String PROJECT_ACCOUNT = "project_account";
		/** The velocity scheme of this project. */
		String PROJECT_VELOCITY_SCHEME = "project_velocity_scheme";
		/** The current velocity of this project. */
		String PROJECT_CURRENT_VELOCITY = "project_current_velocity";
		/** The initial velocity of this project. */
		String PROJECT_INITIAL_VELOCITY = "project_initial_velocity";
		/** The number of done iterations to show of this project. */
		String PROJECT_NUMBER_OF_DONE_ITERATIONS_TO_SHOW = 
				"project_number_of_done_iterations_to_show";
		/** The labels for this project. */
		String PROJECT_LABELS = "project_labels";
		/** If this project allows attachments. */
		String PROJECT_ALLOW_ATTACHMENTS = "project_allow_attachments";
		/** If this project is public. */
		String PROJECT_PUBLIC = "project_public";
		/** If this project uses use_https. */
		String PROJECT_USE_HTTPS = "project_use_https";
		/** If bugs and chores are estimatable for this project. */
		String PROJECT_BUGS_CHORES_ARE_ESTIMATABLE = 
				"project_bugs_and_chores_are_estimatable";
		/** If this project uses commit mode. */
		String PROJECT_COMMIT_MODE = "project_commit_mode";
		/** When the last activity happened. */
		String PROJECT_LAST_ACTIVITY_AT = "project_last_activity_at";
		// TODO add relation to the members table
	}
	
	interface ActivitiesColumns {
		/** Unique string identifying this activity. */
		String ACTIVITY_ID = "activity_id";
		/** The version of this activity. */
		String ACTIVITY_VERSION = "activity_version";
		/** The typ of event that this activity represents. */
		String ACTIVITY_EVENT_TYPE = "activity_event_type";
		/** When this activity occurred. */
		String ACTIVITY_OCCURRED_AT = "activity_occurred_at";
		/** The author of this activity. */
		String ACTIVITY_AUTHOR = "activity_author";
		/** Unique id of the project that this activity happened in. */
		String ACTIVITY_PROJECT_ID = "activity_project_id";
		/** The description of this activity. */
		String ACTIVITY_DESCRIPTION = "activity_description";
		// TODO add a relation to the stories of an activity
	}
	
	interface StoriesColumns {
		/** Unique string identifying this story. */
		String STORY_ID = "story_id";
		/** Unique string identifying the project that this story belongs too. */
		String STORY_PROJECT_ID = "story_project_id";
		/** The name of this story. */
		String STORY_NAME = "story_name";
		/** The story_type for this story. */
		String STORY_TYPE = "story_type";
		/** The url for this story. */
		String STORY_URL = "story_url";
		/** The estimate of this story. */
		String STORY_ESTIMATE = "story_estimate";
		/** The current_state of this story. */
		String STORY_CURRENT_STATE = "story_current_state";
		/** The lighthouse_id for this story. */
		String STORY_LIGHTHOUSE_ID = "story_lighthouse_id";
		/** The lighthouse_url for this story. */
		String STORY_LIGHTHOUSE_URL = "story_lighthouse_url";
		/** The description for this story. */
		String STORY_DESCRIPTION = "story_description";
		/** The requested_by person for this story. */
		String STORY_REQUESTED_BY = "story_requested_by";
		/** The owned_by person for this story. */
		String STORY_OWNED_BY = "story_owned_by";
		/** The created_at date for this story. */
		String STORY_CREATED_AT = "story_created_at";
		/** The accepted_at date for this story. */
		String STORY_ACCEPTED_AT = "story_accepted_at";
		/** The labels for this story. */
		String STORY_LABELS = "story_labels";
	}
	
	interface AttachmentsColumns {
		/** Unique string identifying this attachment. */
		String ATTACHMENT_ID = "attachment_id";
		/** Unique string identifying the story that this attachment belongs too. */
		String ATTACHMENT_STORY_ID = "attachment_story_id";
		/** filename for this attachment. */
		String ATTACHMENT_FILENAME = "attachment_filename";
		/** description for this attachment. */
		String ATTACHMENT_DESCRIPTION = "attachment_description";
		/** uploaded_by person for this attachment. */
		String ATTACHMENT_UPLOADED_BY = "attachment_uploaded_by";
		/** uploaded_at date for this attachment. */
		String ATTACHMENT_UPLOADED_AT = "attachment_uploaded_at";
		/** url for this attachment. */
		String ATTACHMENT_URL = "attachment_url";
	}
	
	interface TasksColumns {
		/** Unique string identifying this task. */
		String TASKS_ID = "task_id";
		/** Unique string identifying the Story that this task belongs too. */
		String TASKS_STORY_ID = "tasks_story_id";
		/** the description for this task. */
		String TASKS_DESCRIPTION = "task_description";
		/** The position for this task. */
		String TASKS_POSITION = "task_position";
		/** weather or not this task is complete. */
		String TASKS_COMPLETE = "task_complete";
		/** the creation date for this task. */
		String TASKS_CREATED_AT = "task_created_at";
	}
	
	interface IterationsColumns {
		/** Unique string identifying this iteration. */
		String ITERATION_ID = "iteration_id";
		/** number for this iteration. */
		String ITERATION_NUMBER = "iteration_number";
		/** start date for this iteration. */
		String ITERATION_START= "iteration_start";
		/** finish date for this iteration. */
		String ITERATION_FINISH= "iteration_finish";
		/** team_strength for this iteration. */
		String ITERATION_TEAM_STRENGTH= "iteration_team_strength";
	}
	
	interface MembershipColumns {
		/** Unique string identifying this membership. */
		String MEMBERSHIP_ID = "member_id";
		/** email for this membership. */
		String MEMBERSHIP_PERSON_EMAIL = "member_person_email";
		/** name for this membership. */
		String MEMBERSHIP_PERSON_NAME = "member_person_name";
		/** initials for this membership. */
		String MEMBERSHIP_PERSON_INITIALS = "member_person_initials";
		/** role for this membership. */
		String MEMBERSHIP_ROLE = "member_role";
		/** project ID for this membership. */
		String MEMBERSHIP_PROJECT_ID = "member_project_id";
		/** project name for this membership. */
		String MEMBERSHIP_PROJECT_NAME = "member_project_name";
	}
	
	public static final String CONTENT_AUTHORITY = "com.josephhopson.pivotal.tracker.service";
	
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
	private static final String PATH_PROJECTS = "projects";
	private static final String PATH_ACTIVITIES = "activities";
	private static final String PATH_STORIES = "stories";
	private static final String PATH_ATTACHMENTS = "attachments";
	private static final String PATH_TASKS = "tasks";
	private static final String PATH_ITERATIONS = "iterations";
	private static final String PATH_MEMBERSHIPS = "memebers";
	
	public static class Projects implements ProjectsColumns, BaseColumns {
		
		// This class cannot be instantiated
		private Projects() {}
		
		
		// ----------------
		// URI
		// ----------------
		
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECTS).build();
		
		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.projects";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.projects";
        
        
        // ----------------
 		// URI builders
 		// ----------------
        
        /** Build {@link Uri} for requested {@link #PROJECT_ID}. */
        public static Uri buildProjectUri(String projectId) {
            return CONTENT_URI.buildUpon().appendPath(projectId).build();
        }
        
        
        // ----------------
  		// URI deconstructors
  		// ----------------
         
         public static String getProjectId(Uri uri) {
         	return uri.getPathSegments().get(1);
         }
	}
	
	public static class Activities implements ActivitiesColumns, BaseColumns {
		
		// This class cannot be instantiated
		private Activities() {}
		
		
		// ----------------
		// URI
		// ----------------
		
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIVITIES).build();
		
		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.activities";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.activities";
        
        
        // ----------------
 		// URI builders
 		// ----------------
        
        /** Default "ORDER BY" clause. */
        // TODO do this correctly with time/dates
        //public static final String DEFAULT_SORT = ActivitiesColumns.ACTIVITY_OCCURRED_AT + " ASC";
        
        /** Build {@link Uri} for requested {@link #ACTIVITY_ID}. */
        public static Uri buildActivityUri(String activityId) {
            return CONTENT_URI.buildUpon().appendPath(activityId).build();
        }
        
        public static Uri buildActivitesForProjectUri(String projectId) {
            return BASE_CONTENT_URI.buildUpon()
            		.appendPath(PATH_PROJECTS).appendPath(projectId)
            		.appendPath(PATH_ACTIVITIES).build();
        }
	}
	
	public static class Stories implements StoriesColumns, BaseColumns {

		// This class cannot be instantiated
		private Stories() {}
		
		
		// ----------------
		// URI
		// ----------------
		
//		public static final Uri CONTENT_URI = 
//				BASE_CONTENT_URI.buildUpon().appendPath(PATH_STORY).build();

		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.stories";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.stories";
        
        
        // ----------------
 		// URI builders
 		// ----------------
        
        /** Build {@link Uri} for requested {@link #STORY_ID}. */
        public static Uri buildStoryUri(String projectId, String storyId) {
            return BASE_CONTENT_URI.buildUpon()
            		.appendPath(PATH_PROJECTS).appendPath(projectId)
            		.appendPath(PATH_STORIES).appendPath(storyId).build();
        }
        
        public static Uri buildAllStoriesUri(String projectId) {
            return BASE_CONTENT_URI.buildUpon()
            		.appendPath(PATH_PROJECTS).appendPath(projectId)
            		.appendPath(PATH_STORIES).build();
        }
        
        
        // ----------------
 		// URI deconstructors
 		// ----------------
        
        public static String getStoryId(Uri uri) {
        	return uri.getPathSegments().get(3);
        }
	}
	
	public static class Tasks implements TasksColumns, BaseColumns {

		// This class cannot be instantiated
		private Tasks() {}
		
		
		// ----------------
		// URI
		// ----------------
		
//		public static final Uri CONTENT_URI = 
//				BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.tasks";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.tasks";
        
        
        // ----------------
 		// URI builders
 		// ----------------
        
        public static Uri buildTaskUri(String projectId, String storyId, String taskId) {
            return BASE_CONTENT_URI.buildUpon()
            		.appendPath(PATH_PROJECTS).appendPath(projectId)
            		.appendPath(PATH_STORIES).appendPath(storyId)
            		.appendPath(PATH_TASKS).appendPath(taskId).build();
        }
        
        public static Uri buildAllTasksUri(String projectId, String storyId) {
            return BASE_CONTENT_URI.buildUpon()
            		.appendPath(PATH_PROJECTS).appendPath(projectId)
            		.appendPath(PATH_STORIES).appendPath(storyId)
            		.appendPath(PATH_TASKS).build();
        }
        
        
        // ----------------
        // URI deconstructors
        // ----------------
        
        public static String getTaskId(Uri uri) {
        	return uri.getPathSegments().get(4);
        }
	}
	
	public static class Attachments implements AttachmentsColumns, BaseColumns {

		// This class cannot be instantiated
		private Attachments() {}
		
		
		// ----------------
		// URI
		// ----------------
		
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_ATTACHMENTS).build();

		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.attachments";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.attachments";
        
        
        // ----------------
 		// URI builders
 		// ----------------
        
        /** Build {@link Uri} for requested {@link #STORY_ID}. */
//        public static Uri buildAttachmentUri(String attachmentId) {
//            return CONTENT_URI.buildUpon().appendPath(attachmentId).build();
//        }
	}
	
	public static class Iterations implements IterationsColumns, BaseColumns {

		// This class cannot be instantiated
		private Iterations() {}
		
		
		// ----------------
		// URI
		// ----------------
		
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITERATIONS).build();

		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.iterations";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.iterations";
        
        
        // ----------------
  		// URI deconstructors
  		// ----------------
         
         public static String getIterationId(Uri uri) {
         	return uri.getPathSegments().get(1);
         }
	}
	
	public static class Memberships implements MembershipColumns, BaseColumns {

		// This class cannot be instantiated
		private Memberships() {}
		
		
		// ----------------
		// URI
		// ----------------
		
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEMBERSHIPS).build();

		
		// ----------------
		// MIME types
		// ----------------
		
		public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.tracker.memberships";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.tracker.memberships";
        
        
        // ----------------
 		// URI builders
 		// ----------------
	}
}
