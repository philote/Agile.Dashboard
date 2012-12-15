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

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Tasks;

import android.content.ContentValues;
import android.content.Context;

/**
 * StoryXMLHandler.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Nov 06, 2012
 */
public class TaskXMLHandler extends DefaultHandler {
	
	Context mContext;
	private String storyId;
	private ContentValues contentValues;
	private boolean currentElement = false;
	private String currentValue;
	private String id;
	private boolean mulitpleTasks;
	private final Stack<String> tagStack = new Stack<String>();
	
	public TaskXMLHandler(Context context, String storyId) {
		mContext = context;
		this.storyId = storyId;
		contentValues = new ContentValues();
		mulitpleTasks = false;
	}
	
	@Override
    public void endDocument() throws SAXException {
		if(mulitpleTasks) {
			TaskHelper.requestFinished(TaskHelper.ALL);
		} else {
			TaskHelper.requestFinished(id);
		}
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, 
    		org.xml.sax.Attributes atts) throws SAXException {
        
		tagStack.push(localName);
		
        currentElement = true;
        currentValue = "";
        
        if(localName.equalsIgnoreCase("tasks")) {
        	mulitpleTasks = true;
        }
	}
	
	@Override
    public void endElement(String namespaceURI, String localName, 
    		String qName) throws SAXException {
		
		tagStack.pop();
		currentElement = false;
		
		if(localName.equalsIgnoreCase("id") && tagStack.peek().equalsIgnoreCase("task")) {
			id = currentValue;
			contentValues.put(Tasks.TASKS_ID, currentValue);
		} else if(localName.equalsIgnoreCase("description") && tagStack.peek().equalsIgnoreCase("task")) {
			contentValues.put(Tasks.TASKS_DESCRIPTION, currentValue);
		} else if(localName.equalsIgnoreCase("position") && tagStack.peek().equalsIgnoreCase("task")) {
			contentValues.put(Tasks.TASKS_POSITION, currentValue);
		} else if(localName.equalsIgnoreCase("complete") && tagStack.peek().equalsIgnoreCase("task")) {
			contentValues.put(Tasks.TASKS_COMPLETE, currentValue);
		} else if(localName.equalsIgnoreCase("created_at") && tagStack.peek().equalsIgnoreCase("task")) {
			contentValues.put(Tasks.TASKS_CREATED_AT, currentValue);
		} else if(localName.equalsIgnoreCase("task")){
			
			contentValues.put(Tasks.TASKS_STORY_ID, storyId);
			
			// insert into db
			mContext.getContentResolver().insert(
					Tasks.buildTaskUri(
							"unknown", 
							contentValues.getAsString(Tasks.TASKS_STORY_ID), 
							contentValues.getAsString(Tasks.TASKS_ID)), 
					contentValues);
			
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
