package com.lansun.qmyo.maijie_biz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.LoginFrament;
import com.lansun.qmyo.maijie_biz.fragment.single.RegisterFrament;

/**
 * 登录和注册的入口
 * 
 * @author  Yeun.Zhang
 *
 */
public class EntryActivity extends BaseFragmentActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_or_login);
		initView();
	}

	private void initView() {
		TextView btn_register = (TextView) findViewById(R.id.btn_register);
		TextView btn_login = (TextView) findViewById(R.id.btn_login);
		frame_id = R.id.entry_frame;
		frameView = (FrameLayout) findViewById(frame_id);
		btn_login.setOnClickListener((OnClickListener) this);
		btn_register.setOnClickListener((OnClickListener) this);
		
	}
	
	

	@Override
	public void onClick(View v) {
		Fragment f;
		switch(v.getId()){
		case R.id.btn_register:
			 f = new RegisterFrament();
			entrySubFragment(f);
			break;
		case R.id.btn_login:
			f = new LoginFrament();
			entrySubFragment(f);
			break;
		}
	}
	
	
//	public void entrySubFragment(Fragment fragment) {
//
//		if (fragment != null && fragment instanceof HeaderFragment) {
//			String tag = fragment.getClass().getSimpleName();
//
//			mFrameViews.setVisibility(View.VISIBLE);
//			FragmentManager fragmentManager = getSupportFragmentManager();
//			FragmentTransaction transaction = fragmentManager.beginTransaction();
//			//transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_right, R.anim.slide_out_from_right);
//			transaction.add(R.id.entry_frame, fragment, tag);
//			transaction.addToBackStack(tag); // 添加回退栈
//			transaction.commitAllowingStateLoss();
//		}
//	}
}
