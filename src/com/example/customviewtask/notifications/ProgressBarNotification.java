package com.example.customviewtask.notifications;

import com.example.customviewtask.Constants;
import com.example.customviewtask.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ProgressBarNotification implements INotification {

	@Override
	public Notification createNotification(Context context, String title, String text) {

		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		builder.setContentTitle((title != null) ? title : "").setContentText((text != null) ? text : "")
				.setSmallIcon(R.drawable.ic_stat_action_get_app);

		// Start a lengthy operation in a background thread
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i <= 100; i += 5) {

					// Set progress in Notification. If you do not know how long
					// is the process, replace this row with
					// "builder.setProgress(0, 0, true);"
					builder.setProgress(100, i, false);

					// Display the ProgressBar for the first time
					notificationManager.notify(Constants.PROGRESS_BAR_NOTIFICATION_ID, builder.build());

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
		});

		thread.start();

		return null;
	}

}
