package com.lansun.qmyo.maijie_biz.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.R;



public class CustomToast {

	/**
	 * 显示Toast
	 * 
	 * Notice： String title
	 * 
	 * @param context
	 * @param root
	 * @param tvString
	 */
	public static void show(Context context, String title, String content) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		tv_title.setText(title);
		tv_content.setText(content);
		
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
	}

	/**
	 * 显示Toast
	 * 
	 * Notice：int title
	 * 
	 * @param context
	 * @param root
	 * @param tvString
	 */
	public static void show(Context context, int title, String content) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		tv_title.setText(title);
		tv_content.setText(content);
		
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
	}

	/*
	 * Notice：int titleId, int contentId
	 */
	public static void show(Context context, int titleId, int contentId) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.custom_toast, null);
		TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
		TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
		
		tv_title.setText(titleId);
		tv_content.setText(contentId);
		
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
	}

}
