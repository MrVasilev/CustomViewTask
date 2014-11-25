package com.example.customviewtask;

public class Constants {

	public static final int NORMAL_NOTIFICATION_ID = 0;
	public static final int BIG_NOTIFICATION_ID = 1;
	public static final int PROGRESS_BAR_NOTIFICATION_ID = 2;
	public static final int EXPANDED_NOTIFICATION_ID = 3;

	public static final String ACTION_DISMISS = "com.example.customviewtask.ACTION_DISMISS";
	public static final String ACTION_SNOOZE = "com.example.customviewtask.ACTION_SNOOZE";
	public static final String ACTION_ANSWER_RECEIVER = "com.example.customviewtask.ACTION_ANSWER_RECEIVER";

	public static final String PING_SERVICE_NAME = "com.example.customviewtask.PING_SERVICE_NAME";

	public static enum NotificationStyles {
		NORMAL, BIG, WITH_PROGRESS_BAR, EXPANDED_LAYOUT, CUSTOM_LAYOUT
	};

}
