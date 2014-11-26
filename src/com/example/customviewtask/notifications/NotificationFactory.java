package com.example.customviewtask.notifications;

import android.app.Notification;
import android.content.Context;

import com.example.customviewtask.Constants;

public class NotificationFactory {

	private Context context;
	private String title;
	private String text;

	public NotificationFactory(Context context, String title, String text) {
		this.context = context;
		this.title = title;
		this.text = text;
	}

	public Notification getNotification(Constants.NotificationStyles notificationStyle) {

		switch (notificationStyle) {

		case NORMAL:

			return new NormalNotification().createNotification(context, title, text);

		case BIG:

			return new BigNotification().createNotification(context, title, text);

		case EXPANDED:

			return new ExpandedNotification().createNotification(context, title, text);

		case WITH_PROGRESS_BAR:

			return new ProgressBarNotification().createNotification(context, title, text);

		default:
			return null;
		}
	}
}
