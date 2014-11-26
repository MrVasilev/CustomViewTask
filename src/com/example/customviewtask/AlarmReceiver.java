package com.example.customviewtask;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.customviewtask.Constants.NotificationStyles;
import com.example.customviewtask.notifications.NotificationFactory;

public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager notificationManager;
	private Notification notification;

	@Override
	public void onReceive(Context context, Intent intent) {

		notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

		String notificationTitle = "";
		String notificationText = "";

		Constants.NotificationStyles style = (NotificationStyles) intent.getSerializableExtra("notification_type");

		switch (style) {

		case NORMAL:
			notificationTitle = context.getString(R.string.normal_notify_title);
			notificationText = context.getString(R.string.normal_notify_text);
			break;

		case BIG:
			notificationTitle = context.getString(R.string.big_notify_title);
			notificationText = context.getString(R.string.big_notify_text);
			break;

		case EXPANDED:
			notificationTitle = context.getString(R.string.extanded_notify_title);
			notificationText = context.getString(R.string.extanded_notify_text);
			break;

		case WITH_PROGRESS_BAR:
			notificationTitle = context.getString(R.string.progress_bar_notify_title);
			notificationText = context.getString(R.string.progress_bar_notify_text);
			break;

		default:
			break;
		}

		NotificationFactory notificationFactory = new NotificationFactory(context, notificationTitle, notificationText);

		notification = notificationFactory.getNotification(style);

		if (notification != null)
			notificationManager.notify(Constants.NOTIFICATION_ID, notification);
	}
}
