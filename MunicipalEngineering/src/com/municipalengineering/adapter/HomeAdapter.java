package com.municipalengineering.adapter;

import java.util.List;

import com.municipalengineering.activity.R;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.view.TendersInquiryView;
import com.municipalengineering.view.TendersInspectionView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends ArrayAdapter<IconBean> {

	public HomeAdapter(Context context, int textViewResourceId,
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
	private TextView mTextView = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.grid_home_item, null);
			mImageView = (ImageView) convertView
					.findViewById(R.id.home_item_image);
		}
		mImageView.setImageResource(mResIds.get(position).getResId());
		if (mResIds.get(position).getClassName()
				.equals(TendersInspectionView.class.getName())
				&& mTextView == null) {
			mTextView = (TextView) convertView
					.findViewById(R.id.grid_item_text);
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("0");
		}
		return convertView;
	}

	public void setTenderInspectionNumber(String num) {
		if (mTextView != null) {
			mTextView.setText(num);
		}
	}

}