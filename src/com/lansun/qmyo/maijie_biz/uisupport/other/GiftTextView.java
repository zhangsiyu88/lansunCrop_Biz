package com.lansun.qmyo.maijie_biz.uisupport.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Yeun.zhang
 *
 */
public class GiftTextView extends TextView {

	public GiftTextView(Context context) {
		super(context);
	}

	public GiftTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GiftTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean canScrollHorizontally(int direction) {
		boolean canScroll = super.canScrollHorizontally(direction);
		
		if (canScroll) {
			return false;
		}
		
		return false;
	}	
}
