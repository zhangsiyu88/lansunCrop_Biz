package com.lansun.qmyo.maijie_biz.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lansun.qmyo.maijie_biz.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       
        setContentView(R.layout.activity_splash);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			@Override
			public void run() {
				finish();
				startActivity(new Intent(SplashActivity.this,EntryActivity.class));
			}
		}, 1500);
        super.onCreate(savedInstanceState);
    }
}
