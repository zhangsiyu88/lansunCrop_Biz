package com.lansun.qmyo.maijie_biz.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 针对键盘的 公用方法
 * 
 * @author  Yeun.Zhang
 *
 */
public class InputWindowUtils {

	
	public static InputWindowUtils _instance;
	
	public static InputWindowUtils getInstance(){
		if(_instance==null){
			_instance = new InputWindowUtils();
		}
		return _instance;
	}
	
	/**
	 * 当前界面 自动适应，自然当键盘弹起时，会将布局重新排列，造成控件位置变形
	 * 但适合 将底部的控件展示出来
	 * @param act
	 */
	public static void InputAdjustResize(Activity act){
		act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	
	/**
	 * 界面设置成 不受键盘 弹起的 影响
	 * @param act
	 */
	public static void InputNo(Activity act){
		act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	
	/**
	 * 设置软件输入的服务类型  控制键盘隐藏
	 * @param act
	 */
	public static void InputHideBySer(Activity act){
		act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	/**
	 * 控制软键盘  隐藏 
	 * @param act
	 * @param v
	 */
	public static void SetInputModeHide(Activity act,View v){
		InputMethodManager imm = (InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0); 
	}
	
	
	
	
	/**
	 * 使软键盘进行相反的 弹出与收回操作
	 * 收回-->弹出
	 * 弹出-->收回
	 * @param act
	 */
	public static void SetInputModeHide(Activity act){
		InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	

	
	

	/*AndroidManifest.xml文件中界面对应的<activity>里加入
	android:windowSoftInputMode="adjustPan"   键盘就会覆盖屏幕                 
	android:windowSoftInputMode="stateVisible|adjustResize"   屏幕整体上移：Resize而且 stateVisible*/
	
	
	
	
	//	activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
	//	| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
	
	
	/**
	* 该方法：(InputMethodManager) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	* 通过获取到的软键盘管理器 操作着 软键盘本身的显示和隐藏
	* 实质上和 
	* activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);方法去操作界面的效果是一致
	* getWindow().setSoftInputMode是通过设置针对输入法展示情况的模式属性，来直接地影响着 界面上的控件，但又间接地影响着 软键盘的展示
	*/
	//InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	//imm.hideSoftInputFromWindow(lv_home_list.getWindowToken(), 0); 
	
}
