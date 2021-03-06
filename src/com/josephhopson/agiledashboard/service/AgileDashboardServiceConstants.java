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
package com.josephhopson.agiledashboard.service;

/**
 * ServiceConstants.java
 * Purpose: 
 * 
 * @author Joseph T. Hopson
 * @version 1.0 Oct 8, 2012
 */
public class AgileDashboardServiceConstants {
	
	private AgileDashboardServiceConstants() {}
	
	
	// ----------------------
	// Tracker API Constants
	// ----------------------
	
	// Tracker URL
	public static final String TRACKER_SERVICE_URL = "https://www.pivotaltracker.com/services/v3";
	
	// Path Constants
	public static final String ACTIVE_TOKENS = "tokens/active";
	
	public static final String ACTIVITES = "activities";
	
	public static final String PROJECTS = "projects";
	public static final String MEMBERSHIPS = "memberships";
	
	public static final String STORIES = "stories";
	public static final String TASKS = "tasks";
	public static final String ATTACHMENTS = "attachments";
	
	// Header Constants
	public static final String HEADER_TRACKERTOKEN= "X-TrackerToken";
	
	// Share prefs
	public static final String TOKEN_PREF = "com.josephhopson.pivotal.tracker.service.tokenpref";
	public static final String TOKEN_PREF_KEY = "com.josephhopson.pivotal.tracker.service.tokenpref.key";
}
