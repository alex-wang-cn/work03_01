package com.municipalengineering.adapter;

import java.util.List;

import com.municipalengineering.activity.R;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.entity.VDTBean;
import com.municipalengineering.entity.VDTBean;
import com.municipalengineering.view.TendersInquiryView;
import com.municipalengineering.view.TendersInspectionView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class VdtsAdapter extends ArrayAdapter<VDTBean> {

	public VdtsAdapter(Context context, int textViewResourceId,
			List<VDTBean> objects) {
		super(context, textViewResourceId, objects);
		this.mVDTBean = objects;
	}

	private List<VDTBean> mVDTBean;

	@Override
	public void insert(VDTBean object, int index) {
		super.insert(object, index);
		mVDTBean.add(index, object);
	}

	private static TextView mInstallPlace = null;
	private static TextView mCameraSerialNum = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.vdts_inquiry_item, null);
			mInstallPlace = (TextView) convertView.findViewById(R.id.vdts_item_install_place_text);
			mCameraSerialNum = (TextView) convertView.findViewById(R.id.vdts_item_install_num_text);
		}
		
		mInstallPlace.setText(mVDTBean.get(position).getInstallPlace());
		mCameraSerialNum.setText(mVDTBean.get(position).getId());
		return convertView;
	}

}