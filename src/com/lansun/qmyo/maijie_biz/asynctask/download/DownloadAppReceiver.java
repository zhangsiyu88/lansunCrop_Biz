package com.lansun.qmyo.maijie_biz.asynctask.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class DownloadAppReceiver extends BroadcastReceiver {
	public static final String DOWNLOAD_CANCEL = "com.edog.hot.download.app";
	public static final String DOWNLOAD_INSTALL = "com.edog.hot.update.install";
	public static final String DOWNLOAD_UPDATE = "com.edog.hot.update.download";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent == null) {
			return;
		}
		
		if(intent.getAction().equals(DOWNLOAD_CANCEL)) {
			String url = intent.getStringExtra("url");
			if(!TextUtils.isEmpty(url)) {
				AppDownloader.getInstance().cancelDownloadTask(url);
			}
		}
		
//		if(intent.getAction().equals(DOWNLOAD_INSTALL)) {
//			NotificationUtils.cancelNotification(DogApp.NOTIFACATION_UPDATE_APP_ID);
//			UiUtil.collapseStatusBar(context);
//			
//			final String url = intent.getStringExtra("install");
//			if(!TextUtils.isEmpty(url)) {
//				DogApp.handler.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						String filePath = FileUtil.getFilePathByUrl(url, Constants.PATh_FILE_DOWNLOAD_APP) + ".apk";
//						installApk(filePath);
//					}
//				});
//				
//			}
//		}
//		
//		if(intent.getAction().equals(DOWNLOAD_UPDATE)) {
//			NotificationUtils.cancelNotification(DogApp.NOTIFACATION_UPDATE_APP_ID);
//			
//			Hot item = (Hot) intent.getSerializableExtra("update");
//			if(item != null) {
//				AppDownloader.getInstance().addUpdateDownloadTask(item, true);
//			}
//		}
	}
	
//	private boolean installApk(String path) {
//		if (TextUtils.isEmpty(path)) {		
//			return false;
//		}
//		
//		Log.i("下载", "installApk url = " + path);
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		
//		if (path.startsWith("file:")) {
//			intent.setDataAndType(Uri.parse(path.toString()), "application/vnd.android.package-archive");
//		} else {
//			intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
//		}
//		
//		DogApp.mContext.startActivity(intent);
//		return true;
//	}
	
}
