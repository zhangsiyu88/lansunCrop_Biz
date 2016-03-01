package com.lansun.qmyo.maijie_biz.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.lansun.qmyo.maijie_biz.activity.WelcomeActivity;


/**
 * 
 * @author  Yeun.Zhang
 *
 */
public class NotificationIntentBuilderImpl implements INotificationIntentBuilder {

	@Override
	public PendingIntent buildIntent(int notifiyMode, Context context) {
		Intent intent = null;
		switch (notifiyMode) {
		case MaijieBizNotificationManager.PENDING_INTENT_GO_MAIN:
			intent = new Intent(context, WelcomeActivity.class);
			intent.putExtra(PushDefine.PUSH_PARAM_TYPE, -1);
			return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		return null;
	}

}
