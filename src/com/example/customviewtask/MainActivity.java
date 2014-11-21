package com.example.customviewtask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private MyCustomView myCustomView;
	private MyRelativeLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainLayout = (MyRelativeLayout) findViewById(R.id.mainLayout);
		myCustomView = (MyCustomView) findViewById(R.id.myCustomView);
		mainLayout.addView(new View(this));
		mainLayout.addView(new View(this));
		mainLayout.addView(new View(this));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}
