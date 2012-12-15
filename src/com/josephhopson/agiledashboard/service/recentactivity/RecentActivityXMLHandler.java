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

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Activities;

import android.content.ContentValues;
import android.content.Context;

/**
 * ActivitiesXMLHandler.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 15, 2012
 */
public class RecentActivityXMLHandler extends DefaultHandler {

	Context mContext;
	private ContentValues contentValues;
	boolean currentElement = false;
	String currentValue;
	String id;
	
	public RecentActivityXMLHandler(Context context) {
		mContext = context;
		contentValues = new ContentValues();
	}
	
	@Override
    public void endDocument() throws SAXException {
		RecentActivityHelper.requestFinished(id);
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, 
    		org.xml.sax.Attributes atts) throws SAXException {
        
        currentElement = true;
        currentValue = "";
	}
	
	@Override
    public void endElement(String namespaceURI, String localName, 
    		String qName) throws SAXException {
		
		currentElement = false;
		
		if(localName.equalsIgnoreCase("id")) {
			id = currentValue;
			contentValues.put(Activities.ACTIVITY_ID, currentValue);
		} else if(localName.equalsIgnoreCase("version")) {
			contentValues.put(Activities.ACTIVITY_VERSION, currentValue);
		} else if(localName.equalsIgnoreCase("event_type")) {
			contentValues.put(Activities.ACTIVITY_EVENT_TYPE, currentValue);
		} else if(localName.equalsIgnoreCase("occurred_at")) {
			contentValues.put(Activities.ACTIVITY_OCCURRED_AT, currentValue);
		} else if(localName.equalsIgnoreCase("author")) {
			contentValues.put(Activities.ACTIVITY_AUTHOR, currentValue);
		} else if(localName.equalsIgnoreCase("project_id")) {
			contentValues.put(Activities.ACTIVITY_PROJECT_ID, currentValue);
		} else if(localName.equalsIgnoreCase("description")) {
			contentValues.put(Activities.ACTIVITY_DESCRIPTION, currentValue);
		} else if(localName.equalsIgnoreCase("activity")){
			
			// insert into db
			mContext.getContentResolver().insert(Activities.CONTENT_URI, contentValues);
			
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
