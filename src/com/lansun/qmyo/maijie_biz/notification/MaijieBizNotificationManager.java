package com.lansun.qmyo.maijie_biz.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.app.MaijieApp;
import com.lansun.qmyo.maijie_biz.bean.PushMessage;

/**
 * 下载通知管理器，支持自定义视图
 * @author Yeun.zahng  
 * 
 */
public class MaijieBizNotificationManager {
	public static final String TAG = "NotificationManager";

	private Context mContext;
	private INotificationIntentBuilder mBuilder;
	private NotificationManager mNotificationManager;

	private RemoteViews mRViewSmall;
	private RemoteViews mRViewBig;
	
	public static final int NOTIFICATION_ID_INQUIRY = 0x010702;
	public static final int PENDING_INTENT_GO_MAIN = 0x010705;
	
	private static MaijieBizNotificationManager _instance;
	
	private MaijieBizNotificationManager() {
		mContext = MaijieApp.getAppContext();
		mBuilder = new NotificationIntentBuilderImpl();

		mNotificationManager = ((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE));
		
		mRViewSmall = new RemoteViews(mContext.getPackageName(), R.layout.notification_small);
		mRViewBig = new RemoteViews(mContext.getPackageName(), R.layout.notification_big);
	}

	public static MaijieBizNotificationManager getInstance() {
		if(_instance == null)
			_instance = new MaijieBizNotificationManager();
		return _instance;
	}
	
	
	@SuppressLint("NewApi")
	public void sendInquiryNotification(PushMessage item) {
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
		PendingIntent contentIntent = mBuilder.buildIntent(PENDING_INTENT_GO_MAIN, mContext);
		
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(item.title);
		builder.setContentText(item.content);
		builder.setTicker("迈界商户");
		builder.setContentIntent(contentIntent);
		builder.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE);
//		if (Build.VERSION.SDK_INT >= 11) {
//			mRViewSmall.setTextViewText(R.id.notify_content, item.content);
//			mRViewSmall.setTextViewText(R.id.notify_title, item.title);
//			
////			mRViewSmall.setOnClickPendingIntent(R.id.desk_lrc_control, desklrcIntent);
////			mRViewSmall.setOnClickPendingIntent(R.id.notification_exit_button, exitIntent);
//			
//			builder.setContent(mRViewSmall);
//		}
		
		Notification notification = builder.build();
//
//		if (Build.VERSION.SDK_INT >= 16) {
//			
//			mRViewBig.setTextViewText(R.id.notify_title, item.title);
//			mRViewBig.setTextViewText(R.id.notify_content, item.content);
//			
////			mRViewSmall.setOnClickPendingIntent(R.id.desk_lrc_control, desklrcIntent);
////			mRViewSmall.setOnClickPendingIntent(R.id.notification_exit_button, exitIntent);
//			
//			notification.bigContentView = mRViewBig;
//		}

		try {// notify里面会bad array lengths at
				// android.os.Parcel.readIntArray(Parcel.java:685)
			mNotificationManager.notify(NOTIFICATION_ID_INQUIRY, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancleNotificationByID(int id) {
		mNotificationManager.cancel(id);
	}

//	Notification notification = new Notification();
//
//	if (android.os.Build.VERSION.SDK_INT < 11 || android.os.Build.VERSION.RELEASE.contains("2.3.")) {
//		notification.tickerText = item.title;
//		notification.icon = R.drawable.ic_launcher;
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		notification.contentView = mRViewSmall;
//		notification.contentIntent = mBuilder.buildIntent(PENDING_INTENT_GO_MAIN, mContext);
//
//	} else {
//		Notification.Builder builder = new Notification.Builder(mContext);
//		notification = builder.getNotification();
//		int id = Resources.getSystem().getIdentifier("status_bar_latest_event_content", "id", "android");
//		notification.contentView.removeAllViews(id);
//		if (android.os.Build.VERSION.SDK_INT >= 16)
//			notification.bigContentView = mRViewBig;
//
//		notification.contentView.addView(id, mRViewSmall);
//		notification.contentIntent = mBuilder.buildIntent(PENDING_INTENT_GO_MAIN, mContext);
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		notification.tickerText = "迈界商户";
//		notification.icon = R.drawable.ic_launcher;
//	}
//	notification.defaults |= Notification.DEFAULT_SOUND;
//	
//	mRViewSmall.setTextViewText(R.id.notify_content, item.content);
//	mRViewSmall.setTextViewText(R.id.notify_title, item.title);
//	mRViewBig.setTextViewText(R.id.notify_title, item.title);
//	mRViewBig.setTextViewText(R.id.notify_content, item.content);
//
//	// 3.0以下不支持摁钮点击事件
////	if (android.os.Build.VERSION.SDK_INT >= 11) {
////		Intent intent;
////		if (isDownComplete) {
////			remoteView.setImageViewResource(R.id.install, R.drawable.bg_update_install_selector);
////			bigRemoteView.setImageViewResource(R.id.install, R.drawable.bg_update_install_selector);
////			bigRemoteView.setViewVisibility(R.id.update_tag, View.VISIBLE);
////
////			intent = new Intent(DownloadAppReceiver.DOWNLOAD_INSTALL);
////			intent.putExtra("install", item.appUrl);
////		} else {
////			remoteView.setImageViewResource(R.id.install, R.drawable.bg_update_update_selector);
////			bigRemoteView.setImageViewResource(R.id.install, R.drawable.bg_update_update_selector);
////			bigRemoteView.setViewVisibility(R.id.update_tag, View.GONE);
////			intent = new Intent(DownloadAppReceiver.DOWNLOAD_UPDATE);
////			intent.putExtra("update", item);
////		}
////
////		PendingIntent exitIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////		remoteView.setOnClickPendingIntent(R.id.install, exitIntent);
////		bigRemoteView.setOnClickPendingIntent(R.id.install, exitIntent);
////
////	} else {
////		remoteView.setViewVisibility(R.id.update_control_layout, View.GONE);
////		if (isDownComplete) {
////			remoteView.setTextViewText(R.id.update_state, "点击安装");
////		} else {
////			remoteView.setTextViewText(R.id.update_state, "点击更新");
////		}
////
////	}
	
}
