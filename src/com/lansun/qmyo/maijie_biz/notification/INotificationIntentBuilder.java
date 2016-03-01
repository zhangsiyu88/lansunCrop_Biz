package com.lansun.qmyo.maijie_biz.notification;

import android.app.PendingIntent;
import android.content.Context;

/**
 * 
 * @author  Yeun.Zhang
 *
 */
public interface INotificationIntentBuilder {
	public PendingIntent buildIntent(int notifityMode, Context context);
}