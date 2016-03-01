package com.lansun.qmyo.maijie_biz.uisupport.pullrefresh.internal;

import com.lansun.qmyo.maijie_biz.log.FrameLog;


public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		FrameLog.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

}
