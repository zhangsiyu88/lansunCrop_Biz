package com.lansun.qmyo.maijie_biz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.ScrollView;

public class SubListView extends ListView {

	private ScrollView parentScrollView;
	private ListView parentListView;

	public SubListView(Context context) {
		super(context);
	}

	public SubListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SubListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setParentScrollView(ScrollView parentScrollView) {
		this.parentScrollView = parentScrollView;
	}

	public void setParentScrollView(ListView parentListView) {
		this.parentListView = parentListView;
	}

	/**
	 * ！Notice！
	 * 这是MySubListView的核心代码，这里的测量方式决定了 ListView中的子对象完全展示展示出来
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			setParentScrollAble(true);
		case MotionEvent.ACTION_MOVE:
			setParentScrollAble(true);
			break;
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:
			setParentScrollAble(true);
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag) {
		if (parentScrollView != null) {
			parentScrollView.requestDisallowInterceptTouchEvent(!flag);
		}
		if (parentListView != null) {
			parentListView.requestDisallowInterceptTouchEvent(!flag);
		}
	}

	private int maxHeight;

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

}
