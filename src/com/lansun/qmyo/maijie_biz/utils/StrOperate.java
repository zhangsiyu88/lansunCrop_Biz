package com.lansun.qmyo.maijie_biz.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * 专门针对键值对的列表进行拼接工作
 * 
 * @author  Yeun.Zhang
 *
 */
public class StrOperate {

	public static String getQueryString(List<NameValuePair> QueryParamsList){
        StringBuilder queryString=new StringBuilder();
        for(NameValuePair param:QueryParamsList){
                queryString.append('&');
            queryString.append(param.getName());
            queryString.append('=');
            queryString.append(paramEncode(param.getValue()));
//            queryString.append(param.getValue());
        }
        
        return queryString.toString().substring(1);
    }
	
	
	public static String paramEncode(String paramDecString) {
        if (!hasValue(paramDecString)) {
            return "";
        }
        try {
            return URLEncoder.encode(paramDecString, "UTF-8").replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~")
                    .replace("#", "%23");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
	
	public static boolean hasValue(String str) {
        return (str != null && !"".equals(str));
    }
}
