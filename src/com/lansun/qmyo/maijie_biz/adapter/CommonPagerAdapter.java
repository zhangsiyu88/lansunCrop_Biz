package com.lansun.qmyo.maijie_biz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用viewpage适配器
 */
public class CommonPagerAdapter extends PagerAdapter {

	protected LayoutInflater inflater;
	protected Context context;
	protected HashMap<Integer, View> pageMap = new HashMap<Integer, View>();

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		if (pageMap.containsKey(position)) {
			pageMap.remove(position);
			/*
			 * 通过打印Log我们可以发现，这里的destroyItem方法当图片画出当前照片的左1和右1 的范围时，当前方法就会被执行
			 */
			Log.e("删除View","删除啦啦啦！");
			//注意下面的view对象的类型是ViewGroup，即为此中的ViewPager对象
			view.removeView(pageMap.get(position));
			
			
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
