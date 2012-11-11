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
package com.josephhopson.agiledashboard.adapters;

import com.josephhopson.agiledashboard.R;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Projects;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ProjectsListAdapter.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 14, 2012
 */
public class ProjectsListAdapter extends CursorAdapter {
	
	public ProjectsListAdapter(Context context) {
		super(context, null, 0);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		TextView nameTextView = (TextView) view.findViewById(R.id.projectName);
		String name = cursor.getString(
				cursor.getColumnIndex(Projects.PROJECT_NAME));
		if(!TextUtils.isEmpty(name)) {
			nameTextView.setText(name);
		} else {
			// reset
			nameTextView.setText(context.getString(R.string.project_name));
		}
		
		TextView lastactivityTextView = (TextView) view.findViewById(R.id.lastactivity);
		String lastactivity = cursor.getString(
				cursor.getColumnIndex(Projects.PROJECT_LAST_ACTIVITY_AT));
		if(!TextUtils.isEmpty(lastactivity)) {
			lastactivityTextView.setText(lastactivity);
		} else {
			// reset
			lastactivityTextView.setText("");
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.projects_list_item_fragment, viewGroup, false);
		bindView(view, context, cursor);
		return view;
	}
}
