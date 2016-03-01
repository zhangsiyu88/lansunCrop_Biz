package com.lansun.qmyo.maijie_biz.uisupport.other;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lansun.qmyo.maijie_biz.app.MaijieApp;

public class UiUtil {
	
	/**
	 * Toast工具类
	 * @param msg  需要打印的信息
	 */
	public static void sToast(String msg) {
		Toast.makeText(MaijieApp.getAppContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 判断网络是否可用
	 * 
	 * @param bHint
	 *            是否弹出提示标志位
	 * @return 网络是否可用
	 */
	public static boolean isNetAvailable() {
		boolean flag = false;
		ConnectivityManager cwjManager = (ConnectivityManager) MaijieApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cwjManager.getActiveNetworkInfo();
		if (cwjManager != null && networkInfo != null)
			flag = networkInfo.isAvailable();
//		if (!flag && bHint)
//			Toast.makeText(DogApp.mContext, "无可用网络", Toast.LENGTH_LONG).show();
		return flag;
	}
	
	public static boolean checkPassword(String pwd) {
    	if(TextUtils.isEmpty(pwd))
    		return false;
    	
    	int length = pwd.length();
    	if(length < 6 || length > 12) {
    		return false;
    	}
    	return true;
    }
    
    public static boolean checkName(String name) {
    	if(TextUtils.isEmpty(name))
    		return false;
    	
    	int length = name.length();
    	if(length < 2 || length > 10) {
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * 获取json中某个字段的值
     * @param response
     * @param tag
     * @return
     */
    public static String getJsonDataByTag(String response, String tag) {
		String result = "";
		try {
			JSONObject jsObj = new JSONObject(response);
			result = jsObj.optString(tag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
    /**
	 * 隐藏软键盘
	 * @param context 上下文
	 */
	public static void closeKeyboard(Activity context) {
		try {
			InputMethodManager m = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (m.isActive()) {
				IBinder iBinder = context.getCurrentFocus().getWindowToken();
				if(iBinder != null)
					m.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 弹出软键盘
	 * @param context 上下文
	 */
	public static void openKeyboard(Activity context) {
		try {
			InputMethodManager m = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			
			//键盘如果是非弹出的状态，在检测状态后弹出
			if (!m.isActive()) {
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
