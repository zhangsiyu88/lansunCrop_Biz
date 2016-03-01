package com.lansun.qmyo.maijie_biz.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

public class TArrayList extends ArrayList<NameValuePair> {
	
	private static final long serialVersionUID = 1L;

    @Override
    public boolean add(NameValuePair nameValuePair) {
    	return super.add(nameValuePair);
//        if(StrOperate.hasValue(nameValuePair.getValue())){
//            return super.add(nameValuePair);
//        }else{
//            return false;
//        }
    }
}
