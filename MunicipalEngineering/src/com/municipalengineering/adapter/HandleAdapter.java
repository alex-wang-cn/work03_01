package com.municipalengineering.adapter;

import java.util.List;

import com.municipalengineering.activity.R;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.view.TendersInquiryView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HandleAdapter extends ArrayAdapter<IconBean> {

	public HandleAdapter(Context context, int textViewResourceId,
			List<IconBean> objects) {
		super(context, textViewResourceId, objects);
		this.mResIds = objects;
	}

	private List<IconBean> mResIds;

	@Override
	public void insert(IconBean object, int index) {
		super.insert(object, index);
		mResIds.add(index, object);
	}

	public List<IconBean> getResIds() {
		return mResIds;
	}

	private ImageView mImageView;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.grid_handle_item, null);
			mImageView = (ImageView) convertView
					.findViewById(R.id.handle_item_image);
		}
		mImageView.setImageResource(mResIds.get(position).getResId());
		return convertView;
	}

}