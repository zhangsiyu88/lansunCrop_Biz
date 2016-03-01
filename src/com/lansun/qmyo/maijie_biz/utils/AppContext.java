package com.lansun.qmyo.maijie_biz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.lansun.qmyo.maijie_biz.dirmanager.MaijieBizDirContext;
import com.lansun.qmyo.maijie_biz.dirmanager.DirectoryManager;
import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class AppContext {

	private static String TAG = "AppContext";
	public static final String APP_NAME = "DaDaCar";

	public static String DEVICE_IMEI;

	public static String DEVICE_MAC_ADDR;

	public static int VERSION_CODE;

	public static String VERSION_NAME;
	
	public static int WIDTH;

	public static int HEIGHT;

	public static float DENSITY;

	public static int DENSITY_DPI;

	public static float SCALED_DENSITY;
	
	public static int IMAG_CACHE_SIZE = 0;

	private static boolean inited = false;
	private static DirectoryManager dirManager = null;

	public synchronized static boolean init(Activity context) {

		if (inited) {
			return true;
		}

		try {
			if (!initFileSystem()) {
				return false;
			}
			initScreenInfo(context);
			String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			String macAddr = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();

			DEVICE_IMEI = imei;
			DEVICE_MAC_ADDR = macAddr;
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			VERSION_CODE = pi.versionCode;
			VERSION_NAME = pi.versionName;
			
//			int mMemClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
//			if (mMemClass > 0) {
//				IMAG_CACHE_SIZE = 1024 * 1024 * mMemClass / 8;
//			}
			
//			context.startService(new Intent(context, TraceService.class));
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		inited = true;
		return true;
	}

	public synchronized static boolean initFileSystem() {
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			FrameLog.v(TAG, "initFileSystem:" + Environment.getExternalStorageState());
			
			return false;
		}
		if (dirManager == null)
			dirManager = new DirectoryManager(new MaijieBizDirContext(APP_NAME));
		return dirManager.createAll();
	}
	
	public static void initScreenInfo(Activity activity) {
		if (activity == null) return;
		if (WIDTH == 0) {
			try {
				DisplayMetrics dm = new DisplayMetrics();
				activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
				WIDTH = Math.min(dm.widthPixels, dm.heightPixels);
				HEIGHT = Math.max(dm.widthPixels, dm.heightPixels);
				DENSITY = dm.density;
				
				DENSITY_DPI = dm.densityDpi;
				SCALED_DENSITY = dm.scaledDensity;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static DirectoryManager getDirManager() {
		if (dirManager == null)
			initFileSystem();
		return dirManager;
	}
}
