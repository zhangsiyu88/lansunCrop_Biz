package com.lansun.qmyo.maijie_biz.asynctask.download.threadpool;

import com.lansun.qmyo.maijie_biz.log.FrameLog;

/**
 * 线程池中的任务单元，继承此类可以衍生出不同的任务类型
 * 具体的操作请在run函数里实现
 * @author Yeun.zhang
 *
 */
public abstract class ThreadPoolTask implements Runnable {

	protected String mUrl;
	
	public ThreadPoolTask(String url) {
		mUrl = url;
		FrameLog.d("ThreadPoolTask", mUrl);
	}
	
	public abstract void run();
	
	public String getURL() {
		return mUrl != null ? mUrl.trim() : mUrl;
	}
}
