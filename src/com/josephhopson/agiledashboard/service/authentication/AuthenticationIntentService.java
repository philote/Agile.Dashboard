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

import com.josephhopson.agiledashboard.service.rest.AgileDashboardServiceRestClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/**
 * AuthenticationIntentService.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 23, 2012
 */
public class AuthenticationIntentService extends IntentService {
	
	public static int PROCESS_ITEM_FAILED = 2;
	
	public static final String URL = "com.josephhopson.pivotal.tracker.service.authentication.url";
	public static final String USERNAME = "com.josephhopson.pivotal.tracker.service.authentication.username";
	public static final String PASSWORD = "com.josephhopson.pivotal.tracker.service.authentication.password";
	public static final String MESSENGER = "com.josephhopson.pivotal.tracker.service.authentication.messenger";
	
	public AuthenticationIntentService() {
		super("AuthenticationIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d(getClass().getName(), "Starting intent.");
		
		String url = intent.getStringExtra(URL);
		String username = intent.getStringExtra(USERNAME);
		String password = intent.getStringExtra(PASSWORD);
		
		Messenger messenger = null;
		Bundle extras = intent.getExtras();
		if (extras!=null) {
    		messenger = (Messenger)extras.get(MESSENGER);
    	}
		
		AgileDashboardServiceRestClient mRestClient = new AgileDashboardServiceRestClient(url);
		mRestClient.addBasicAuthentication(username, password);
		
		try {
			mRestClient.execute();
			
			AuthenticationProcessor mAuthenticationProcessor = new AuthenticationProcessor(this);
			mAuthenticationProcessor.processAuthResponce(mRestClient.getResponse(), messenger);
			
		} catch (Exception e) {
			Log.e(getClass().getName(), "Exception authenticating user", e);
			AuthenticationHelper.requestFinished();
			
			// set up the message
	    	Message msg = Message.obtain();
	    	msg.arg1 = PROCESS_ITEM_FAILED;
	        
	        try {
	        	messenger.send(msg);
	    		Log.d(getClass().getName(), "Sending the failed authentication message back.");
	    	} catch (android.os.RemoteException e1) {
	    		Log.e(getClass().getName(), "Exception sending the failed authentication message", e1);
	    	}
		}
	}
	

}
