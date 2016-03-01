package com.lansun.qmyo.maijie_biz.asynctask.http;

import android.content.Context;

import com.lansun.qmyo.maijie_biz.volley.Request;
import com.lansun.qmyo.maijie_biz.volley.RequestQueue;
import com.lansun.qmyo.maijie_biz.volleyvolley.toolbox.Volley;

/**
 * 主请求队列
 * @author lruijun
 * @date 2015.06.04
 */
public class MainRequestQueue {

	private Context mContext;
	private RequestQueue mRequestQueue;
	
	private static MainRequestQueue mInstance = null;
	
	/** 获取主请求的单例对象 */
	public static synchronized MainRequestQueue getInstance(Context context){
		if(mInstance == null ){
			mInstance = new MainRequestQueue(context);
		}
		return mInstance;
	}
	
	private MainRequestQueue(Context context){
		mContext = context.getApplicationContext();
	}
	
	public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

	/** 将请求加入到队列中 */
    public <T> void addToQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
