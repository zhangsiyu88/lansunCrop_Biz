package com.lansun.qmyo.maijie_biz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;

/**
 * 登录和注册的入口
 * 
 * @author  Yeun.Zhang
 *
 */
public class EntryActivity extends Activity {

	public Handler handler = new Handler();
	public boolean juage = true;
	public int images[] = new int[] { R.drawable.cloud_1, 
			R.drawable.cloud_2,
			R.drawable.cloud_1, 
			R.drawable.cloud_2};
	
	
	private int RUNNING_DURATION = 15000;
	private int count = 0;
	private ImageView iv_1;
	private ImageView iv_2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		
		iv_1 = (ImageView) findViewById(R.id.iv_1);
		iv_2 = (ImageView) findViewById(R.id.iv_2);
		iv_2.setVisibility(View.INVISIBLE);
		handler.post(bg_runnable);
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
			if (juage)
				handler.postDelayed(bg_runnable, RUNNING_DURATION);
			    Toast.makeText(getApplicationContext(), "~.~", Toast.LENGTH_SHORT).show();
			Log.i("handler", "handler");
		}
	};
	
}
