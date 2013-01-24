package com.municipalengineering.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.municipalengineering.myinterface.IMyView;
import com.tools.SoapService;

public class TendersInquiryView extends TendersInspectionView {
	private Context mContext;
	private AlertDialog.Builder mBuilder;

	public TendersInquiryView(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public IMyView reusme() {
		createDialog();
		return this;
	}

	private TextView mTextView;

	private void createDialog() {
		mBuilder = new Builder(mContext);
		mTextView = new EditText(mContext);
		mBuilder.setTitle("«Î ‰»Î").setView(mTextView)
				.setPositiveButton("≤È—Ø", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String tenderName = mTextView.getText().toString();
						if (tenderName.equals("")) {
							Toast.makeText(mContext,
									mTextView.getText().toString(), 5 * 1000)
									.show();
							createDialog();
						} else {
							mSoapService.quiryTenders(tenderName,
									SoapService.QuiryTenders);
						}

					}
				}).setCancelable(false);
		mBuilder.show();
	}
}
