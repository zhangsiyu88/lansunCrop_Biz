package com.lansun.qmyo.maijie_biz.log;

import java.io.File;

import android.os.Environment;


public final class FrameLog {
	private static boolean sDebug = true;// 标记是否进行log的打印工作
	private static Logger sLogger = null;
	private final static String LOG_FILENAME = "frame_logcat.log";
	private final static Object obj = new Object();
	public static Logger getLogger() {
		if(sLogger == null){
			synchronized (obj) {
				if(sLogger == null){
					sLogger = Logger.getLogger(Environment.getExternalStorageDirectory().getAbsolutePath() 
							+ File.separator + LOG_FILENAME);		
				}
			}
		}
		return sLogger;
	}
	
	public static synchronized void setDebug(boolean debug) {
		sDebug = debug;
		if (debug && sLogger == null) {
			sLogger = Logger.getLogger(Environment.getExternalStorageDirectory().getAbsolutePath() 
					+ File.separator + LOG_FILENAME);
		}
	}
	
	public static boolean isDebug() {
		return sDebug;
	}
	
	public static synchronized void trace(boolean isOn) {
		if (sLogger == null) {
			sLogger = Logger.getLogger(Environment.getExternalStorageDirectory().getAbsolutePath() 
					+ File.separator + LOG_FILENAME);
		}
		if (isOn) {
			sLogger.traceOn();
		} else {
			sLogger.traceOff();
		}
	}
	
	public static void d(String tag, String msg) {
		if (sDebug) {
			sLogger.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (sDebug) {
			sLogger.i(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (sDebug) {
			sLogger.e(tag, msg);
		}
	}
	
	public static void e(String tag, Throwable e) {
		if (sDebug) {
			sLogger.e(tag, e);
		}
	}

	public static void v(String tag, String msg) {
		if (sDebug) {
			sLogger.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (sDebug) {
			sLogger.w(tag, msg);
		}
	}

	public static void printStackTrace(Exception e) {
		if (sDebug) {
			sLogger.e("KuwoException", e);
		}
	}
}
