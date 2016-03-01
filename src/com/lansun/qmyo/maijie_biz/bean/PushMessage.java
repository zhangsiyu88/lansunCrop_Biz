package com.lansun.qmyo.maijie_biz.bean;

import org.json.JSONObject;

public class PushMessage extends Result {
	private static final long serialVersionUID = 5855811573724778679L;
	
	
	
	public String content;



	@Override
	public boolean parseJsonObj(JSONObject jsonObj) {
		return false;
		
	}
	
	
}
