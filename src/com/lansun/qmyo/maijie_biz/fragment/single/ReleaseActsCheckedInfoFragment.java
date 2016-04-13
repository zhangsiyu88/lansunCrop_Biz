package com.lansun.qmyo.maijie_biz.fragment.single;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.umeng.analytics.MobclickAgent;

public class ReleaseActsCheckedInfoFragment extends HeaderFragment implements OnClickListener {
	

	private ViewGroup rootView;
	private Button bt_nextstep;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_releaseactsinfo_checked, null);
		rootView = contentView;
		initView();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	private void initView() {
		bt_nextstep = (Button) rootView.findViewById(R.id.bt_nextstep);
		bt_nextstep.setOnClickListener(this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}

	@Override
	protected String getTitle() {
		return getString(R.string.about_us);
	}

	@Override
	protected int getMenuResId() {
		return -1;
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("");
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd(""); 
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_nextstep:
			Fragment f = new ActivityPreShowFragment();
			((MainActivity)getActivity()).entrySubFragment(f);
			break;
		default:
			break;
		}		
		super.onClick(v);
	}

}
