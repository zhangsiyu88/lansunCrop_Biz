package com.lansun.qmyo.maijie_biz.asynctask.download;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.lansun.qmyo.maijie_biz.app.MaijieApp;
import com.lansun.qmyo.maijie_biz.asynctask.download.http.IHttpNotify;
import com.lansun.qmyo.maijie_biz.asynctask.download.http.NetworkError;
import com.lansun.qmyo.maijie_biz.bean.DownloadItem;
import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class CallbackListener implements IHttpNotify{
	private static final String TAG = "DownloadController";
	private long mStartTime = 0l;
	private DownloadItem mItem;
	
	private int notifyType;
	private boolean isShowNotify;
	
	public CallbackListener(DownloadItem item) {
		mItem = item;
		notifyType = 0;// 节目库最热列表 推荐App下载
		isShowNotify = true;
	}
	public CallbackListener(DownloadItem hot, boolean isShowNotify) {
		this(hot);
		notifyType = 1;// 应用升级App下载
		this.isShowNotify = isShowNotify;
	}
	
	@Override
	public void onStart(long totalSize) {
		FrameLog.i(TAG, "onStart totalSize:"+totalSize);
		mStartTime = System.currentTimeMillis();
		mItem.currSize = 0;
		mItem.totleSize = (int) totalSize;
		
	}

	@Override
	public void onProgress(long currSize) {
		mItem.currSize = currSize;
		if(!isShowNotify) 
			return;
		
		
	}

	@Override
	public void onFinished(final String path) {
		long totalTime = System.currentTimeMillis() - mStartTime;
		FrameLog.i(TAG, "下载总时间："+totalTime);
		
//					int cancelID = notifyType == 0 ? DogApp.NOTIFACATION_DOWN_APP_ID : DogApp.NOTIFACATION_UPDATE_APP_ID;
//					NotificationUtils.cancelNotification(cancelID);
					installApk(path);
	}

	@Override
	public void onFail(NetworkError error) {
		
		
	}

	@Override
	public void onCancel(String path, long totalSize, long curSize) {
		
	}
	
	private boolean installApk(String path) {
		if (TextUtils.isEmpty(path)) {		
			return false;
		}
		
		FrameLog.i("下载", "installApk url = " + path);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		if (path.startsWith("file:")) {
			intent.setDataAndType(Uri.parse(path.toString()), "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
		}
		
		MaijieApp.getAppContext().startActivity(intent);
		return true;
	}
	
}
