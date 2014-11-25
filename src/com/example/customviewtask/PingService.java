package com.example.customviewtask;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class PingService extends IntentService {

	private Handler handler;

	public PingService() {
		super(Constants.PING_SERVICE_NAME);
	}

	public PingService(String name) {
		super(name);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handler = new Handler();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		String action = intent.getAction();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		if (action.equals(Constants.ACTION_DISMISS)) {

			handler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), getString(R.string.dismissed_notification_toast),
							Toast.LENGTH_SHORT).show();
				}
			});

			notificationManager.cancel(Constants.NOTIFICATION_ID);

		} else if (action.equals(Constants.ACTION_SNOOZE)) {

			handler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), getString(R.string.snoozed_notification_toast),
							Toast.LENGTH_SHORT).show();
				}
			});

			notificationManager.cancel(Constants.NOTIFICATION_ID);
		}

	}

}
