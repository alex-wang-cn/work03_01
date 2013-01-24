package com.municipalengineering.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.municipalengineering.activity.R;
import com.municipalengineering.adapter.TendersAdapter;
import com.municipalengineering.entity.TendersBean;
import com.municipalengineering.myinterface.IMyView;
import com.tools.MyProgressBar;
import com.tools.SoapService;
import com.tools.XmlPaser;

public class TendersInspectionView implements IMyView {
	private Context mContext;
	private View mConverView;
	public static final String tag = "TendersInquiryView";
	protected SoapService mSoapService;
	private ArrayList<TendersBean> mListViewData = new ArrayList<TendersBean>();
	private Button mNetErro;
	private List<String> mCMMStateArray = new ArrayList<String>();
	private List<String> mWatcherStateArray= new ArrayList<String>();

	public TendersInspectionView(Context context) {
		this.mContext = context;
		mSoapService = new SoapService(mHandler);
		Collections.addAll(mCMMStateArray, mContext.getResources()
				.getStringArray(R.array.ccm_state));
		Collections.addAll(mWatcherStateArray, mContext.getResources()
				.getStringArray(R.array.screen_view_state));
	}

	private TendersAdapter mAdapter;

	private void inflateView() {
		mConverView = LayoutInflater.from(mContext).inflate(
				R.layout.inquiry_list, null);

		ListView listView = (ListView) mConverView
				.findViewById(R.id.inqiry_list_left);
		mAdapter = new TendersAdapter(mContext, listView.getId(), mListViewData);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getTendersData(mAdapter.getItem(position));
			}
		});

		mCCMStateSpinner = (Spinner) mConverView
				.findViewById(R.id.tender_ccm_sate);
		mCCMStateSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View arg1, int postion, long arg3) {
						if (mCurrentTenderBean != null)
							mCurrentTenderBean.setSkjzt((String) parent
									.getItemAtPosition(postion));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		mScreenViewStateSpinner = (Spinner) mConverView
				.findViewById(R.id.tenders_screen_view_state);

		mScreenViewStateSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View arg1, int position, long arg3) {
						if (mCurrentTenderBean != null)
							mCurrentTenderBean.setSpzt((String) parent
									.getItemAtPosition(position));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		mComments = (TextView) mConverView.findViewById(R.id.tenders_comment);
		mTenderPersonName = (TextView) mConverView
				.findViewById(R.id.tenders_name);
		mTenderPersonPhone = (TextView) mConverView
				.findViewById(R.id.tenders_person_phone);
		mProgressBar = (MyProgressBar) mConverView
				.findViewById(R.id.tenders_progress_bar);
		mlistProgressBar = (MyProgressBar) mConverView
				.findViewById(R.id.list_progress_bar);
		mNetErro = (Button) mConverView.findViewById(R.id.tenders_lost_net);

		mNetErro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSoapService.getTaskByUser(GetTestkCode);
				mlistProgressBar.setVisibility(View.VISIBLE);
				mNetErro.setVisibility(View.GONE);
			}
		});

	}

	private View mInquiryRightView;

	protected final int UploadImage1 = 1;
	protected final int UploadImage2 = UploadImage1 + 1;
	protected final int GetTestkCode = UploadImage2 + 1;
	protected final int GetTendersResult = GetTestkCode + 1;
	protected final int UploadInspectionData = GetTendersResult + 1;
    
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetTestkCode:
				paserListData();
				mAdapter.notifyDataSetChanged();
				mlistProgressBar.setVisibility(View.GONE);
				break;
			case GetTendersResult:
				fillTenderData();
				break;
			case UploadImage1:
				mSoapService.upLoadImage(mPaths.get(2),
						mCurrentTenderBean.getId(), UploadImage2);
				break;

			case UploadImage2:
				mSoapService.upLoadTenderData(mCurrentTenderBean,
						UploadInspectionData);
				break;
			case UploadInspectionData:
				upLoadend();
				break;
				
			case SoapService.QuiryTenders:
				break;
			case SoapService.SERVICE_ERRO:
				dealNetErro();
			default:
				break;
			}
		}

	};

	private boolean isDataUpload = false;

	private void dealNetErro() {
		if (isDataUpload) {
			mProgressBar.setVisibility(View.GONE);
			isDataUpload = false;
			Toast.makeText(mContext, "网络错误，上传失败，请重试", 5 * 1000).show();
		} else {
			mNetErro.setVisibility(View.VISIBLE);
			mlistProgressBar.setVisibility(View.GONE);
		}
	};

	private void upLoadend() {
		isDataUpload = false;
		mProgressBar.setVisibility(View.GONE);
	}

	protected void paserListData() {
		Log.i(tag, mSoapService.getReuslt());
		XmlPaser.paserTenderA(mListViewData, mSoapService.getReuslt());
	}

	private TextView mTenderPersonName;
	private TextView mTenderPersonPhone;
	private TextView mComments;
	private Spinner mCCMStateSpinner;
	private Spinner mScreenViewStateSpinner;

	protected void fillTenderData() {
		XmlPaser.paserTendersData(mCurrentTenderBean, mSoapService.getReuslt());
		((TextView) mConverView.findViewById(R.id.tenders_area))
				.setText(mCurrentTenderBean.getArea());
		((TextView) mConverView.findViewById(R.id.tenders_manager_name))
				.setText(mCurrentTenderBean.getProjectManager());
		((TextView) mConverView.findViewById(R.id.tenders_manager_phone))
				.setText(mCurrentTenderBean.getProjectManagerPhone());
		((TextView) mConverView.findViewById(R.id.tenders_name))
				.setText(mCurrentTenderBean.getName());
		// ((TextView)mConverView.findViewById(R.id.tenders_person_name)).setText(mCurrentTenderBean.getTenderPersonName());
		// ((TextView)mConverView.findViewById(R.id.tenders_person_phone)).setText(mCurrentTenderBean.getTenderPersionPhone());
		((TextView) mConverView.findViewById(R.id.tenders_project_build_unit))
				.setText(mCurrentTenderBean.getGfZt());
		((TextView) mConverView.findViewById(R.id.tenders_salary_manage_state))
				.setText(mCurrentTenderBean.getMonitorTypeGz());
		((TextView) mConverView
				.findViewById(R.id.tender_realy_name_manage_state))
				.setText(mCurrentTenderBean.getMonitorType());
		mCCMStateSpinner.setSelection(
				mCMMStateArray.indexOf(mCurrentTenderBean.getSkjzt()), true);
		mScreenViewStateSpinner.setSelection(
				mWatcherStateArray.indexOf(mCurrentTenderBean.getSpzt()), true);
		mProgressBar.setVisibility(View.GONE);

	}

	private TendersBean mCurrentTenderBean;
	private MyProgressBar mProgressBar;
	private MyProgressBar mlistProgressBar;

	private void getTendersData(TendersBean tendersBean) {
		mCurrentTenderBean = tendersBean;
		mProgressBar.setVisibility(View.VISIBLE);
		mSoapService.getTenders(mCurrentTenderBean.getBId(), GetTendersResult);

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
		return this;
	}

	@Override
	public IMyView reusme() {
		mSoapService.getTaskByUser(GetTestkCode);
		mlistProgressBar.setVisibility(View.VISIBLE);
		return this;
	}

	public String getTaskId() {
		return mCurrentTenderBean.getId();
	}

	private HashMap<Integer, String> mPaths;

	public void upLoad(HashMap<Integer, String> paths) {
		mProgressBar.setVisibility(View.VISIBLE);
		isDataUpload = true;
		mPaths = paths;
		if (!upLoadImage(UploadImage1)) {
			if (!upLoadImage(UploadImage2)) {
				mSoapService.upLoadTenderData(mCurrentTenderBean,
						UploadInspectionData);

			}
		}

	}

	private boolean upLoadImage(int tag) {
		String filePaths = mPaths.get(tag);
		if (filePaths != null || !filePaths.equals("")) {
			mProgressBar.setText("相片上传中");
			mSoapService.upLoadImage(filePaths, mCurrentTenderBean.getId(),
					UploadImage2);
			return true;
		}
		return false;
	}
}
