package com.municipalengineering.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.municipalengineering.activity.R;
import com.municipalengineering.adapter.HandleAdapter;
import com.municipalengineering.adapter.HomeAdapter;
import com.municipalengineering.entity.IconBean;
import com.municipalengineering.myinterface.IMyAdapter;
import com.municipalengineering.myinterface.IMyView;
import com.tools.InitIconBeans;
import com.tools.SoapService;
import com.tools.XmlPaser;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class HomeView implements IMyView, IMyAdapter {

	private Context mContext;
	private View mConverView;
	private HomeAdapter mAdapter;
    private ViewDirector mVd;
	public HomeView(Context context) {
		this.mContext = context;
		mSoapService = new SoapService(mHandler);
	    mVd = ViewDirector.getInstance();
	}

	private ArrayList<IconBean> mResIds = InitIconBeans.getInstance()
			.getHomeIcons();

	private void inflateView() {

		mConverView = LayoutInflater.from(mContext).inflate(
				R.layout.home_grid_view, null);
		GridView gridView = (GridView) mConverView.findViewById(R.id.home_grid);
		mAdapter = new HomeAdapter(mContext, gridView.getId(), mResIds);

		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mVd.changeView(mAdapter.getItem(arg2).getClassName());

			}
		});
	}

	@Override
	public ArrayAdapter<IconBean> getAdapter() {
		return mAdapter;
	}

	SoapService mSoapService;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetTaskResultCode:
				mAdapter.setTenderInspectionNumber(XmlPaser
						.paserSingleTag(mSoapService.getReuslt(),"num"));
				mAdapter.notifyDataSetChanged();
				break;
			case SoapService.SERVICE_ERRO:
				Toast.makeText(mContext, "ÍøÂçÁ¬½Ó´íÎó", 3*1000).show();
			default:
				break;
			}
		};
	};
	private final int GetTaskResultCode = 1;

	@Override
	public IMyView reusme() {
		mSoapService.getTaskNumber(GetTaskResultCode);
		return this;
	}

	@Override
	public View getMyView() {
		if (mConverView == null) {
			inflateView();
		}
		return mConverView;
	}

	@Override
	public IMyView onPause() {
		// mContext.un
		return null;
	}
}
