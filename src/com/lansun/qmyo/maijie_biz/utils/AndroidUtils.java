package com.lansun.qmyo.maijie_biz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.lansun.qmyo.maijie_biz.app.MaijieApp;

public class AndroidUtils {
	private static final String TAG = "androidUtils";
	private static WindowManager windowManager;
	private static float density;
	private static DisplayMetrics displayMetrics;

	private AndroidUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 调用application的全局变量
	 * 
	 * @return
	 */
	public static Context getContext() {
		return MaijieApp.getsContext();
	}

	public static Handler getHandler() {
		return MaijieApp.getsHandler();
	}

	public static Thread getMainThread() {
		return MaijieApp.getsMainThread();
	}

	public static int getMainThreadId() {
		return MaijieApp.getsMainThreadId();
	}

	/**
	 * 获取xml的资源文件转化成对象
	 */
	public static String getStringById(int resId) {
		return getContext().getResources().getString(resId);
	}

	public static String[] getStringsByid(int resId) {
		return getContext().getResources().getStringArray(resId);
	}

	public static ColorStateList getColorStateListById(int resId) {
		return getContext().getResources().getColorStateList(resId);
	}

	public static Drawable getDrawableById(int resId) {
		return getContext().getResources().getDrawable(resId);

	}

	/**
	 * 屏幕适配工具
	 * @param dip
	 * @return
	 */
	public static int dip2px(int dip) {
		if (displayMetrics == null) {
			displayMetrics = getContext().getResources().getDisplayMetrics();
		}
		density = displayMetrics.density;
		return (int) (dip * density + 0.5);

	}

	public static int px2dip(int px) {
		if (displayMetrics == null) {
			displayMetrics = getContext().getResources().getDisplayMetrics();
		}
		density = displayMetrics.density;
		return (int) (px / density + 0.5);

	}

	/**
	 * 返回屏幕的宽高 px
	 * @return
	 */
	public static int getDispalyWidthPx() {
		if (windowManager == null) {
			windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager.getDefaultDisplay().getWidth();
	}

	public static int getDispalyHeighPx() {
		if (windowManager == null) {
			windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager.getDefaultDisplay().getHeight();
	}

	/**
	 * 返回屏幕的宽度 dp
	 * @return
	 */
	public static int getDispalyWidthDip() {
		if (displayMetrics == null) {
			displayMetrics = getContext().getResources().getDisplayMetrics();
		}
		density = displayMetrics.density;
		return (int) (getDispalyWidthPx() / density);
	}

	public static int getDispalyHeigtDip() {
		if (displayMetrics == null) {
			displayMetrics = getContext().getResources().getDisplayMetrics();
		}
		density = displayMetrics.density;
		return (int) (getDispalyHeighPx() / density);
	}

	/**
	 * 判断当前是否在主线程中运行
	 * @return
	 */
	public static boolean isRunningInMainThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}

	/**
	 * 确保ui操作时在主线程中
	 * 
	 * @param runnable
	 */
	public static void RunningInMainThread(Runnable runnable) {
		if (isRunningInMainThread()) {
			runnable.run();

		} else {
			getHandler().post(runnable);
		}

	}

	/**
	 * xml-->View
	 * @param layout
	 * @return
	 */
	public static View inflate(int layout) {
		return View.inflate(getContext(), layout, null);

	}

	/**
	 * 格式化文件尺寸
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		return Formatter.formatFileSize(getContext(), size);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(Long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		try {

			return sdf.format(time);

		} catch (Exception e) {
			Log.e(TAG, "格式化时间异常,你确定是是输入毫秒时间吗? ");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按照固定的格式解析时间<b>"yyyy-MM-dd-HH-mm-ss"<b> 发生异常的时候我们返回0,因为时间是不可能等于0的,
	 * 所以接收的时候要进行判断
	 * 
	 * @param time
	 * @return
	 */
	public static long parseTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		try {
			return sdf.parse(time).getTime();
		} catch (ParseException e) {
			Log.e(TAG, "解析时间异常,你确认你输入的时间格式是这种(yyyy-MM-dd-HH-mm-ss)格式吗?");
			e.printStackTrace();
		}
		return 0;
	}

	public static Resources getResources() {
		return getContext().getResources();
	}

	public static int getColor(int id) {
		return getResources().getColor(id);
	}

	/**
	 * 获取状态栏的高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 
	 * * 利用反射获得状态栏的高度 * *
	 * 
	 * @param context
	 *            *
	 * @return
	 * */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}
	/**
	 * 按条件的到随机颜色
	 * @param alpha
	 * @param begin
	 * @param last
	 * @return
	 */

	public static int getRandomArgb(int alpha, int begin, int last) {
		if (last > 255) {
			last = 255;
		}
		int red = begin + new Random().nextInt(last - begin);
		int green = begin + new Random().nextInt(last - begin);
		int blue = begin + new Random().nextInt(last - begin);
		return Color.argb(alpha, red, green, blue);
	}

	/**
	 * 根据颜色和圆角
	 * @param rgb
	 * @param r
	 * @return
	 */
	public static Drawable drawBitmap(int rgb, int r) {
		GradientDrawable gradientDrawable = new GradientDrawable();
		// 设置绘画图片色值
		gradientDrawable.setColor(rgb);
		// 绘画的是矩形
		gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
		// 设置矩形的圆角半径
		gradientDrawable.setCornerRadius(r);

		return gradientDrawable;
	}

	// 创建图片选择器
	public static StateListDrawable getStateListDrawable(Drawable pressDrawable, Drawable normalDrawable) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_pressed }, pressDrawable);
		stateListDrawable.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
		stateListDrawable.addState(new int[] {}, normalDrawable);
		return stateListDrawable;
	}

	/**
	 * 得到随机背景色并且带选择器, 并且可以设置圆角
	 * 
	 * @param alpha
	 * @param begin
	 * @param last
	 * @param cornerRadius
	 * @return
	 */
	public static StateListDrawable getRandomDrawable(int alpha, int begin, int last, int cornerRadius) {
		int color = 0xffcecece;
		return getStateListDrawable(drawBitmap(color, cornerRadius), drawBitmap(getRandomArgb(alpha, begin, last), cornerRadius));

	}

	/**
	 * 延时执行任务
	 * 
	 * @param runnable
	 * @param delayTime
	 */

	public static void postDelayed(Runnable runnable, long delayTime) {
		getHandler().postDelayed(runnable, delayTime);
	}

	/**
	 * 移除指定的任务
	 * 
	 * @param runnable
	 */
	public static void removeCallback(Runnable runnable) {
		// 移除在当前handler中维护的任务(传递进来的任务)
		getHandler().removeCallbacks(runnable);

	}

	/**
	 * 移除所有的任务和消息 一般要界面的销毁中吊销
	 */
	public static void removeAllCallbacksAndMessages() {
		getHandler().removeCallbacksAndMessages(null);
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
	/**
	 * Drawable选择器
	 * @param pressedArgb
	 * @param normalArgb
	 * @return
	 */
	public static StateListDrawable getDrawable(int pressedArgb, int normalArgb) {

		return getStateListDrawable(drawBitmap(pressedArgb, 0), drawBitmap(pressedArgb, 0));
	}

	/**
	 * 颜色选择器
	 * @param pressedArgb
	 * @param normalArgb
	 * @return
	 */
	public static ColorStateList getColor(int pressedArgb, int normalArgb) {

		ColorStateList colorStateList = new ColorStateList(new int[][] { { android.R.attr.state_enabled, android.R.attr.state_pressed }, { android.R.attr.state_enabled } }, new int[] { pressedArgb, normalArgb });

		return colorStateList;
	}

}
