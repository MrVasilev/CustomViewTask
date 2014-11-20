package com.example.customviewtask;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private MyCustomView myCustomView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myCustomView = (MyCustomView) findViewById(R.id.myCustomView);
		myCustomView.setOnTouchListener(myCustomView);
	}
}
