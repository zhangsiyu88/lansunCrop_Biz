package com.lansun.qmyo.maijie_biz.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.BaseFragment;

public class HelpFragment extends BaseFragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.helpcenter_fragment, null);
		return super.onCreateView(inflater, contentView, savedInstanceState);		
	}
	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getRMenuIvResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	protected int getRMenuTvResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	protected int getLMenuTvResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	protected int getLMenuIvResId() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	protected void getNetData(boolean needLoading) {
		// TODO Auto-generated method stub

	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
