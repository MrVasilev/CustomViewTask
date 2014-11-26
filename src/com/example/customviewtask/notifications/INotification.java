package com.example.customviewtask.notifications;

import android.app.Notification;
import android.content.Context;

public interface INotification {
	
	public Notification createNotification(Context context, String title, String text);
}
