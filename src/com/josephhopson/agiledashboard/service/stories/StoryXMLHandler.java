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

import java.util.Stack;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;

import android.content.ContentValues;
import android.content.Context;

/**
 * StoryXMLHandler.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Nov 06, 2012
 */
public class StoryXMLHandler extends DefaultHandler {
	
	Context mContext;
	private ContentValues contentValues;
	boolean currentElement = false;
	String currentValue;
	String id;
	boolean mulitpleStories;
	private final Stack<String> tagStack = new Stack<String>();
	
	public StoryXMLHandler(Context context) {
		mContext = context;
		contentValues = new ContentValues();
		mulitpleStories = false;
	}
	
	@Override
    public void endDocument() throws SAXException {
		if(mulitpleStories) {
			StoryHelper.requestFinished(StoryHelper.ALL);
		} else {
			StoryHelper.requestFinished(id);
		}
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, 
    		org.xml.sax.Attributes atts) throws SAXException {
        
		tagStack.push(localName);
		
        currentElement = true;
        currentValue = "";
        
        if(localName.equalsIgnoreCase("stories")) {
        	mulitpleStories = true;
        }
	}
	
	@Override
    public void endElement(String namespaceURI, String localName, 
    		String qName) throws SAXException {
		
		tagStack.pop();
		currentElement = false;
		
		if(localName.equalsIgnoreCase("id") && tagStack.peek().equalsIgnoreCase("story")) {
			id = currentValue;
			contentValues.put(Stories.STORY_ID, currentValue);
		} else if(localName.equalsIgnoreCase("project_id") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_PROJECT_ID, currentValue);
		} else if(localName.equalsIgnoreCase("story_type") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_TYPE, currentValue);
		} else if(localName.equalsIgnoreCase("url") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_URL, currentValue);
		} else if(localName.equalsIgnoreCase("estimate") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_ESTIMATE, currentValue);
		} else if(localName.equalsIgnoreCase("current_state") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_CURRENT_STATE, currentValue);
		} else if(localName.equalsIgnoreCase("description") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_DESCRIPTION, currentValue);
		} else if(localName.equalsIgnoreCase("name") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_NAME, currentValue);
		} else if(localName.equalsIgnoreCase("requested_by") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_REQUESTED_BY, currentValue);
		} else if(localName.equalsIgnoreCase("owned_by") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_OWNED_BY, currentValue);
		} else if(localName.equalsIgnoreCase("created_at") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_CREATED_AT, currentValue);
		} else if(localName.equalsIgnoreCase("accepted_at") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_ACCEPTED_AT, currentValue);
		} else if(localName.equalsIgnoreCase("labels") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_LABELS, currentValue);
		} else if(localName.equalsIgnoreCase("lighthouse_id") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_LIGHTHOUSE_ID, currentValue);
		} else if(localName.equalsIgnoreCase("lighthouse_url") && tagStack.peek().equalsIgnoreCase("story")) {
			contentValues.put(Stories.STORY_LIGHTHOUSE_URL, currentValue);
		} else if(localName.equalsIgnoreCase("story")){
			
			// insert into db
			mContext.getContentResolver().insert(Stories.buildAllStoriesUri(
					contentValues.getAsString(Stories.STORY_PROJECT_ID)), 
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
