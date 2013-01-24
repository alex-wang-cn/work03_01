package com.municipalengineering.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.municipalengineering.activity.R;
import com.municipalengineering.entity.PersonBean;
import com.municipalengineering.myinterface.IMyView;
import com.tools.SoapService;
import com.tools.XmlPaser;

public class PersonnelInquiryView implements IMyView {
	private Context mContext;
	private View mConverView;
	private AlertDialog.Builder mBuilder;
	private SoapService mSoapService;

	public PersonnelInquiryView(Context context) {
		this.mContext = context;
		mSoapService = new SoapService(mHandler);
	}

	private View inflateView() {
		mConverView = LayoutInflater.from(mContext).inflate(
				R.layout.person_empty, null);
		return mConverView;
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

	// 用户名在
	private TextView mPersonName;
	private TextView mPersonIdcard;
	private TextView mSex;
	private TextView mCName;
	private TextView mCardNum;
	private TextView mCardXh;
	private TextView mHasUseCard;
	private Button mButton;

	@Override
	public IMyView reusme() {
		if (mBuilder == null) {
			initDialog();
		}
		mBuilder.show();
		return this;
	}

	private void initDialog() {
		mBuilder = new Builder(mContext);
		View dailogView = LayoutInflater.from(mContext).inflate(
				R.layout.person_quiry, null);
		mBuilder.setView(dailogView).setCancelable(false);
		mCName = (TextView) dailogView.findViewById(R.id.person_quiry_company);
		mPersonIdcard = (TextView) dailogView
				.findViewById(R.id.person_quiry_ic);
		mPersonName = (TextView) dailogView
				.findViewById(R.id.person_quiry_name);
		mCardNum = (TextView) dailogView
				.findViewById(R.id.person_quiry_raic_number);
		mSex = (TextView) dailogView.findViewById(R.id.person_quiry_sex);
		mButton = (Button) dailogView.findViewById(R.id.person_quiry_button);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mPersonIdcard.getText().length() < 13) {
					Toast.makeText(mContext, "请输入13位身份证呢号", 1000 * 5);
					return;
				}
				mBean.setIdCard(mPersonIdcard.getText().toString());
				getData();
			}
		});
	}

	protected void getData() {
		mButton.setEnabled(false);
		mSoapService
				.getPersonData(mBean.getIdCard(), SoapService.GetPersonData);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == SoapService.GetPersonData) {
				fillData();
			}
		};
	};
	private PersonBean mBean = new PersonBean();

	private void fillData() {
		XmlPaser.paserPersonData(mSoapService.getReuslt(), mBean);
		mPersonIdcard.setText(mBean.getIdCard());
		mCardNum.setText(mBean.getCardNum());
		mCardXh.setText(mBean.getCardXh());
		mCName.setText(mBean.getCName());
		mHasUseCard.setText(mBean.getHasUseCard());
		mPersonName.setText(mBean.getUserName());
		mButton.setEnabled(true);
	};
}
