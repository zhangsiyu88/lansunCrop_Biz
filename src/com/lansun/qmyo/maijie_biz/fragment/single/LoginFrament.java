package com.lansun.qmyo.maijie_biz.fragment.single;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.BizCooperationActivity;
import com.lansun.qmyo.maijie_biz.activity.EntryActivity;
import com.lansun.qmyo.maijie_biz.activity.MainActivity;
import com.lansun.qmyo.maijie_biz.fragment.base.HeaderFragment;

public class LoginFrament extends HeaderFragment implements OnClickListener{

	
	public Handler handler = new Handler();
	public boolean juage = true;
	public int images[] = new int[] { R.drawable.cloud_1, 
			R.drawable.cloud_2,
			R.drawable.cloud_1, 
			R.drawable.cloud_2};
	
	private ViewGroup rootView;
	private int RUNNING_DURATION = 15000;
	private int count = 0;
	private ImageView iv_1;
	private ImageView iv_2;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.activity_login, null);
		rootView = contentView;
		initView();
		hostActivity = getActivity();
		return super.onCreateView(inflater, contentView, savedInstanceState);
	}
	
	 @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		show(ResultType.LOADING);
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						show(ResultType.RESULT);						
					}
				});
			}
		}, 100);
	}
	private void initView() {
		iv_1 = (ImageView) rootView.findViewById(R.id.iv_1);
		iv_2 = (ImageView) rootView.findViewById(R.id.iv_2);
		TextView tv_2register = (TextView) rootView.findViewById(R.id.tv_2register);
		TextView btn_2login = (TextView) rootView.findViewById(R.id.btn_2login);
		btn_2login.setOnClickListener(this);
		tv_2register.setOnClickListener(this);
		iv_2.setVisibility(View.INVISIBLE);
		//handler.post(bg_runnable);
	}
	
	/**
	 * 背景滚动 的 线程
	 */
	public Runnable bg_runnable = new Runnable() {

		@Override
		public void run() {
			iv_2.setVisibility(View.VISIBLE);
			TranslateAnimation ta1 = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF,0f, 
					Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, -1f);
			ta1.setDuration(RUNNING_DURATION);
			ta1.setInterpolator(new LinearInterpolator());
			ta1.setFillAfter(true);
			
			TranslateAnimation ta2 = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF, 1f, 
					Animation.RELATIVE_TO_SELF, 0f);
			ta2.setFillAfter(true);
			ta2.setDuration(RUNNING_DURATION);
			ta2.setInterpolator(new LinearInterpolator());
			
			//iamgeView 出去  imageView2 进来
			iv_1.startAnimation(ta1);
			iv_2.startAnimation(ta2);
			iv_1.setBackgroundResource(images[count % 4]);
			count++;
			iv_2.setBackgroundResource(images[count % 4]);
			/*if (juage)
				handler.postDelayed(bg_runnable, RUNNING_DURATION);
			    Toast.makeText(getActivity().getApplicationContext(), "~.~", Toast.LENGTH_SHORT).show();
			Log.i("handler", "handler");*/
		}
	};
	private FragmentActivity hostActivity;


	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected int getMenuResId() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean onKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void onClick(View v) {
		Fragment f;
		switch(v.getId()){
			case R.id.tv_2register:
				if(getActivity().getSupportFragmentManager().getBackStackEntryCount()!=0){
					getActivity().getSupportFragmentManager().popBackStack();
				}
				f = new RegisterFrament();
				((EntryActivity)getActivity()).entrySubFragment(f);
				break;
			case R.id.btn_2login:
				//1.执行登录操作的网络访问
				
				
				//2.若未完成之前注册的门店认领工作,仍然跳转至商业合作页面
				if(true){
					lToast("登陆成功");
					hostActivity.startActivity(new Intent(hostActivity,BizCooperationActivity.class));
					hostActivity.finish();
					break;
				}
				
				//3.登陆成功，执行跳转
				lToast("登陆成功");
				hostActivity.startActivity(new Intent(hostActivity,MainActivity.class));
				hostActivity.finish();
				break;
		default:
		    super.onClick(v);
		    break;
		}
	}
}
