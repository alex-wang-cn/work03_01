package com.municipalengineering.view;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.municipalengineering.activity.R;
import com.municipalengineering.myinterface.IMyView;
import com.tools.InitIconBeans;

public class CompanyInquiryView implements IMyView {
	private Context mContext;
	private View mConverView;
	private HashMap<Integer, String> mInqiuryLeftData = new HashMap<Integer, String>();
	private HashMap<Integer, List<String>> mInqiuryRightData = new HashMap<Integer, List<String>>();
	private Builder mBuilder;
	private EditText mTextView;

	public CompanyInquiryView(Context context) {
		this.mContext = context;
	}

	private View inflateView() {
		mConverView = LayoutInflater.from(mContext).inflate(
				R.layout.company_list, null);
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

	private void createDialog() {
		mBuilder = new Builder(mContext);
		mTextView = new EditText(mContext);
		mBuilder.setTitle("请输入查询的公司字段").setView(mTextView)
				.setPositiveButton("查询", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String tem = mTextView.getText().toString();
						if (tem.equals("")) {
							Toast.makeText(mContext, "请输入查询公司字段", 5 * 1000)
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

	@Override
	public IMyView reusme() {
		createDialog();
		return this;
	}
}
