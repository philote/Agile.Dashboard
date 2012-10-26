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

import com.josephhopson.pivotal.tracker.dashboard.R;
import com.josephhopson.agiledashboard.service.provider.TrackerServiceContract.Projects;

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
	
	private LayoutInflater inflater;
	// TODO get from string.xml
	private static final String PROJECT_NAME = "Project Name";
	
	/**
	 * @param context
	 */
	public ProjectsListAdapter(Context context) {
		super(context, null, 0);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		if(holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.projectName);
		}
		
		String name  = cursor.getString(
				cursor.getColumnIndex(Projects.PROJECT_NAME));
		if(!TextUtils.isEmpty(name)) {
			holder.name.setText(name);
		} else {
			// reset
			holder.name.setText(PROJECT_NAME);
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View view = inflater.inflate(R.layout.projects_list_item, null);
		bindView(view, context, cursor);
		return view;
	}
	
	class ViewHolder{
		TextView name;
	}

}
