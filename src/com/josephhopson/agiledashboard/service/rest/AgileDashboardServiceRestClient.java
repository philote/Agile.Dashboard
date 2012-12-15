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
package com.josephhopson.agiledashboard.service.rest;

import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.josephhopson.rest.AuthenticationException;
import com.josephhopson.rest.Constants;
import com.josephhopson.rest.RestClient;
import com.josephhopson.rest.RestServiceException;
import com.josephhopson.agiledashboard.service.AgileDashboardServiceConstants;

/**
 * TrackerRestClient.java
 * Purpose: 
 * 
 * @author Joseph T. Hopson
 * @version 1.0 Oct 8, 2012
 */
public class AgileDashboardServiceRestClient extends RestClient {
	
	private BasicNameValuePair trackerToken;
	
	private int backOffCounter = 0;
	private final int[] backOffTimesInMillisecs = {0, 30000, 60000, 90000, -1};
	
	public AgileDashboardServiceRestClient(String url) {
		super(url);
	}
	
	public AgileDashboardServiceRestClient(String url, String token) {
		super(url);
		
		trackerToken = new BasicNameValuePair(AgileDashboardServiceConstants.HEADER_TRACKERTOKEN, token);
		
		addTrackerHeaders();
	}
	
	public void execute() throws RestServiceException {
		int delay = backOffTimesInMillisecs[backOffCounter];
		if(delay == -1) {
			throw new RestServiceException("Service is not responding");
		}
		
		try {
			if(delay > 0) {
				Thread.sleep(delay);
			}
			
			super.execute(Constants.RequestMethod.GET);
			// TODO could be other types
			
			checkForErrors();
		} catch (Exception e) {
			Log.e(getClass().getName(), "Exception executing request", e);
			if(e instanceof AuthenticationException) {
				throw (AuthenticationException)e;
			}
			backOffCounter++;
			execute();
		}
	}
	
	
	//----------------------
	// Helper Functions
	//----------------------
	
	/**
	 * helper function to add the generic headers to the client
	 */
	private void addTrackerHeaders() {
		addHeader(trackerToken.getName(), trackerToken.getValue());
	}
	
	private void checkForErrors() throws RestServiceException {
		if(getResponseCode() == 401) {
			throw new AuthenticationException(Constants.UNABLE_TO_AUTHENTICATE_MSG);
		} else if(getResponseCode() >= 300 || getResponseCode() <= 199) {
			throw new RestServiceException(getErrorMessage(), getResponseCode());
		}
    }
}
