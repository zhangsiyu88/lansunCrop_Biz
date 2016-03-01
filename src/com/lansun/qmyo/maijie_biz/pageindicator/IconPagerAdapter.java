package com.lansun.qmyo.maijie_biz.pageindicator;

import android.graphics.drawable.Drawable;

public interface IconPagerAdapter {
	/**
     * Get icon representing the page at {@code index} in the adapter.
     */
    Drawable getIconDrawable(int index);

    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);
    
    // From PagerAdapter
    int getCount();
}
