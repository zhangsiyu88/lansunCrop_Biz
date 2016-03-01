package com.lansun.qmyo.maijie_biz.asynctask.download.threadpool;

import android.os.Process;
import android.text.TextUtils;

import com.lansun.qmyo.maijie_biz.asynctask.download.http.DownloadHttpListener;
import com.lansun.qmyo.maijie_biz.asynctask.download.http.HttpProvider;

/**
 * extends ThreadPoolTask,执行具体的线程操作，提供DownloadHttpListener的接口对象
 * @author  Yeun.Zhang
 *
 */
public class ThreadPoolAppTask extends ThreadPoolTask {

	private DownloadHttpListener mDownloadHttpListener;
	
	public ThreadPoolAppTask(String url, DownloadHttpListener listener) {
		super(url);
		mDownloadHttpListener = listener;
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
		
		//装饰模式：实际工作的为HttpProvider.doGet()进行
		if(!TextUtils.isEmpty(getURL())) {
			HttpProvider.doGet(getURL(), mDownloadHttpListener);
		}
	}

}
