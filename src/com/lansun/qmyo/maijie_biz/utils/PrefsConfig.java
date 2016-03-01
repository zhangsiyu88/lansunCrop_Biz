package com.lansun.qmyo.maijie_biz.utils;

import android.content.Context;

import com.lansun.qmyo.maijie_biz.app.MaijieApp;
import com.lansun.qmyo.maijie_biz.bean.User;

public class PrefsConfig {
	private static String TAG = "Config";

	/**
	 * 是否已创建快捷方式的标志.
	 */
	public static boolean has_shortcut;
	/**
	 * 是否显示Guide界面
	 */
	public static boolean has_show_guide;
	public static boolean login_status;
	/**
	 * CID
	 */
	public static String u_cid;
	/**
	 * 用户ID
	 */
	public static String u_id;
	/**
	 * 用户名称
	 */
	public static String u_name;
	/**
	 * 密码
	 */
	public static String u_passwd;
	/**
	 * 用户电话 
	 */
	public static String u_phonenum;
	/**
	 * 性别
	 */
	public static String u_sex;
	/**
	 * 经销商ID
	 */
	public static String u_dealerId;
	/**
	 * 经销商
	 */
	public static String u_dealer;
	/**
	 * 地区
	 */
	public static String u_city;
	
	public static String u_attNum;
	public static String u_dealerType;
	public static String u_forbiddenEndTime;
	public static String u_grade;
	//三张图片
	//头像
	public static String u_imageUrl;
	//名片
	public static String u_identity_card_pic;
	//身份证
	public static String u_business_card_pic;
	
	public static String u_inquiryPushNum;
	public static String u_status;
	public static String u_stepNum;
	public static String u_succeRushNum;
	public static String u_topNum;
	public static String u_job;
	
	public static String u_brandName;
//	public static String u_brandImgUrl;
	
	public static void loadFromPref(Context context) {
		has_shortcut = PrefsUtils.loadPrefBoolean(context, "has_shortcut", false);
		has_show_guide = PrefsUtils.loadPrefBoolean(context, "has_show_guide", true);
		login_status = PrefsUtils.loadPrefBoolean(context, "login_status", false);
		
		u_cid = PrefsUtils.loadPrefString(context, "u_cid");
		u_id = PrefsUtils.loadPrefString(context, "u_id", "");
		u_name = PrefsUtils.loadPrefString(context, "u_name", "");
		u_passwd = PrefsUtils.loadPrefString(context, "u_passwd");
		u_phonenum = PrefsUtils.loadPrefString(context, "u_phonenum", "");
		u_sex = PrefsUtils.loadPrefString(context, "u_sex", "");
		u_dealerId = PrefsUtils.loadPrefString(context, "u_dealerId", "");
		u_dealer = PrefsUtils.loadPrefString(context, "u_dealer", "");
		u_city = PrefsUtils.loadPrefString(context, "u_city", "");
		
		u_attNum = PrefsUtils.loadPrefString(context, "u_attNum", "");
		u_dealerType = PrefsUtils.loadPrefString(context, "u_dealerType", "");
		u_forbiddenEndTime = PrefsUtils.loadPrefString(context, "u_forbiddenEndTime", "");
		u_grade = PrefsUtils.loadPrefString(context, "u_grade", "");
		
		u_imageUrl = PrefsUtils.loadPrefString(context, "u_imageUrl", "");
		u_identity_card_pic = PrefsUtils.loadPrefString(context, "u_identity_card_pic", "");
		u_business_card_pic = PrefsUtils.loadPrefString(context, "u_business_card_pic", "");
		
		u_inquiryPushNum = PrefsUtils.loadPrefString(context, "u_inquiryPushNum", "");
		u_status = PrefsUtils.loadPrefString(context, "u_status", "");
		u_stepNum = PrefsUtils.loadPrefString(context, "u_stepNum", "");
		u_succeRushNum = PrefsUtils.loadPrefString(context, "u_succeRushNum", "");
		u_topNum = PrefsUtils.loadPrefString(context, "u_topNum", "");
		u_brandName = PrefsUtils.loadPrefString(context, "u_brandName", "");
//		u_brandImgUrl = PrefsUtils.loadPrefString(context, "u_brandImgUrl", "");
		u_job = PrefsUtils.loadPrefString(context, "u_job", "");
	}

