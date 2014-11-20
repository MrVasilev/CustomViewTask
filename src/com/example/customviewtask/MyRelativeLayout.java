package com.example.customviewtask;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {

	public MyRelativeLayout(Context context) {
		super(context);

	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean result = false;

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			float x = event.getX() - (getChildAt(0).getWidth() / 2);
			float y = event.getY() - (getChildAt(0).getHeight() / 2);

			float childX = getChildAt(0).getX();
			float childY = getChildAt(0).getY();

			ObjectAnimator translationX = ObjectAnimator.ofFloat(getChildAt(0), "translationX", childX, x);
			ObjectAnimator translationY = ObjectAnimator.ofFloat(getChildAt(0), "translationY", childY, y);
			AnimatorSet animatorSet = new AnimatorSet();

			animatorSet.playTogether(translationX, translationY);
			animatorSet.start();

			result = true;

			break;

		default:
			break;
		}

		return result;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		int motionEvent = MotionEventCompat.getActionMasked(event);

		float x = event.getX();
		float y = event.getY();
		float childX = getChildAt(0).getX();
		float childY = getChildAt(0).getY();
		float childRadius = getChildAt(0).getWidth() / 2;
		float childXEnd = getChildAt(0).getX() + (childRadius * 2);
		float childYEnd = getChildAt(0).getY() + (childRadius * 2);
		boolean result = true;

		switch (motionEvent) {
		case MotionEvent.ACTION_DOWN:

			if (x >= childX && x <= childXEnd && y >= childY && y <= childYEnd) {
				result = false;
			} else {
				result = true;
			}

			break;

		case MotionEvent.ACTION_MOVE:

			if (x >= childX && x <= childXEnd && y >= childY && y <= childYEnd) {
				result = false;
			} else {
				result = true;
			}

			break;

		default:
			break;
		}

		return result;
	}

}
