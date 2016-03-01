package com.lansun.qmyo.maijie_biz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.activity.EntryActivity;
import com.lansun.qmyo.maijie_biz.app.MaijieApp;
import com.lansun.qmyo.maijie_biz.log.FrameLog;

public class SysUtils {
      private static final String TAG = "SysUtils";

      private final static Map<String, ArrayList<String>> neednotCreateShortcurDevices = new HashMap<String, ArrayList<String>>();

      static {
	  ArrayList<String> meizu = new ArrayList<String>();
	  meizu.add("M9");
	  meizu.add("MX");
	  meizu.add("MX3");
	  meizu.add("MX2");
	  neednotCreateShortcurDevices.put("meizu", meizu);
	  ArrayList<String> xiaomi = new ArrayList<String>();
	  xiaomi.add("MI-ONE PLUS");
	  neednotCreateShortcurDevices.put("xiaomi", xiaomi);
      }

      /**
       * 添加桌面图标
       */
      public static void createShortcut(Context context) {
	  PrefsConfig.has_shortcut = true;

	  if (!needCreateShortcut()) {
	        FrameLog.v(TAG, "do not need to create shortcut.");
	        return;
	  }
	  FrameLog.v(TAG, "create shortcut");
	  removeShortcut(context);
	  Intent shortcutIntent = new Intent();
	  shortcutIntent.setAction(Intent.ACTION_MAIN);
	  ComponentName localComponentName = new ComponentName(context.getPackageName(), EntryActivity.class.getName());
	  shortcutIntent.setComponent(localComponentName);

	  Intent sendShortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	  sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
	  sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	  sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
		    Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), R.drawable.ic_launcher));
	  sendShortcutIntent.putExtra("duplicate", false);
	  context.sendBroadcast(sendShortcutIntent);
      }

      public static void removeShortcut(Context context) {
	  Intent shortcutIntent = new Intent();
	  shortcutIntent.setAction(Intent.ACTION_MAIN);
	  ComponentName localComponentName = new ComponentName(context.getPackageName(), EntryActivity.class.getName());
	  shortcutIntent.setComponent(localComponentName);
	  Intent sendShortcutIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
	  sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
	  sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	  context.sendBroadcast(sendShortcutIntent);
      }

      /**
       * 振动的操作
       * @param mContext
       */
      public static void shortVibrator(Context mContext) {
	  long[] pattern = new long[] { 20, 10, 10, 10 };
	  Vibrator mVibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
	  mVibrator.vibrate(pattern, -1);
      }

      /**
       * 是否创建快捷图标 包含在列表中的设备不创建
       * 
       * @return
       */
      public static boolean needCreateShortcut() {
	  ArrayList<String> modelList = neednotCreateShortcurDevices.get(Build.BRAND.toLowerCase());
	  return modelList == null ? true : !modelList.contains(Build.MODEL.toUpperCase());
      }

      // 获取系统的是否是可调试状态
      public static boolean getDebuggable(Context context) {
	  boolean isDebuggable = false;
	  if (context == null) {
	        return isDebuggable;
	  }

	  isDebuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
	  return isDebuggable;
      }

      /**
       * 判断手机状态
       * @return
       */
      public static boolean isPhoneIdle() {
	  TelephonyManager telephonymanager = (TelephonyManager) MaijieApp.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);

	  switch (telephonymanager.getCallState()) {
	  case TelephonyManager.CALL_STATE_IDLE:
	        return true;
	  case TelephonyManager.CALL_STATE_OFFHOOK:
	  case TelephonyManager.CALL_STATE_RINGING:
	        return false;
	  default:
	        return true;
	  }
      }

      /**
       * 根据View的对象进行隐藏键盘的操作
       * @param windowView
       */
      public static void hideKeyboard(View windowView) {
	  if (windowView == null) {
	        return;
	  }
	  //view所在的全局Context
	  Context context = windowView.getContext();
	  InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	  imm.hideSoftInputFromWindow(windowView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
      }

      /**
       * 输入全局的上下文对象
       * @param context
       */
      public static void hideKeyboard(Context context) {
	  if (context == null) {
	        return;
	  }

	  InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	  if (imm.isActive()) {
	        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	  }
      }

      /**
       * 返回当前程序版本号
       */
      public static int getAppVersionCode(Context context) {
	  int versionCode = -1;
	  try {
	        // ---get the package info---
	        PackageManager pm = context.getPackageManager();
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
	        versionCode = pi.versionCode;
	  } catch (Exception e) {
	        FrameLog.e("VersionInfo", e);
	        return -1;
	  }
	  return versionCode;
      }
}
