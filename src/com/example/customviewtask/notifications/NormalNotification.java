package com.example.customviewtask.notifications;

import com.example.customviewtask.NotificationResultActivity;
import com.example.customviewtask.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class NormalNotification implements INotification {

	@Override
	public Notification createNotification(Context context, String title, String text) {

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

}
