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
			notification.tickerText = "你有新任务 "
					+ i.getExtras().getInt(
							MessageService.INPUT_METHOD_SERVICE) + "条；点击登录处理";
			addNotificaction();
		}
	}

	private void addNotificaction() {

		/***
		 * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
		 * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
		 * notification.deleteIntent 当当前notification被移除时执行的intent
		 * notification.vibrate 当手机震动时，震动周期设置
		 */

		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		// audioStreamType的值必须AudioManager中的值，代表着响铃的模式
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

		// 下边的两个方式可以添加音乐
		// notification.sound =
		// Uri.parse("file:///sdcard/notification/ringer.mp3");
		// notification.sound =
		// Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		Intent intent = new Intent(mContext, Login.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		// 点击状态栏的图标出现的提示信息设置
		notification.setLatestEventInfo(mContext, "内容提示：", "标段新任务提醒",
				pendingIntent);
		manager.notify(1, notification);

	}
}
