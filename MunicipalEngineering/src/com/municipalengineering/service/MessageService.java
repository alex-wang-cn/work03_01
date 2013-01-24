package com.municipalengineering.service;

import java.util.Timer;
import java.util.TimerTask;

import com.municipalengineering.activity.Login;
import com.municipalengineering.adapter.HandleAdapter;
import com.tools.SoapService;
import com.tools.XmlPaser;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class MessageService extends Service {

	private String mUserId = null;
	private Timer mTimer = new Timer();
	private final int getTaskCode = 1;
	private SoapService mSoapService;
	private int mTaskNum = 0;
	private String INTENT_MYMESSAGE = "com.wang.broadcast.taks";
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case getTaskCode:
				if ((mTaskNum = Integer.parseInt(XmlPaser.paserSingleTag(
						mSoapService.getReuslt(), "num"))) > 0) {
					Intent i = new Intent(INTENT_MYMESSAGE);
					i.putExtra(INTENT_MYMESSAGE, mTaskNum);
					sendBroadcast(i);
				}
				break;
			default:
				break;
			}
		};

	};

	private TimerTask mTask = new TimerTask() {
		@Override
		public void run() {
			//mSoapService.getTaskNumber(getTaskCode);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (Login.getUserBean() != null) {
			mUserId = Login.getUserBean().getUserId();
			mSoapService = new SoapService(mHandler);
			if (mUserId != null) {
				mTimer.schedule(mTask, 200, 60 * 1000);
			}
		}
	}

}
