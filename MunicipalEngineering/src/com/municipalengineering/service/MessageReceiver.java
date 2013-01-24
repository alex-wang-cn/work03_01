package com.municipalengineering.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.municipalengineering.activity.Login;
import com.municipalengineering.activity.R;

public class MessageReceiver extends BroadcastReceiver {

	NotificationManager manager = null;
	Notification notification = null;
    private Context mContext;
	@Override
	public void onReceive(Context context, Intent i) {
		if (i.getAction().equals(MessageService.INPUT_METHOD_SERVICE)) {
			if (manager == null) {
				notification = new Notification();
				notification.icon = R.drawable.icon_bdxj;
			}
			this.mContext = context;
			notification.tickerText = "���������� "
					+ i.getExtras().getInt(
							MessageService.INPUT_METHOD_SERVICE) + "���������¼����";
			addNotificaction();
		}
	}

	private void addNotificaction() {

		/***
		 * notification.contentIntent:һ��PendingIntent���󣬵��û������״̬���ϵ�ͼ��ʱ��
		 * ��Intent�ᱻ���� notification.contentView:���ǿ��Բ���״̬����ͼ����Ƿ�һ��view
		 * notification.deleteIntent ����ǰnotification���Ƴ�ʱִ�е�intent
		 * notification.vibrate ���ֻ���ʱ������������
		 */

		// ���������ʾ
		notification.defaults = Notification.DEFAULT_SOUND;
		// audioStreamType��ֵ����AudioManager�е�ֵ�������������ģʽ
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

		// �±ߵ�������ʽ�����������
		// notification.sound =
		// Uri.parse("file:///sdcard/notification/ringer.mp3");
		// notification.sound =
		// Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		Intent intent = new Intent(mContext, Login.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		// ���״̬����ͼ����ֵ���ʾ��Ϣ����
		notification.setLatestEventInfo(mContext, "������ʾ��", "�������������",
				pendingIntent);
		manager.notify(1, notification);

	}
}
