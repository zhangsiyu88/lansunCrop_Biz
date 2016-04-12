package com.lansun.qmyo.maijie_biz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.eventbus.EventBus;
import com.lansun.qmyo.maijie_biz.fragment.single.BizInfoFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.BizUserInfoFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.ClaimStoreFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.UploadVerifyMaterialFragment;
import com.lansun.qmyo.maijie_biz.fragment.single.VerifyMaterialFragment;

/**
 * 注册完成后的跳转页
 * 
 * @author  Yeun.Zhang
 *
 */
public class BizCooperationActivity extends BaseFragmentActivity implements OnClickListener{



	private TextView tv_pelase_claim_store;
	private TextView tv_go2homepage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bizcoorperation);
		initView();
		//将当前的Activity塞入事件总线中，获取到
		EventBus.getDefault().register(this);
	}

	private void initView() {
		frame_id  = R.id.biz_frame;
		frameView = (FrameLayout) findViewById(frame_id);
		RelativeLayout rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
		RelativeLayout rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
		RelativeLayout rl_3 = (RelativeLayout) findViewById(R.id.rl_3);
		RelativeLayout rl_4 = (RelativeLayout) findViewById(R.id.rl_4);
		tv_go2homepage = (TextView) findViewById(R.id.tv_go2homepage);
		
		tv_pelase_claim_store = (TextView) findViewById(R.id.tv_pelase_claim_store);
		
		
		rl_1.setOnClickListener(this);
		rl_2.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		rl_4.setOnClickListener(this);
		tv_go2homepage.setOnClickListener(this);
	}
	
	

	@Override
	public void onClick(View v) {
		Fragment f;
		switch(v.getId()){
		case R.id.rl_1:
			 f = new ClaimStoreFragment();
			 entrySubFragment(f);
			break;
		case R.id.rl_2:
			f = new BizInfoFragment();
			entrySubFragment(f);
			break;
		case R.id.rl_3:
			f = new BizUserInfoFragment();
			entrySubFragment(f);
			break;
		case R.id.rl_4:
			/*f = new UploadVerifyMaterialFragment();
			entrySubFragment(f);*/
			startActivity(new Intent(this,UploadVerifyMaterialActivity.class));
			break;
		case R.id.rl_5:
			f = new VerifyMaterialFragment();
			entrySubFragment(f);
			break;
		case R.id.tv_go2homepage:
			finish();
			startActivity(new Intent(this,MainActivity.class));
			break;
		}
	}
	
	public void onEventMainThread(Object item) {  
		Toast.makeText(this, "拿到EventBus的内容", Toast.LENGTH_LONG).show();
		tv_pelase_claim_store.setText((String)item);
	}
	
}
