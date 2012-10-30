package com.josephhopson.agiledashboard.adapters;

import com.josephhopson.agiledashboard.R;
import com.josephhopson.agiledashboard.service.provider.AgileDashboardServiceContract.Stories;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * StoriesListAdapter.java
 * Purpose:
 * 
 * @author Joseph Hopson
 * @version 1.0 Oct 30, 2012
 */
public class StoriesListAdapter extends CursorAdapter {
	
	public StoriesListAdapter(Context context) {
		super(context, null, 0);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		if(holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.projectName);
		}
		
		String name  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_NAME));
		if(!TextUtils.isEmpty(name)) {
			holder.name.setText(name);
		} else {
			// reset
			holder.name.setText(context.getString(R.string.story_name));
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.stories_list_item, viewGroup, false);
		bindView(view, context, cursor);
		return view;
	}
	
	class ViewHolder {
		TextView name;
	}
}