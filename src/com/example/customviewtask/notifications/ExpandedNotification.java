package com.example.customviewtask.notifications;

import com.example.customviewtask.NotificationResultBackActivity;
import com.example.customviewtask.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class ExpandedNotification implements INotification {

	@Override
	public Notification createNotification(Context context, String title, String text) {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		Intent intent = new Intent(context, NotificationResultBackActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		builder.setContentTitle((title != null ? title : "")).setContentText((text != null) ? text : "")
				.setSmallIcon(R.drawable.ic_stat_action_receipt).setContentIntent(pendingIntent).setAutoCancel(true);

		// Create InboxStyle
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

		String[] events = { "First Item", "Second Item", "Another Item", "Move, move, move...", "Do not push me!",
				"This is the last item from all details..." };

		// Sets a title for the Inbox in expanded layout
		inboxStyle.setBigContentTitle("Event tracker details : ");

		// Moves events into the expanded layout
		for (int i = 0; i < events.length; i++) {
			inboxStyle.addLine(events[i]);
		}

		// Moves the expanded layout object into the notification object.
		builder.setStyle(inboxStyle);

		/**
		 * If we want to add BigImage into Notification we should use this code
		 * 
		 * NotificationCompat.BigPictureStyle bigPictureStyle = new
		 * NotificationCompat.BigPictureStyle(); Bitmap imageBitmap =
		 * BitmapFactory.decodeResource(context.getResources(),
		 * R.drawable.notification_image);
		 * bigPictureStyle.bigPicture(imageBitmap);
		 * 
		 */

		return builder.build();
	}

}
