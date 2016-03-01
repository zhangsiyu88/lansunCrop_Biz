package com.lansun.qmyo.maijie_biz.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 正则工具类
 * 
 * @author anqi
 */
public class RegularUtils {

	/**
	 * 首字母是不是点
	 */
	public static boolean isFirstDot(String str) {
		try {
			return str.trim().startsWith(".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 用于用户名、昵称的规则检测
	 * 
	 * @param str
	 * @return
	 */
	public static boolean commonFormat(String str) {
		String regEx = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// return m.matches();
		if (m.find()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 *  验证手机号是否正确
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if(TextUtils.isEmpty(phone))
			return false;
		
		Pattern pattern = Pattern.compile("^(13|14|15|18|17)\\d{9}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		
		return false;
	}

	/**
		 * 验证输入的邮箱格式是否符合
		 * 
		 * @param email
		 * @return 是否合法
		 */
		public static boolean emailFormat(String email) {
			int space = email.indexOf(" ");
			if(space > -1) {// 
				return false;
			}
			
			int index = email.indexOf("@");
			if(index > 0) {
				String [] arrStr = email.split("@");
				if(arrStr.length != 2) {
					return false;
				}
				
				int dot = arrStr[1].indexOf(".");
				int strlength = arrStr[1].length();
				
				if((dot > 0) && (dot != strlength-1)) {
					
	//				boolean tag = true;
					String pattern1 = "^([a-z0-9A-Z]+[-_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	//				String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
					Pattern pattern = Pattern.compile(pattern1);
					Matcher mat = pattern.matcher(email);
					return mat.matches();
	//				if (!mat.find()) {
	//					tag = false;
	//				}
	//				return tag;
				}
			}
			return false;
		}

	public static boolean isVaildEmail(String email) {
		// ^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean result = matcher.matches();
		return result;
	}

	/**
	 * 判断字符串是否以数字开头
	 * @param str
	 * @return
	 */
	public static boolean isStartWithNum(String str) {
		int chr = str.charAt(0);
		if(chr<48 || chr>57) {
			return false;
		} 
		return true; 
	}

}
