package com.example.customviewtask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.customviewtask.Constants.NotificationStyles;

public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager notificationManager;
	private Notification notification;

	@Override
	public void onReceive(Context context, Intent intent) {

		notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

		String notificationTitle = "Repeating notification";
		String notificationText = "This is a test notification.";
		String notificationBigText = "This notification was send from AlarmManager which send notifications in every 2 minutes. And this should be some long long text and so on...";
		Constants.NotificationStyles style = (NotificationStyles) intent.getSerializableExtra("notification_type");

		switch (style) {

		case NORMAL:
			notification = createNormalNotification(context, notificationTitle, notificationText);
			notificationManager.notify(Constants.NORMAL_NOTIFICATION_ID, notification);
			break;

		case BIG:
			notification = createBigNotification(context, notificationTitle, notificationText, notificationBigText);
			notificationManager.notify(Constants.BIG_NOTIFICATION_ID, notification);
			break;

		case EXPANDED_LAYOUT:
			notification = createExpandedNotification(context);
			notificationManager.notify(Constants.EXPANDED_NOTIFICATION_ID, notification);
			break;

		case WITH_PROGRESS_BAR:
			createNotificationWithProgressBar(context);
			break;

		default:
			break;
		}
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

	private void createNotificationWithProgressBar(Context context) {

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		builder.setContentTitle("Pictures Download").setContentText("Download in progress")
				.setSmallIcon(R.drawable.ic_stat_action_get_app);

		// Start a lengthy operation in a background thread
		new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i <= 100; i += 5) {

					// Set progress in Notification. If you do not know how long
					// is the process, replace this row with
					// "builder.setProgress(0, 0, true);"
					builder.setProgress(100, i, false);

					// Display the ProgressBar for the first time
					notificationManager.notify(Constants.NORMAL_NOTIFICATION_ID, builder.build());

					// Sleeps the thread, simulating an operation
					// that takes time
					try {
						// Sleep for 1 seconds
						Thread.sleep(1 * 1000);

					} catch (InterruptedException e) {
						Log.d("TAG", "Sleep failure!");
					}
				}

				// Removes the progress bar
				builder.setProgress(0, 0, false);
				// When loop ends, update notification
				builder.setContentText("Download complete");

				notificationManager.notify(Constants.PROGRESS_BAR_NOTIFICATION_ID, builder.build());
			}
		}).start();
	}

	private Notification createExpandedNotification(Context context) {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		builder.setContentTitle("Event tracker").setContentText("Event received")
				.setSmallIcon(R.drawable.ic_stat_action_receipt);

		//Create InboxStyle
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
		 * NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
		 * Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_image);	
		 * bigPictureStyle.bigPicture(imageBitmap);
		 * 
		 */

		return builder.build();
	}

}
