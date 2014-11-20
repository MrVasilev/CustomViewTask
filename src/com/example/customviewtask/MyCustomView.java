package com.example.customviewtask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyCustomView extends View implements OnTouchListener {

	private Paint paint;
	private Paint paintText;
	private String coordinates;
	private float radius;
	private float touchX;
	private float touchY;
	private int screenWidth;
	private int screenHeight;

	public MyCustomView(Context context) {
		super(context);
		init();
		getScreenMetrics(context);
	}

	public MyCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		getScreenMetrics(context);
		checkAttrs(context, attrs);
	}

	// Check for available attributes
	private void checkAttrs(Context context, AttributeSet attrs) {

		TypedArray attrsArr = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);

		final int allAttrsNum = attrsArr.getIndexCount();

		for (int i = 0; i < allAttrsNum; i++) {

			int attr = attrsArr.getIndex(i);

			switch (attr) {

			case R.styleable.MyCustomView_shape_color:

				int color = attrsArr.getColor(attr, Color.BLUE);
				paint.setColor(color);

				break;

			case R.styleable.MyCustomView_text_color:

				int textColor = attrsArr.getColor(attr, Color.BLUE);
				paintText.setColor(textColor);

				break;
			}
		}

		attrsArr.recycle();
	}

	public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

		this.paint = new Paint();
		this.paintText = new Paint();
		this.paint.setColor(Color.BLUE);
		this.paint.setStyle(Style.FILL);
		this.paint.setTextAlign(Align.CENTER);
		this.paintText.setColor(Color.WHITE);
		this.paintText.setStyle(Style.FILL);
		this.paintText.setTextAlign(Align.CENTER);
		this.paintText.setTextSize(30f);
	}

	private void getScreenMetrics(Context context) {

		if (context != null) {
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			screenWidth = displayMetrics.widthPixels;
			screenHeight = displayMetrics.heightPixels;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		radius = Math.min(getHeight(), getWidth()) / 2;

		coordinates = getX() + " : " + getY();

		canvas.drawCircle(radius, radius, radius, paint);

		canvas.drawText(coordinates, radius, radius, paintText);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean result = true;

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			touchX = event.getX();
			touchY = event.getY();

			invalidate();

			result = true;

			break;

		case MotionEvent.ACTION_MOVE:

			calculateCoordinates(event);

			result = true;
			break;

		default:
			break;
		}

		return result;
	}

	private void calculateCoordinates(MotionEvent event) {

		float eventX = event.getX();
		float eventY = event.getY();

		float x = getX();
		float y = getY();

		float resultX = (eventX - touchX) + x;
		float resultY = (eventY - touchY) + y;
		float resultXEnd = resultX + (radius * 2);
		float resultYEnd = resultY + (radius * 2);

		if (resultX >= 0 && resultY >= 0 && resultXEnd <= screenWidth && resultYEnd <= screenHeight) {

			// Set the new coordinates of the CustomView
			this.layout((int) resultX, (int) resultY, (int) resultXEnd, (int) resultYEnd);

			invalidate();
		}
	}
}
