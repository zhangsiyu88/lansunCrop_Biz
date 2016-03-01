package com.lansun.qmyo.maijie_biz.pageindicator;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class TabsAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	private final Context mContext;
	private final ArrayList<PageTab> mTabs = new ArrayList<PageTab>();

	public class PageTab {
		private final Class<?> clss;
		private int iconId;;
		public String tag;
		public String title;
		public Bundle bundle;
		public Fragment f;
		
		PageTab(Class<?> clss){
			this.clss = clss;
		}
		PageTab(String _tag, String _title, Class<?> _class, int _iconId, Bundle _bundle) {
			clss = _class;
			iconId = _iconId;
			tag = _tag;
			title = _title;
			bundle = _bundle;
			f = null;
		}

		public int getIconId() {
			return iconId;
		}

		public void setIconId(int idonId) {
			this.iconId = idonId;
		}

		public Bundle getBundle() {
			return bundle;
		}
		public void setBundle(Bundle bundle) {
			this.bundle = bundle;
		}
		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		public Class<?> getClss() {
			return clss;
		}
		
	}

	public TabsAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		mContext = activity;
	}

	public void init() {
		mTabs.clear();
	}
	
	public void addTab(String tag, String title, Class<?> clss, int iconId, Bundle bundle) {
		mTabs.add(new PageTab(tag, title, clss, iconId, bundle));
	}
	
	public void addTab(String tag, String title, Class<?> clss, int iconId) {
		mTabs.add(new PageTab(tag, title, clss, iconId, null));
	}
	public void addTab(PageTab tab){
		mTabs.add(tab);
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (mTabs.size() == 0) {
			return null;
		}
		return mTabs.get(position % mTabs.size()).title;
	}

	@Override
	public Fragment getItem(int position) {
		PageTab info = mTabs.get(position);
		
		// 防止在getItem时，重复调用
		if (info.f == null) {
			info.f = Fragment.instantiate(mContext, info.clss.getName(), info.bundle);	
		}
		
		return info.f;
	}
	
	public Fragment getFragment(int position){
		if (position >= mTabs.size() || position < 0) {
			return null;
		}
		PageTab info = mTabs.get(position);
		if(info != null)
			return info.f;
		return null;
	}
	
	public void Resume(){
		for(PageTab info: mTabs){
			if(info != null && info.f!= null && info.f.isAdded()){
				info.f.onResume();
			}
		}
	}
	
	public void Pause(){
		for(PageTab info: mTabs){
			if(info != null && info.f!= null && info.f.isAdded()){
				info.f.onPause();
			}
		}
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override
	public int getIconResId(int index) {
		return 0;
	}

	@Override
	public Drawable getIconDrawable(int index) {
		return mContext.getResources().getDrawable(mTabs.get(index).iconId);
	}

}
