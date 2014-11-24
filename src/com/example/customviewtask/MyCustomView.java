package com.example.customviewtask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View {

	private Paint paint;
	private Paint paintText;
	private String coordinates;
	private float radius;
	private int currentWidth;
	private int currentHeight;

	public MyCustomView(Context context) {
		super(context);
		init();
	}

	public MyCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		checkAttrs(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		setCurrentWidth(widthMeasureSpec);
		setCurrentHeight(heightMeasureSpec);
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
		this.radius = Math.min(getHeight(), getWidth()) / 2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		coordinates = getX() + " : " + getY();

		canvas.drawCircle(radius, radius, radius, paint);

		canvas.drawText(coordinates, radius, radius, paintText);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		if (radius > 0)
			this.radius = radius;
	}

	public int getCurrentWidth() {
		return currentWidth;
	}

	public void setCurrentWidth(int currentWidth) {
		this.currentWidth = currentWidth;
	}

	public int getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(int currentHeight) {
		this.currentHeight = currentHeight;
	}
}
