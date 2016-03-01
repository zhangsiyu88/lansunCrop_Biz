package com.lansun.qmyo.maijie_biz.asynctask.http;

import com.lansun.qmyo.maijie_biz.volley.Response.ErrorListener;
import com.lansun.qmyo.maijie_biz.volley.Response.Listener;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.StringRequest;

public class StringTask extends StringRequest {

	public StringTask(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	
	public StringTask(String url, Listener<String> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
    }
	
	
}
