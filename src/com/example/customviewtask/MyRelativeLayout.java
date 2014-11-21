package com.example.customviewtask;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {

	private boolean isTouchIn;
	private float touchX;
	private float touchY;
	private int screenWidth;
	private int screenHeight;

	public MyRelativeLayout(Context context) {
		super(context);
		getScreenMetrics(context);

	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		getScreenMetrics(context);

	}

	/**
	 * Get the metrics of device's display
	 * 
	 * @param context
	 */
	private void getScreenMetrics(Context context) {

		if (context != null) {
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			screenWidth = displayMetrics.widthPixels;
			screenHeight = displayMetrics.heightPixels;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean result = false;

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			isTouchIn = isTapCustomViewInside(event);

			if (isTouchIn) {

				float childX = getChildAt(0).getX();
				float childY = getChildAt(0).getY();

				touchX = event.getX() - childX;
				touchY = event.getY() - childY;

			} else {

				moveViewWithAnim(event);
			}

			result = true;

			break;

		case MotionEvent.ACTION_MOVE:

			if (isTouchIn) {

				calculateCoordinates(event);
			}

			result = true;

			break;

		default:
			break;
		}

		return result;
	}

	/**
	 * If user tap outside of the CustomView, move the view to that coordinates
	 * with animation
	 * 
	 * @param event
	 */
	private void moveViewWithAnim(MotionEvent event) {

		int size = getChildAt(0).getHeight();

		int childX = (int) getChildAt(0).getX();
		int childY = (int) getChildAt(0).getY();

		int x = (int) event.getX() - (getChildAt(0).getWidth() / 2);
		int y = (int) event.getY() - (getChildAt(0).getHeight() / 2);

		int childXEnd = childX + size;
		int childYEnd = childY + size;

		int xEnd = x + size;
		int yEnd = y + size;

		ObjectAnimator translationX = ObjectAnimator.ofInt(getChildAt(0), "Left", childX, x);
		ObjectAnimator translationY = ObjectAnimator.ofInt(getChildAt(0), "Top", childY, y);
		ObjectAnimator translationX2 = ObjectAnimator.ofInt(getChildAt(0), "Right", childXEnd, xEnd);
		ObjectAnimator translationY2 = ObjectAnimator.ofInt(getChildAt(0), "Bottom", childYEnd, yEnd);

		translationX.start();
		translationY.start();
		translationX2.start();
		translationY2.start();
	}

	/**
	 * Calculate new coordinates of the custom view
	 * 
	 * @param event
	 */
	private void calculateCoordinates(MotionEvent event) {

		float eventX = event.getX();
		float eventY = event.getY();

		float radius = getChildAt(0).getWidth() / 2;
		float resultX = eventX - touchX;
		float resultY = eventY - touchY;
		float resultXEnd = resultX + (radius * 2);
		float resultYEnd = resultY + (radius * 2);

		if (resultX >= 0 && resultY >= 0 && resultXEnd <= screenWidth && resultYEnd <= screenHeight) {

			// Set the new coordinates of the CustomView
			getChildAt(0).layout((int) resultX, (int) resultY, (int) resultXEnd, (int) resultYEnd);

			getChildAt(0).invalidate();
		} else {
			int a = 5;
		}
	}

	/**
	 * Check if user tap inside of the CustomView or not
	 * 
	 * @param event
	 * @return
	 */
	private boolean isTapCustomViewInside(MotionEvent event) {

		if (event != null) {

			float x = event.getX();
			float y = event.getY();
			float childX = getChildAt(0).getX();
			float childY = getChildAt(0).getY();
			float childRadius = getChildAt(0).getWidth() / 2;
			float childXEnd = getChildAt(0).getX() + (childRadius * 2);
			float childYEnd = getChildAt(0).getY() + (childRadius * 2);

			if (x >= childX && x <= childXEnd && y >= childY && y <= childYEnd) {
				return true;
			}
		}

		return false;
	}
}
