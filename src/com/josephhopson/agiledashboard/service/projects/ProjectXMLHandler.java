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
package com.josephhopson.agiledashboard.service.projects;
import java.util.Stack;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Projects;

import android.content.ContentValues;
import android.content.Context;

/**
 * ProjectXMLHandler.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 15, 2012
 */
public class ProjectXMLHandler extends DefaultHandler {
	
	Context mContext;
	private ContentValues contentValues;
	boolean currentElement = false;
	String currentValue;
	String id;
	boolean mulitpleProjects;
	private final Stack<String> tagStack = new Stack<String>();
	
	public ProjectXMLHandler(Context context) {
		mContext = context;
		contentValues = new ContentValues();
		mulitpleProjects = false;
	}
	
	@Override
    public void endDocument() throws SAXException {
		if(mulitpleProjects) {
			ProjectHelper.requestFinished(ProjectHelper.ALL);
		} else {
			ProjectHelper.requestFinished(id);
		}
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, 
    		org.xml.sax.Attributes atts) throws SAXException {
        
		tagStack.push(localName);
		
        currentElement = true;
        currentValue = "";
        
        if(localName.equalsIgnoreCase("projects")) {
        	mulitpleProjects = true;
        }
	}
	
	@Override
    public void endElement(String namespaceURI, String localName, 
    		String qName) throws SAXException {
		
		tagStack.pop();
		currentElement = false;
		
		if(localName.equalsIgnoreCase("id") && tagStack.peek().equalsIgnoreCase("project")) {
			id = currentValue;
			contentValues.put(Projects.PROJECT_ID, currentValue);
		} else if(localName.equalsIgnoreCase("name") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_NAME, currentValue);
		} else if(localName.equalsIgnoreCase("iteration_length") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_INITIAL_VELOCITY, currentValue);
		} else if(localName.equalsIgnoreCase("week_start_day") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_WEEK_START_DAY, currentValue);
		} else if(localName.equalsIgnoreCase("point_scale") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_POINT_SCALE, currentValue);
		} else if(localName.equalsIgnoreCase("velocity_scheme") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_VELOCITY_SCHEME, currentValue);
		} else if(localName.equalsIgnoreCase("current_velocity") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_CURRENT_VELOCITY, currentValue);
		} else if(localName.equalsIgnoreCase("initial_velocity") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_INITIAL_VELOCITY, currentValue);
		} else if(localName.equalsIgnoreCase("number_of_done_iterations_to_show") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_NUMBER_OF_DONE_ITERATIONS_TO_SHOW, currentValue);
		} else if(localName.equalsIgnoreCase("labels") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_LABELS, currentValue);
		} else if(localName.equalsIgnoreCase("allow_attachments") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_ALLOW_ATTACHMENTS, currentValue);
		} else if(localName.equalsIgnoreCase("public") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_PUBLIC, currentValue);
		} else if(localName.equalsIgnoreCase("use_https") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_USE_HTTPS, currentValue);
		} else if(localName.equalsIgnoreCase("bugs_and_chores_are_estimatable") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_BUGS_CHORES_ARE_ESTIMATABLE, currentValue);
		} else if(localName.equalsIgnoreCase("commit_mode") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_COMMIT_MODE, currentValue);
		} else if(localName.equalsIgnoreCase("last_activity_at") && tagStack.peek().equalsIgnoreCase("project")) {
			contentValues.put(Projects.PROJECT_LAST_ACTIVITY_AT, currentValue);
		} else if(localName.equalsIgnoreCase("project")){
			
			// insert into db
			mContext.getContentResolver().insert(Projects.CONTENT_URI, contentValues);
			
			contentValues.clear();
		}
	}
	
	@Override
    public void characters(char ch[], int start, int length) {
		if(currentElement){
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
