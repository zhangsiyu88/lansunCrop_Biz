package com.lansun.qmyo.maijie_biz.wheeldialog.wheeldate;

import java.text.SimpleDateFormat;

public class JudgeDate {

	/**
	 * 
	 * @param str_input
	 * @param rDateFormat
	 * @return
	 */
	public static  boolean isDate(String str_input,String rDateFormat){
		if (!isNull(str_input)) {
	         SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
	         formatter.setLenient(false);
	         try {
	             formatter.format(formatter.parse(str_input));
	         } catch (Exception e) {
	             return false;
	         }
	         return true;
	     }
		return false;
	}
	public static boolean isNull(String str){
		if(str==null)
			return true;
		else
			return false;
	}
}