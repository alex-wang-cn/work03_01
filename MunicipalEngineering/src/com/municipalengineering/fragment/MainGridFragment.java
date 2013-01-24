package com.municipalengineering.fragment;

import java.util.ArrayList;

import com.municipalengineering.activity.R;
import com.municipalengineering.adapter.HomeAdapter;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.myinterface.IMyView;
import com.municipalengineering.view.ViewDirector;
import com.tools.InitIconBeans;
import com.tools.SoapService;
import com.tools.XmlPaser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainGridFragment extends Fragment {
	public interface MainGridItemClickLisner {
		void onItemSelect(int position);
	}

	private MainGridItemClickLisner mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_grid_view, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.home_grid);
		mAdapter = new HomeAdapter(getActivity(), gridView.getId(), mResIds);
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (mCallback != null) {
					mCallback.onItemSelect(position);
				}
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mCallback = (MainGridItemClickLisner) activity;
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString()
                    + " must implement MainGridItemClickLisner");
		}
	}
	private HomeAdapter mAdapter;

	private ArrayList<IconBean> mResIds = InitIconBeans.getInstance()
			.getHomeIcons();

	SoapService mSoapService;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetTaskResultCode:
				mAdapter.setTenderInspectionNumber(XmlPaser.paserSingleTag(
						mSoapService.getReuslt(), "num"));
				mAdapter.notifyDataSetChanged();
				break;
			case SoapService.SERVICE_ERRO:
				Toast.makeText(getActivity(), "����l�Ӵ���", 3 * 1000).show();
			default:
				break;
			}
		};
	};
	private final int GetTaskResultCode = 1;

}
