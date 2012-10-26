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
package com.josephhopson.agiledashboard;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.josephhopson.analytics.tracking.EasyTracker;

/**
 * BaseActivity.java
 * Purpose: A base activity that handles common functionality in the app.
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 13, 2012
 */
public class BaseActivity extends SherlockFragmentActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasyTracker.getTracker().setContext(this);
        
        getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	// TODO figure out what to do on home click
//            	if (this instanceof DashboardActivity) {
//                    return false;
//                }
            	return true;
        }
        return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getTracker().trackActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getTracker().trackActivityStop(this);
    }
}
