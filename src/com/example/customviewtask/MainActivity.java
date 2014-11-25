package com.example.customviewtask;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	// private MyCustomView myCustomView;
	// private MyRelativeLayout mainLayout;

	private AlarmManager alarmManager;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// mainLayout = (MyRelativeLayout) findViewById(R.id.mainLayout);
		// myCustomView = (MyCustomView) findViewById(R.id.myCustomView);
	}

	@Override
	protected void onResume() {
		super.onResume();

		setRepeatNotification();
	}

	/**
	 * Set repeating Alarm which in every 2 minutes send message to
	 * AlarmReceiver (BroadcastReceiver) and then it create and send
	 * Notification.
	 */
	private void setRepeatNotification() {

		int intervalSeconds = 120 * 1000;

		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		Intent intent = new Intent(this, AlarmReceiver.class);

		intent.putExtra("notification_type", Constants.NotificationStyles.WITH_PROGRESS_BAR);
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), intervalSeconds,
				pendingIntent);
	}

	@Override
	protected void onPause() {
		super.onPause();

		alarmManager.cancel(pendingIntent);
	}
}
