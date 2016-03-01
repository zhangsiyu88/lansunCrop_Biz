package com.lansun.qmyo.maijie_biz.uisupport.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterView extends View {
	
	private String[] letterList;
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	
	private int choose;
	private Paint paint;
	boolean showBkg;

	public LetterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LetterView(Context context) {
		super(context);
		init();
	}

	private void init() {
		letterList = new String[] { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		choose = -1;
		paint = new Paint();
		showBkg = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / letterList.length;
		for (int i = 0; i < letterList.length; i++) {
			paint.setTextSize(30);
			paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(letterList[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(letterList[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * letterList.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < letterList.length) {
					listener.onTouchingLetterChanged(letterList[c]);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < letterList.length) {
					listener.onTouchingLetterChanged(letterList[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener listener) {
		onTouchingLetterChangedListener = listener;
	}

	public interface OnTouchingLetterChangedListener {
		public abstract void onTouchingLetterChanged(String s);
	}

}
