package com.lansun.qmyo.maijie_biz.ui.fragment.qrcode;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pc.ioc.event.EventBus;
import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.bean.EventItem;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;

public class QrResultFragment extends HeaderFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.qr_result_layout, null);
		

		return super.onCreateView(inflater, contentView, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	protected int getMenuResId() {
		return -1;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().post(new EventItem(EventItem.ID_BACK_TO_QR));
	}
	
	@Override
	public boolean onKeyDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			close();
			return true;
		default:
			return false;
		}
	}

}
