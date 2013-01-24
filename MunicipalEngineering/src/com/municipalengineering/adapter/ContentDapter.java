package com.municipalengineering.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.municipalengineering.activity.R;

public class ContentDapter extends BaseAdapter {

	private Context mCotent;
	private ViewTag myTag;

	public ContentDapter(Context context) {
		this.mCotent = context;
	}

	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		if (myTag != null) {
			return myTag.icon;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			myTag = new ViewTag();
			convertView.setTag(myTag);
		} else {
			myTag = (ViewTag) convertView.getTag();
		}

		myTag.icon.setText("!!!!!!!!!!!!!!!!!!" + position);
		myTag.icon.setTextSize(18);
		return convertView;
	}

	public static class ViewTag {
		TextView icon;
	}
}