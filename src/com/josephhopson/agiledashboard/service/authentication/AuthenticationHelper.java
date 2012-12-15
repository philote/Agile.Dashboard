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

import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Messenger;
import android.util.Log;

/**
 * AuthenticationServiceHelper.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 23, 2012
 */
public class AuthenticationHelper {
	
	private static AuthenticationHelper ourInstance = new AuthenticationHelper();
	private static boolean authInProcess = false;
	
	private AuthenticationHelper() {}
	
	public static void getUserToken(Context context, String username, 
			String password, Handler authenticationHandler) {
		if(!authInProcess) {
			authInProcess = true;
			
			Log.d("AuthenticationHelper", "Auth in progress");
			
			String url = AgileDashboardServiceConstants.TRACKER_SERVICE_URL + "/" 
					+ AgileDashboardServiceConstants.ACTIVE_TOKENS;
			
			Intent mAuthenticationIntentService = new Intent(context, AuthenticationIntentService.class);
			mAuthenticationIntentService.putExtra(AuthenticationIntentService.URL, url);
			mAuthenticationIntentService.putExtra(AuthenticationIntentService.USERNAME, username);
			mAuthenticationIntentService.putExtra(AuthenticationIntentService.PASSWORD, password);
			mAuthenticationIntentService.putExtra(AuthenticationIntentService.MESSENGER, new Messenger(authenticationHandler));
			context.startService(mAuthenticationIntentService);
		}
	}
	
	// -------------------------------
  	// Helper functions
  	// -------------------------------
     
    public static AuthenticationHelper getInstance() {
        return ourInstance;
    }
    
    public static void requestFinished() {
    	authInProcess = false;
    }
}
