package com.example.customviewtask;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private MyCustomView myCustomView;
	private MyRelativeLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainLayout = (MyRelativeLayout) findViewById(R.id.mainLayout);
		myCustomView = (MyCustomView) findViewById(R.id.myCustomView);
		myCustomView.setOnTouchListener(myCustomView);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}
