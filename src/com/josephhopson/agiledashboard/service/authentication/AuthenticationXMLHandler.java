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
package com.josephhopson.agiledashboard.service.authentication;

import java.util.Stack;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/**
 * AuthenticationXMLHandler.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 23, 2012
 */
public class AuthenticationXMLHandler extends DefaultHandler {
	
	public static int PROCESS_ITEM_SUCCEEDED = 1;
	
	Context mContext;
	Messenger mMessenger;
	String currentValue;
	boolean currentElement = false;
	private final Stack<String> tagStack = new Stack<String>();
	
	public AuthenticationXMLHandler(Context context, Messenger messenger) {
		mContext = context;
		mMessenger = messenger;
		Log.d(getClass().getName(), "Starting to process the responce.");
	}
	
	@Override
    public void endDocument() throws SAXException {
		AuthenticationHelper.requestFinished();
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, 
    		org.xml.sax.Attributes atts) throws SAXException {
		Log.d(getClass().getName(), "begin element: " + localName);
		currentValue = "";
		currentElement = true;
		tagStack.push(localName);
		
	}
	
	@Override
    public void endElement(String namespaceURI, String localName, 
    		String qName) throws SAXException {
		
		Log.d(getClass().getName(), "end element: " + localName);
		tagStack.pop();
		currentElement = false;
		
		if(localName.equalsIgnoreCase("guid") && tagStack.peek().equalsIgnoreCase("token")) {
			
			Log.d(getClass().getName(), "add token to shared prefs: " + currentValue);
			SharedPreferences mSharedPreferences = mContext.getSharedPreferences(AgileDashboardServiceConstants.TOKEN_PREF, Context.MODE_PRIVATE);
			SharedPreferences.Editor mEditor = mSharedPreferences.edit();
			mEditor.putString(AgileDashboardServiceConstants.TOKEN_PREF_KEY, currentValue);
			mEditor.commit();
			
			// set up the message
	    	Message msg = Message.obtain();
	    	msg.arg1 = PROCESS_ITEM_SUCCEEDED;
	        
	        try {
	        	mMessenger.send(msg);
	    		Log.d(getClass().getName(), "sending the message back.");
	    	} catch (android.os.RemoteException e1) {
	    		Log.e(getClass().getName(), "Exception sending message", e1);
	    	}
		}
		
	}
	
	@Override
    public void characters(char ch[], int start, int length) {
		if(currentElement){
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
