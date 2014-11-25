package com.example.customviewtask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.customviewtask.Constants.NotificationStyles;

public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager notificationManager;
	private Notification notification;

	@Override
	public void onReceive(Context context, Intent intent) {

		String notificationTitle = "Repeating notification";
		String notificationText = "This is a test notification.";
		String notificationBigText = "This notification was send from AlarmManager which send notifications in every 2 minutes. And this should be some long long text and so on...";
		Constants.NotificationStyles style = (NotificationStyles) intent.getSerializableExtra("notification_type");

		switch (style) {

		case NORMAL:
			notification = createNormalNotification(context, notificationTitle, notificationText);
			break;

		case BIG:
			notification = createBigNotification(context, notificationTitle, notificationText, notificationBigText);
			break;

		default:
			break;
		}

		notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

		notificationManager.notify(Constants.NOTIFICATION_ID, notification);
	}

	private Notification createNormalNotification(Context context, String title, String text) {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		builder.setSmallIcon(R.drawable.ic_stat_action_android);
		builder.setContentTitle((title != null) ? title : "");
		builder.setContentText((text != null) ? text : "");
		builder.setSound(soundUri);
		builder.setAutoCancel(true);

		// Creates an Intent for the Activity
		Intent notifyIntent = new Intent(context, NotificationResultActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentIntent(pendingIntent);

		return builder.build();
	}

	private Notification createBigNotification(Context context, String title, String text, String bigText) {

		Intent dismissIntent = new Intent(context, PingService.class);
		dismissIntent.setAction(Constants.ACTION_DISMISS);
		PendingIntent dismissPendingIntent = PendingIntent.getService(context, 0, dismissIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Intent snoozeIntent = new Intent(context, PingService.class);
		snoozeIntent.setAction(Constants.ACTION_SNOOZE);
		PendingIntent snoozePendingIntent = PendingIntent.getService(context, 0, snoozeIntent, 0);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		builder.setSmallIcon(R.drawable.ic_stat_action_android);
		builder.setContentTitle((title != null) ? title : "");
		builder.setContentText((text != null) ? text : "");
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setStyle(new NotificationCompat.BigTextStyle().bigText((bigText != null) ? bigText : ""));
		builder.addAction(R.drawable.ic_stat_dismiss, context.getString(R.string.dismiss_label), dismissPendingIntent);
		builder.addAction(R.drawable.ic_stat_snooze, context.getString(R.string.snooze_label), snoozePendingIntent);
		builder.setAutoCancel(true);

		// Creates an Intent for the Activity
		Intent notifyIntent = new Intent(context, NotificationResultBackActivity.class);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentIntent(pendingIntent);

		return builder.build();
	}

}
