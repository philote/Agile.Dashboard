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
package com.josephhopson.analytics.tracking;

import android.app.Activity;
import android.content.Context;

/**
 * Temporarily just a stub.
 */
public class EasyTracker {
    public static EasyTracker getTracker() {
        return new EasyTracker();
    }

    public void trackView(String s) {
    }

    public void trackActivityStart(Activity activity) {
    }

    public void trackActivityStop(Activity activity) {
    }

    public void setContext(Context context) {
    }

    public void trackEvent(String s1, String s2, String s3, long l) {
    }

    public void dispatch() {
    }
}