	public static void saveToPref(Context context) {
		PrefsUtils.savePrefBoolean(context, "has_shortcut", has_shortcut);
		PrefsUtils.savePrefBoolean(context, "has_show_guide", has_show_guide);
		PrefsUtils.savePrefBoolean(context, "login_status", login_status);
		
		PrefsUtils.savePrefString(context, "u_cid", u_cid);
		PrefsUtils.savePrefString(context, "u_id", u_id);
		PrefsUtils.savePrefString(context, "u_name", u_name);
		PrefsUtils.savePrefString(context, "u_passwd", u_passwd);
		PrefsUtils.savePrefString(context, "u_phonenum", u_phonenum);
		PrefsUtils.savePrefString(context, "u_sex", u_sex);
		PrefsUtils.savePrefString(context, "u_dealerId", u_dealerId);
		PrefsUtils.savePrefString(context, "u_dealer", u_dealer);
		PrefsUtils.savePrefString(context, "u_city", u_city);
		
		
		PrefsUtils.savePrefString(context, "u_attNum", u_attNum);
		PrefsUtils.savePrefString(context, "u_dealerType", u_dealerType);
		PrefsUtils.savePrefString(context, "u_forbiddenEndTime", u_forbiddenEndTime);
		PrefsUtils.savePrefString(context, "u_grade", u_grade);
		
		PrefsUtils.savePrefString(context, "u_imageUrl", u_imageUrl);
		PrefsUtils.savePrefString(context, "u_identity_card_pic", u_identity_card_pic);
		PrefsUtils.savePrefString(context, "u_business_card_pic", u_business_card_pic);
		
		PrefsUtils.savePrefString(context, "u_inquiryPushNum", u_inquiryPushNum);
		PrefsUtils.savePrefString(context, "u_status", u_status);
		PrefsUtils.savePrefString(context, "u_stepNum", u_stepNum);
		PrefsUtils.savePrefString(context, "u_succeRushNum", u_succeRushNum);
		PrefsUtils.savePrefString(context, "u_topNum", u_topNum);
		
		PrefsUtils.savePrefString(context, "u_brandName", u_brandName);
//		PrefsUtils.savePrefString(context, "u_brandImgUrl", u_brandImgUrl);
		PrefsUtils.savePrefString(context, "u_job", u_job);
	}
	
	public static void saveUser(User user) {
//		u_cid = user.u_cid;
		u_id = user.u_id;
		u_name = user.u_name;
		u_passwd = user.u_passwd;
		u_phonenum = user.u_phonenum;
		u_sex = user.u_sex;
		u_dealerId = user.u_dealerId;
		u_dealer = user.u_dealer;
		u_city = user.u_city;
		
		u_attNum = user.u_attNum;
		u_dealerType = user.u_dealerType;
		u_forbiddenEndTime = user.u_forbiddenEndTime;
		u_grade = user.u_grade;
		//图片
		u_imageUrl = user.u_imageUrl;
		u_identity_card_pic=user.u_identity_card_pic;
		u_business_card_pic=user.u_business_card_pic;
		
		u_inquiryPushNum = user.u_inquiryPushNum;
		u_status = user.u_status;
		u_stepNum = user.u_stepNum;
		u_succeRushNum = user.u_succeRushNum;
		u_topNum = user.u_topNum;
		u_brandName = user.u_brandName;
		u_job = user.u_job;
		login_status = true;
		
		//将上述的参数送入到Pref中
		saveToPref(MaijieApp.getAppContext());
	}
	
	public static void logout() {
		u_id = "";
		u_name = "";
		u_passwd = "";
		u_phonenum = "";
		u_sex = "";
		u_dealerId = "";
		u_dealer = "";
		u_city = "";
		u_attNum = "";
		u_dealerType = "";
		u_forbiddenEndTime = "";
		u_grade = "";
		u_imageUrl = "";
		u_business_card_pic="";
		u_identity_card_pic="";
		u_inquiryPushNum = "";
		u_status = "";
		u_stepNum = "";
		u_succeRushNum = "";
		u_topNum = "";
		u_brandName = "";
//		u_brandImgUrl = "";
		u_job = "";
		login_status = false;
	}

}
