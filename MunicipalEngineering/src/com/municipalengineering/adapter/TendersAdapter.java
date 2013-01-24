package com.municipalengineering.adapter;

import java.util.List;

import com.municipalengineering.activity.R;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.entity.TendersBean;
import com.municipalengineering.view.TendersInquiryView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TendersAdapter extends ArrayAdapter<TendersBean> {

	private List<TendersBean> mList;

	public TendersAdapter(Context context, int textViewResourceId,
			List<TendersBean> objects) {
		super(context, textViewResourceId, objects);
		mList = objects;
	}

	private TextView mText = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.inquiry_left_item, null);
			mText = (TextView) convertView
					.findViewById(R.id.inquiry_lieft_item_text);
		}
		mText.setText(mList.get(position).getName());
		return convertView;
	}

}