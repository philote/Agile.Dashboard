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
		TextView nameTextView = (TextView) view.findViewById(R.id.storyName);
		String name  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_NAME));
		if(!TextUtils.isEmpty(name)) {
			nameTextView.setText(name);
		} else {
			// reset
			nameTextView.setText(context.getString(R.string.story_name));
		}
		
		TextView ownedbyTextView = (TextView) view.findViewById(R.id.ownedby);
		String ownedby  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_OWNED_BY));
		if(!TextUtils.isEmpty(ownedby)) {
			ownedbyTextView.setText(ownedby);
		} else {
			// reset
			ownedbyTextView.setText("");
		}
		
		TextView currentstateTextView = (TextView) view.findViewById(R.id.currentstate);
		String currentstate  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_CURRENT_STATE));
		if(!TextUtils.isEmpty(currentstate)) {
			currentstateTextView.setText(currentstate);
		} else {
			// reset
			currentstateTextView.setText("");
		}
		
		TextView estimateTextView = (TextView) view.findViewById(R.id.estimate);
		String estimate  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_ESTIMATE));
		if(!TextUtils.isEmpty(estimate)) {
			estimateTextView.setText(estimate);
		} else {
			// reset
			estimateTextView.setText("");
		}
		
		TextView storytypeTextView = (TextView) view.findViewById(R.id.storytype);
		String storytype  = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_TYPE));
		if(!TextUtils.isEmpty(storytype)) {
			storytypeTextView.setText(storytype);
		} else {
			// reset
			storytypeTextView.setText("");
		}
		
		TextView storylabelsTextView = (TextView) view.findViewById(R.id.storylabels);
		String storylabels = cursor.getString(
				cursor.getColumnIndex(Stories.STORY_LABELS));
		if(!TextUtils.isEmpty(storylabels)) {
			storylabelsTextView.setText(storylabels);
		} else {
			// reset
			storylabelsTextView.setText(context.getString(R.string.story_name));
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.stories_list_item_fragment, viewGroup, false);
		bindView(view, context, cursor);
		return view;
	}
}
