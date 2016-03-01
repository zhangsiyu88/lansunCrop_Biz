package com.lansun.qmyo.maijie_biz.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.pc.ioc.event.EventBus;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.entity.CloseEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;



/**
 * 大图滚动pagerAdapter
 * 
 * @author bhxx
 * 
 */
public class DetailHeaderPagerAdapter extends CommonPagerAdapter implements
		Serializable {
	protected LayoutInflater inflater;
	private ArrayList<String> photos;
	private DisplayImageOptions options;
	

	public DetailHeaderPagerAdapter(Context context, ArrayList<String> photos) {
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.photos = photos;
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}



	@Override
	public int getCount() {
		if (photos != null) {
			return photos.size();
		}
		return 0;
	}

	public Object instantiateItem(ViewGroup view, int position) {
		
		View convertView = inflater.inflate(R.layout.store_detail_head_item,null);
		
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		if (photos.get(position).contains("http")) {
			ImageLoader.getInstance().displayImage(
					photos.get(position) + "?imageView2/4/w/" + dm.widthPixels
							+ "/format/jpg", (ImageView) convertView, options);
		} else {
			ImageLoader.getInstance().displayImage(photos.get(position),(ImageView) convertView, options);
		}
		
			convertView.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View arg0) {
					CloseEntity event = new CloseEntity();
					event.setClose(true);
					
					//当convertView被点击的时候，该图片所在的fragment会消失掉
					//这个event会被 注册了监听者的ImageGalleryDialog拦截并且执行了关闭操作
					EventBus.getDefault().post(event);
				}
			});
		//pageMap的就是用来存储view对象，便于使viewgroup对象在执行删除时能够找到要删除的view，具体可见DetailHeaderPagerAdapter的父类CommonPagerAdapter
		pageMap.put(position, convertView);
		//每当我们实现一次instantiateItem，那么这个viewgroup对象都会将生成的新的复用对象放入进来
		view.addView(convertView);
		/*
		 * 通过打印Log我们可以发现，这里的instantiateItem方法一上来就会执行三遍即当前显示的图片和左边的图片、右边的图片（如果左、右有图片的话，否则只执行两边）
		 */
		Log.e("Tag", "总共实行了多少次的addView操作！");
		return convertView;
	}

	private int mChildCount = 0;

	@Override
	public void notifyDataSetChanged() {
		mChildCount = getCount();
		super.notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		if (mChildCount > 0) {
			mChildCount--;
			return POSITION_NONE;
		}
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View collection, int position, Object o) {
		((ViewGroup) collection).removeView((View) o);
	}
}
