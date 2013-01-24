package com.municipalengineering.fragment;


import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.municipalengineering.activity.R;

public class CompanyInquiryFragment extends Fragment {
	private Context mContext;
	private Builder mBuilder;
	private EditText mTextView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.company_list, container, false);
	}
	

	private void createDialog() {
		mBuilder = new Builder(mContext);
		mTextView = new EditText(mContext);
		mBuilder.setTitle("�������ѯ�Ĺ�˾�ֶ�").setView(mTextView)
				.setPositiveButton("��ѯ", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String tem = mTextView.getText().toString();
						if (tem.equals("")) {
							Toast.makeText(mContext, "�������ѯ��˾�ֶ�", 5 * 1000)
									.show();
							createDialog();
						} else {
							getData(tem);
						}
					}
				}).setCancelable(false);
		mBuilder.show();
	}

	protected void getData(String tem) {

	}

}
