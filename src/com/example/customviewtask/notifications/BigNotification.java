package com.example.customviewtask.notifications;

import com.example.customviewtask.Constants;
import com.example.customviewtask.NotificationResultBackActivity;
import com.example.customviewtask.PingService;
import com.example.customviewtask.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class BigNotification implements INotification {

	@Override
	public Notification createNotification(Context context, String title, String text) {

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
		builder.setStyle(new NotificationCompat.BigTextStyle().bigText((text != null) ? text : ""));
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
