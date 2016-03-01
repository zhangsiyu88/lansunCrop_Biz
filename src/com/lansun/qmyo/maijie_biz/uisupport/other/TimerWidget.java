package com.lansun.qmyo.maijie_biz.uisupport.other;

import java.text.NumberFormat;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lansun.qmyo.maijie_biz.R;

public class TimerWidget extends LinearLayout {

      /** 显示小时的View */
      protected TextView hourText = null;
      /** 显示冒号的view. */
      protected TextView colonView1 = null;
      /** 显示分钟的View */
      protected TextView minitText = null;
      /** 显示冒号的view. */
      protected TextView colonView2 = null;
      /** 显示秒钟View. */
      protected TextView secondText = null;
      /** 布局ID. */
      /** 全局的LayoutInflater对象，已经完成初始化. */
      public LayoutInflater mInflater;
      /**
       * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
       */
      public LinearLayout.LayoutParams layoutParamsFF = null;
      /**
       * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
       */
      public LinearLayout.LayoutParams layoutParamsFW = null;
      /**
       * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
       */
      public LinearLayout.LayoutParams layoutParamsWF = null;
      /**
       * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
       */
      public LinearLayout.LayoutParams layoutParamsWW = null;

      public TimerWidget(Context context) {
	  super(context);
	  initTimerWidget(context);
      }

      public TimerWidget(Context context, AttributeSet attrs) {
	  super(context, attrs);
	  initTimerWidget(context);
      }

      /**
       * Inner TextView
       * 
       * @param context
       *              the context
       */
      public void initTimerWidget(Context context) {

	  // 水平排列
	  this.setOrientation(LinearLayout.HORIZONTAL);

	  mInflater = LayoutInflater.from(context);
	  layoutParamsFF = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	  layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	  layoutParamsWF = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	  layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	  layoutParamsWW.gravity = Gravity.CENTER_VERTICAL;
	  // layoutParamsWW.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

	  hourText = new TextView(context);
	  hourText.setBackgroundResource(R.drawable.shape_black_square);
	  hourText.setWidth(dip2px(context, 20));
	  hourText.setHeight(dip2px(context, 20));
	  hourText.setTextColor(Color.WHITE);
	  hourText.setGravity(Gravity.CENTER);

	  colonView1 = new TextView(context);
	  colonView1.setTextColor(Color.BLACK);
	  colonView1.setText(":");
	  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		    LinearLayout.LayoutParams.WRAP_CONTENT);
	  lp.setMargins(dip2px(context, 3), 0, dip2px(context, 3), 0);
	  colonView1.setLayoutParams(lp);

	  minitText = new TextView(context);
	  minitText.setBackgroundResource(R.drawable.shape_black_square);
	  minitText.setWidth(dip2px(context, 20));
	  minitText.setHeight(dip2px(context, 20));
	  minitText.setTextColor(Color.WHITE);
	  minitText.setGravity(Gravity.CENTER);

	  colonView2 = new TextView(context);
	  colonView2.setTextColor(Color.BLACK);
	  colonView2.setText(":");
	  LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		    LinearLayout.LayoutParams.WRAP_CONTENT);
	  lp1.setMargins(dip2px(context, 3), 0, dip2px(context, 3), 0);
	  colonView1.setLayoutParams(lp1);

	  secondText = new TextView(context);
	  secondText.setBackgroundResource(R.drawable.shape_black_square);
	  secondText.setWidth(dip2px(context, 20));
	  secondText.setHeight(dip2px(context, 20));
	  secondText.setTextColor(Color.WHITE);
	  secondText.setGravity(Gravity.CENTER);

	  this.addView(hourText, layoutParamsWW);
	  this.addView(colonView1, layoutParamsWW);
	  this.addView(minitText, layoutParamsWW);
	  this.addView(colonView2, layoutParamsWW);
	  this.addView(secondText, layoutParamsWW);
      }

      private CountDownTimer downTimer;

      /**
       * 设置倒计时
       */
      public void setTime(long longTime) {

	  if (downTimer != null) {
	        downTimer.cancel();
	  }
	  downTimer = new CountDownTimer(longTime * 1000, 1000) {
	        public void onTick(long millisUntilFinished) {
		    long hour = millisUntilFinished / (1 * 60 * 60 * 1000) % 24;
		    long minute = millisUntilFinished / (1 * 60 * 1000) % 60;
		    long second = millisUntilFinished / (1 * 1000) % 60;
		    hourText.setText(formatTime(hour));
		    minitText.setText(formatTime(minute));
		    secondText.setText(formatTime(second));
	        }

	        public void onFinish() {
		    hourText.setText("00");
		    minitText.setText("00");
		    secondText.setText("00");
	        }
	  };

	  downTimer.start();
      }

      /**
       * 个位数格式化
       * 
       * @param n
       * @return
       */
      private String formatTime(long n) {
	  NumberFormat formatter = NumberFormat.getNumberInstance();
	  formatter.setMinimumIntegerDigits(2);
	  formatter.setGroupingUsed(false);
	  return formatter.format(n);
      }

      /**
       * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
       */
      public static int dip2px(Context context, float dpValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (dpValue * scale + 0.5f);
      }

}
